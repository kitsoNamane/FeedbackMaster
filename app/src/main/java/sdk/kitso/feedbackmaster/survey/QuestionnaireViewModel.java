package sdk.kitso.feedbackmaster.survey;

import android.util.Log;

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
    private Runnable reload;
    private LiveData<AnswerResponse> answerResponse;
    private FeedbackMasterQuestionnaireApiFactory feedbackMasterQuestionnaireApiFactory;

    public void init() {
        Log.d("LIVEDATA 3", "livedata created");
        feedbackMasterQuestionnaireApiFactory = new FeedbackMasterQuestionnaireApiFactory(MainActivity.feedbackMasterSurveyApiService);
    }

    public void getQuestionsFromServer(String surveyReference, String businessReference) {
        questionnaire = Transformations.switchMap(feedbackMasterQuestionnaireApiFactory.getMutableLiveData(),
                feedbackMasterQuestionnaireApi -> feedbackMasterQuestionnaireApi.getQuestions(surveyReference, businessReference));
        networkState = Transformations.switchMap(feedbackMasterQuestionnaireApiFactory.getMutableLiveData(),
                feedbackMasterQuestionnaireApi1 -> feedbackMasterQuestionnaireApi1.getNetworkState());
        reload = () -> getQuestionsFromServer(surveyReference, businessReference);
    }

    public void sendAnswerToServer(QuestionnaireAnswer answer) {
        answerResponse = Transformations.switchMap(feedbackMasterQuestionnaireApiFactory.getMutableLiveData(),
                feedbackMasterQuestionnaireApi -> feedbackMasterQuestionnaireApi.sendAnswers(answer));
        networkState = Transformations.switchMap(feedbackMasterQuestionnaireApiFactory.getMutableLiveData(),
                feedbackMasterQuestionnaireApi -> feedbackMasterQuestionnaireApi.getNetworkState());
        reload = () -> sendAnswerToServer(answer);
    }

    public void retry() {
        Thread thread = new Thread(
                reload
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
