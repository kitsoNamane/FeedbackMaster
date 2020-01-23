package sdk.kitso.feedbackmaster.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.model.QuestionResponse;
import sdk.kitso.feedbackmaster.model.Result;

public class FeedbackMasterQuestions {

    public static FeedbackMasterQuestions instance;
    private Runnable reload;
    private MutableLiveData<NetworkState> networkState;
    private MutableLiveData<Result> questionnaire;

    private FeedbackMasterQuestions() {
        questionnaire = new MutableLiveData<>();
        networkState = new MutableLiveData<>();
    }

    public static FeedbackMasterQuestions getInstance() {
        if(instance == null) {
            instance = new FeedbackMasterQuestions();
        }
        return instance;
    }

    public void getQuestionsFromServer(String surveyReference, String businessReference) {
        //initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        MainActivity.feedbackMasterSurveyApiService.getQuestions(surveyReference, businessReference).enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if(response.isSuccessful()) {
                    //SystemClock.sleep(3000);
                    Log.d("FMDIGILAB 20", response.message());
                    //loadInitialCallback.onResult(response.body().getData().getDataItemList(), null, 2);
                    //Result result = response.body().getResult();
                    questionnaire.postValue(response.body().getResult());
                    //initialLoading.postValue(NetworkState.LOADED);
                    //networkState.postValue(NetworkState.LOADED);
         //           initialLoading.postValue(NetworkState.LOADED);
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    Log.d("FMDIGILAB 20", response.message());
                   networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    questionnaire.postValue(null);
                    reload = () -> getQuestionsFromServer(surveyReference, businessReference);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable throwable) {
                String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                Log.d("FMDIGILAB 20", errorMessage);
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                reload = () -> getQuestionsFromServer(surveyReference, businessReference);
                questionnaire.postValue(null);
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
