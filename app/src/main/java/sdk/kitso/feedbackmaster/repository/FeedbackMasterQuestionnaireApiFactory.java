package sdk.kitso.feedbackmaster.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

public class FeedbackMasterQuestionnaireApiFactory {

    private FeedbackMasterQuestionnaireApi feedbackMasterQuestionnaireApi;
    private MutableLiveData<FeedbackMasterQuestionnaireApi> mutableLiveData;

    public FeedbackMasterQuestionnaireApiFactory(FeedbackMasterSurveyApiService apiService) {
        this.mutableLiveData = new MutableLiveData<>();
        Log.d("LIVEDATA 1", "livedata created");

        if(this.feedbackMasterQuestionnaireApi == null) {
            Log.d("LIVEDATA 2", "livedata created");
            this.feedbackMasterQuestionnaireApi = FeedbackMasterQuestionnaireApi.getInstance(apiService);
        }
        this.mutableLiveData.postValue(this.feedbackMasterQuestionnaireApi);
    }

    public MutableLiveData<FeedbackMasterQuestionnaireApi> getMutableLiveData() {
        return this.mutableLiveData;
    }
}
