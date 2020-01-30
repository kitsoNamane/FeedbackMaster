package sdk.kitso.feedbackmaster.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    private FeedbackMasterSurveyApiService apiService;

    private FeedbackMasterQuestionnaireApi(FeedbackMasterSurveyApiService apiService) {
        this.questionnaire = new MutableLiveData<>();
        this.networkState = new MutableLiveData<>();
        this.answerResponse = new MutableLiveData<>();
        this.apiService = apiService;
    }

    public static FeedbackMasterQuestionnaireApi getInstance(FeedbackMasterSurveyApiService apiService) {
        if(instance == null) {
            instance = new FeedbackMasterQuestionnaireApi(apiService);
        }
        return instance;
    }

    public MutableLiveData<AnswerResponse> sendAnswers(QuestionnaireAnswer questionnaireAnswer) {
        networkState.postValue(NetworkState.LOADING);
        apiService.sendAnswer(questionnaireAnswer).enqueue(new Callback<AnswerResponse>() {
            @Override
            public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("FMDIGILAB 25", response.message());
                    Log.d("FMDIGILAB 25", response.body().toString());
                    answerResponse.postValue(response.body());
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    Log.d("FMDIGILAB 26", response.message());
                    answerResponse.postValue(null);
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    reload = () -> sendAnswers(questionnaireAnswer);
                }
            }

            @Override
            public void onFailure(Call<AnswerResponse> call, Throwable throwable) {
                String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                Log.d("FMDIGILAB 27", errorMessage);
                questionnaire.postValue(null);
                answerResponse.postValue(null);
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                reload = () -> sendAnswers(questionnaireAnswer);
            }
        });
        return answerResponse;
    }

    public MutableLiveData<Result> getQuestions(String surveyReference, String businessReference) {
        if(questionnaire.getValue() == null) {
            return _getQuestionsFromServer(surveyReference, businessReference);
        } else if(!questionnaire.getValue().getQuestionBusiness().getRef()
                .equals(businessReference)
        ) {
            return _getQuestionsFromServer(surveyReference, businessReference);
        } else {
            questionnaire.postValue(null);
            return questionnaire;
        }
    }

    private MutableLiveData<Result> _getQuestionsFromServer(String surveyReference, String businessReference) {
        networkState.postValue(NetworkState.LOADING);
        apiService.getQuestions(surveyReference, businessReference).enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("FMDIGILAB 20", response.message());
                    assert response.body() != null;
                    Log.d("FMDIGILAB 23", response.body().getResult().toString());
                    networkState.postValue(NetworkState.LOADED);
                    questionnaire.postValue(response.body().getResult());
                } else {
                    Log.d("FMDIGILAB 20", response.message());
                    questionnaire.postValue(null);
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    reload = () -> getQuestions(surveyReference, businessReference);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable throwable) {
                String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                Log.d("FMDIGILAB 20", errorMessage);
                questionnaire.postValue(null);
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                reload = () -> getQuestions(surveyReference, businessReference);
            }
        });
        return questionnaire;
    }

    public MutableLiveData<Result> getQuestionnaire() {
        return questionnaire;
    }

    public MutableLiveData<AnswerResponse> getAnswerResponse() {
        return answerResponse;
    }
    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
