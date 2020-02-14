package sdk.feedbackmaster.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import sdk.feedbackmaster.MainActivity;
import sdk.feedbackmaster.model.AnswerResponse;
import sdk.feedbackmaster.model.GetQuestionArgs;
import sdk.feedbackmaster.model.NetworkState;
import sdk.feedbackmaster.model.QuestionnaireAnswer;
import sdk.feedbackmaster.model.Result;
import sdk.feedbackmaster.repository.api.FeedbackMasterQuestionnaireApi;

public class QuestionnaireViewModel extends ViewModel {
    public Runnable reload;
    private FeedbackMasterQuestionnaireApi feedbackMasterQuestionnaireApi = FeedbackMasterQuestionnaireApi.getInstance(MainActivity.feedbackMasterSurveyApiService);
    private MutableLiveData<QuestionnaireAnswer> getQuestionnaireAnswerArgs = new MutableLiveData<>();

    private MutableLiveData<Integer> getNetworkArgs = new MutableLiveData<>(1);
    private MutableLiveData<GetQuestionArgs> getQuestionsArgs = new MutableLiveData<>();
    private Integer resetNetwork = new Integer(0);

    public LiveData<Result> questionnaire = Transformations.switchMap(getQuestionsArgs,
        (args) -> feedbackMasterQuestionnaireApi.getQuestions(args.getSurveyReference(), args.getBusinessReference())
    );

    public LiveData<AnswerResponse> answerResponse = Transformations.switchMap(getQuestionnaireAnswerArgs,
            (getQuestionnaireAnswerArgs) -> feedbackMasterQuestionnaireApi.sendAnswers(getQuestionnaireAnswerArgs)
    );

    public LiveData<NetworkState> networkState = Transformations.switchMap(getNetworkArgs,
        (args) ->  feedbackMasterQuestionnaireApi.getNetworkState()
    );



    public void getQuestionsFromServer(String surveyReference, String businessReference) {
        getQuestionsArgs.setValue(new GetQuestionArgs(surveyReference, businessReference));
        getNetworkArgs.setValue(getNetworkArgs.getValue()+1);
        Log.d("FMDIGILAB 10", "surveyAlias : "+surveyReference+" businessAlias : "+businessReference);
        reload = () -> getQuestionsFromServer(surveyReference, businessReference);
    }

    public void sendAnswerToServer(QuestionnaireAnswer answer) {
        getQuestionnaireAnswerArgs.setValue(answer);
        getNetworkArgs.setValue(getNetworkArgs.getValue()+1);
        reload = () -> sendAnswerToServer(answer);
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

    public void retry() {
        Thread thread = new Thread(
                reload
        );
        thread.run();
    }

    public void clearNetworkState() {
        feedbackMasterQuestionnaireApi.clearNetworkState();
        getNetworkArgs.setValue(resetNetwork);
    }
}

