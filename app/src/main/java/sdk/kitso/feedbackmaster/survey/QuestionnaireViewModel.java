package sdk.kitso.feedbackmaster.survey;

import android.util.Log;

import java.util.Objects;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.model.AnswerResponse;
import sdk.kitso.feedbackmaster.model.QuestionnaireAnswer;
import sdk.kitso.feedbackmaster.model.Result;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterQuestionnaireApiFactory;

public class QuestionnaireViewModel extends ViewModel {
    private LiveData<NetworkState> networkState;
    private LiveData<Result> questionnaire;
    private LiveData<AnswerResponse> answerResponse;
    private FeedbackMasterQuestionnaireApiFactory feedbackMasterQuestionnaireApiFactory;

    public void init() {
        Log.d("LIVEDATA 3", "livedata created");
        feedbackMasterQuestionnaireApiFactory = new FeedbackMasterQuestionnaireApiFactory(MainActivity.feedbackMasterSurveyApiService);
    }

    public void getQuestionsFromServer(String surveyReference, String businessReference) {
        feedbackMasterQuestionnaireApiFactory.getMutableLiveData()
                .getValue()
                .getQuestionsFromServer(surveyReference, businessReference);
        networkState = Transformations.switchMap(feedbackMasterQuestionnaireApiFactory.getMutableLiveData(),
                feedbackMasterQuestionnaireApi1 -> feedbackMasterQuestionnaireApi1.getNetworkState());
        questionnaire = Transformations.switchMap(feedbackMasterQuestionnaireApiFactory.getMutableLiveData(),
                feedbackMasterQuestionnaireApi -> feedbackMasterQuestionnaireApi.getQuestionnaire());
    }

    public void sendAnswerToServer(QuestionnaireAnswer answer) {
        Objects.requireNonNull(feedbackMasterQuestionnaireApiFactory.getMutableLiveData()
                .getValue())
                .sendQuestionsToServer(answer);
        networkState = Transformations.switchMap(feedbackMasterQuestionnaireApiFactory.getMutableLiveData(),
                feedbackMasterQuestionnaireApi -> feedbackMasterQuestionnaireApi.getNetworkState());
        answerResponse = Transformations.switchMap(feedbackMasterQuestionnaireApiFactory.getMutableLiveData(),
                feedbackMasterQuestionnaireApi -> feedbackMasterQuestionnaireApi.getAnswerResponse());
    }

    public void retry() {
        Thread thread = new Thread(
                feedbackMasterQuestionnaireApiFactory.getMutableLiveData().getValue().reload
        );
        thread.start();
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<AnswerResponse> getAnswerResponse() {
        return answerResponse;
    }

    public LiveData<Result> getQuestionnaire() {
        return questionnaire;
    }
}
