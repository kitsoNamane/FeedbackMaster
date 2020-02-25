package sdk.feedbackmaster.repository.remote_datasource.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdk.feedbackmaster.model.AnswerResponse;
import sdk.feedbackmaster.model.NetworkState;
import sdk.feedbackmaster.model.QuestionResponse;
import sdk.feedbackmaster.model.QuestionnaireAnswer;
import sdk.feedbackmaster.model.Result;
import sdk.feedbackmaster.repository.remote_datasource.FeedbackMasterSurveyApiService;

public class FeedbackMasterQuestionnaireApi {

    private static FeedbackMasterQuestionnaireApi instance;
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
        if(questionnaireAnswer.getAnswers() == null) {
            answerResponse.postValue(null);
            return answerResponse;
        }

        apiService.sendAnswer(questionnaireAnswer).enqueue(new Callback<AnswerResponse>() {
            @Override
            public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
                if(response.isSuccessful() && response.body().isSuccess()) {
                    answerResponse.postValue(response.body());
                    networkState.postValue(NetworkState.LOADED);
                    Log.d("FMDIGILAB 11", response.body().toString());
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED,
                            response.body().getMessage().get(0).toString())
                    );
                    Log.d("FMDIGILAB 11", response.body().toString());
                    answerResponse.postValue(response.body());
                    reload = () -> call.request();
                }
            }

            @Override
            public void onFailure(Call<AnswerResponse> call, Throwable throwable) {
                String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                answerResponse.postValue(null);
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                reload = () -> call.request();
            }
        });
        return answerResponse;
    }

    public MutableLiveData<Result> getQuestions(String surveyReference, String businessReference) {
        if(surveyReference == null || businessReference == null) {
            questionnaire.postValue(null);
            return questionnaire;
        }

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
                if(response.isSuccessful() && response.body().isSuccess()) {
                    networkState.postValue(NetworkState.LOADED);
                    questionnaire.postValue(response.body().getResult());
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED,
                            response.body().getMessage().get(0).toString())
                    );
                    questionnaire.postValue(response.body().getResult());
                    reload = () -> call.request();
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable throwable) {
                String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                questionnaire.postValue(null);
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                reload = () -> call.request();
            }
        });
        return questionnaire;
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public void invalidateAnswerResponse() {
        answerResponse.postValue(null);
    }

    public void invalidateQuestionnaire() {
        questionnaire.postValue(null);
    }

    public void clearNetworkState() {
        networkState.postValue(new NetworkState(NetworkState.Status.NULL, "Current Value Null"));
    }
}
