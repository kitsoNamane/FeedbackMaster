package sdk.kitso.feedbackmaster.repository;

import androidx.lifecycle.MutableLiveData;

public class FeedbackMasterQuestionnaireApiFactory {

    private FeedbackMasterQuestionnaireApi feedbackMasterQuestionnaireApi;
    private MutableLiveData<FeedbackMasterQuestionnaireApi> mutableLiveData;

    public FeedbackMasterQuestionnaireApiFactory(FeedbackMasterSurveyApiService apiService) {
        this.mutableLiveData = new MutableLiveData<>();

        if(this.feedbackMasterQuestionnaireApi == null) {
            this.feedbackMasterQuestionnaireApi = FeedbackMasterQuestionnaireApi.getInstance(apiService);
        }
        this.mutableLiveData.postValue(this.feedbackMasterQuestionnaireApi);
    }

    public MutableLiveData<FeedbackMasterQuestionnaireApi> getMutableLiveData() {
        return this.mutableLiveData;
    }
}
