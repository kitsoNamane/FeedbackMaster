package sdk.kitso.feedbackmaster.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.model.AnswerResponse;
import sdk.kitso.feedbackmaster.model.QuestionResponse;
import sdk.kitso.feedbackmaster.model.QuestionnaireAnswer;
import sdk.kitso.feedbackmaster.model.Result;

public class FeedbackMasterQuestionnaireApi {

    public static FeedbackMasterQuestionnaireApi instance;
    public Runnable reload;
    private MutableLiveData<NetworkState> networkState;
    private MutableLiveData<Result> questionnaire;
    private MutableLiveData<AnswerResponse> answerResponse;

    private FeedbackMasterQuestionnaireApi() {
        questionnaire = new MutableLiveData<>();
        networkState = new MutableLiveData<>();
        answerResponse = new MutableLiveData<>();
    }

    public static FeedbackMasterQuestionnaireApi getInstance() {
        if(instance == null) {
            instance = new FeedbackMasterQuestionnaireApi();
        }
        return instance;
    }

    public void sendQuestionsToServer(QuestionnaireAnswer questionnaireAnswer) {
        networkState.postValue(NetworkState.LOADING);
        MainActivity.feedbackMasterSurveyApiService.sendAnswer(questionnaireAnswer).enqueue(new Callback<AnswerResponse>() {
            @Override
            public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("FMDIGILAB 25", response.message());
                    Log.d("FMDIGILAB 25", response.body().toString());
                    answerResponse.postValue(response.body());
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    Log.d("FMDIGILAB 26", response.message());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    reload = () -> sendQuestionsToServer(questionnaireAnswer);
                }
            }

            @Override
            public void onFailure(Call<AnswerResponse> call, Throwable throwable) {
                String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                Log.d("FMDIGILAB 27", errorMessage);
                questionnaire.postValue(null);
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                reload = () -> sendQuestionsToServer(questionnaireAnswer);
            }
        });
    }

    public void getQuestionsFromServer(String surveyReference, String businessReference) {
        if(questionnaire.getValue() == null) {
            _getQuestionsFromServer(surveyReference, businessReference);
        } else if(!questionnaire.getValue().getQuestionBusiness().getRef()
                .equals(businessReference)
        ) {
            _getQuestionsFromServer(surveyReference, businessReference);
        }
    }

    private void _getQuestionsFromServer(String surveyReference, String businessReference) {
        networkState.postValue(NetworkState.LOADING);
        MainActivity.feedbackMasterSurveyApiService.getQuestions(surveyReference, businessReference).enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("FMDIGILAB 20", response.message());
                    questionnaire.postValue(response.body().getResult());
                    Log.d("FMDIGILAB 23", response.body().getResult().toString());
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    Log.d("FMDIGILAB 20", response.message());
                    questionnaire.postValue(null);
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    reload = () -> getQuestionsFromServer(surveyReference, businessReference);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable throwable) {
                String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                Log.d("FMDIGILAB 20", errorMessage);
                questionnaire.postValue(null);
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                reload = () -> getQuestionsFromServer(surveyReference, businessReference);
            }
        });
    }

    public MutableLiveData<Result> getQuestionnaire() {
        return questionnaire;
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
