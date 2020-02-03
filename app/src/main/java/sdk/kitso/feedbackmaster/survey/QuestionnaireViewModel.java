package sdk.kitso.feedbackmaster.survey;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.model.AnswerResponse;
import sdk.kitso.feedbackmaster.model.GetQuestionArgs;
import sdk.kitso.feedbackmaster.model.QuestionnaireAnswer;
import sdk.kitso.feedbackmaster.model.Result;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterQuestionnaireApi;

public class QuestionnaireViewModel extends ViewModel {
    private FeedbackMasterQuestionnaireApi feedbackMasterQuestionnaireApi = FeedbackMasterQuestionnaireApi.getInstance(MainActivity.feedbackMasterSurveyApiService);
    private MutableLiveData<GetQuestionArgs> getQuestionsArgs = new MutableLiveData<>();
    private MutableLiveData<QuestionnaireAnswer> getQuestionnaireAnswerArgs = new MutableLiveData<>();
    private MutableLiveData<Integer> getNetworkArgs = new MutableLiveData<>(0);

    public LiveData<Result> questionnaire = Transformations.switchMap(getQuestionsArgs,
        (args) -> feedbackMasterQuestionnaireApi.getQuestions(args.getSurveyReference(), args.getBusinessReference())
    );

    public LiveData<NetworkState> networkState = Transformations.switchMap(getNetworkArgs,
            args -> feedbackMasterQuestionnaireApi.getNetworkState()
    );

    public LiveData<AnswerResponse> answerResponse = Transformations.switchMap(getQuestionnaireAnswerArgs,
            getQuestionnaireAnswerArgs -> feedbackMasterQuestionnaireApi.sendAnswers(getQuestionnaireAnswerArgs)
    );

    public Runnable reload;

    public void init() {
    }

    public void getQuestionsFromServer(String surveyReference, String businessReference) {
        Log.d("FMDIGILAB", "getting questions");
        getQuestionsArgs.setValue(new GetQuestionArgs(surveyReference, businessReference));
        getNetworkArgs.setValue(getNetworkArgs.getValue()+1);
        reload = () -> getQuestionsFromServer(surveyReference, businessReference);
    }

    public void sendAnswerToServer(QuestionnaireAnswer answer) {
        getQuestionnaireAnswerArgs.setValue(answer);
        getNetworkArgs.setValue(getNetworkArgs.getValue()+1);
        reload = () -> sendAnswerToServer(answer);
    }

    public void retry() {
        Thread thread = new Thread(
                reload
        );
        thread.run();
    }

    public LiveData<NetworkState> getNetworkState() {
        if(networkState == null) {
            networkState = new MutableLiveData<>();
        }
        return networkState;
    }

    public LiveData<AnswerResponse> getAnswerResponse() {
        if(answerResponse == null) {
            answerResponse = new MutableLiveData<>();
        }
        return answerResponse;
    }

    public LiveData<Result> getQuestionnaire() {
        if(questionnaire == null) {
            questionnaire = new MutableLiveData<>();
        }
        return questionnaire;
    }
}
