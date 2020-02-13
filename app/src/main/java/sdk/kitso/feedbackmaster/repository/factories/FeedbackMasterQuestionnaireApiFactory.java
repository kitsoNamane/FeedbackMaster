package sdk.kitso.feedbackmaster.repository.factories;

import androidx.lifecycle.MutableLiveData;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterSurveyApiService;
import sdk.kitso.feedbackmaster.repository.api.FeedbackMasterQuestionnaireApi;

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
