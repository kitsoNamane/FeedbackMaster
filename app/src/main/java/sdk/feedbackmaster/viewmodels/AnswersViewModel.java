package sdk.feedbackmaster.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import sdk.feedbackmaster.MainActivity;
import sdk.feedbackmaster.model.AnswerResponse;
import sdk.feedbackmaster.model.NetworkState;
import sdk.feedbackmaster.model.QuestionnaireAnswer;
import sdk.feedbackmaster.repository.api.FeedbackMasterQuestionnaireApi;

public class AnswersViewModel extends ViewModel {
    public Runnable reload;
    private FeedbackMasterQuestionnaireApi feedbackMasterQuestionnaireApi = FeedbackMasterQuestionnaireApi.getInstance(MainActivity.feedbackMasterSurveyApiService);
    private MutableLiveData<QuestionnaireAnswer> getQuestionnaireAnswerArgs = new MutableLiveData<>();
    private MutableLiveData<Integer> getNetworkArgs = new MutableLiveData<>(1);
    private Integer resetNetwork = new Integer(0);

    private LiveData<AnswerResponse> answerResponse = Transformations.switchMap(getQuestionnaireAnswerArgs,
            (getQuestionnaireAnswerArgs) -> feedbackMasterQuestionnaireApi.sendAnswers(getQuestionnaireAnswerArgs)
    );

    private LiveData<NetworkState> networkState = Transformations.switchMap(getNetworkArgs,
            (args) ->  feedbackMasterQuestionnaireApi.getNetworkState()
    );

    public void sendAnswers(QuestionnaireAnswer answer) {
        getQuestionnaireAnswerArgs.setValue(answer);
        getNetworkArgs.setValue(getNetworkArgs.getValue()+1);
        reload = () -> sendAnswers(answer);
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
