package sdk.feedbackmaster.repository.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import sdk.feedbackmaster.model.DataItem;
import sdk.feedbackmaster.repository.FeedbackMasterSurveyApiService;
import sdk.feedbackmaster.repository.api.FeedbackMasterNetworkDataSource;


public class FeedbackMasterNetworkDataFactory extends DataSource.Factory<Integer, DataItem> {
    FeedbackMasterNetworkDataSource feedbackMasterNetworkDataSource;
    FeedbackMasterSurveyApiService apiService;
    public MutableLiveData<FeedbackMasterNetworkDataSource> mutableLiveData;

    public FeedbackMasterNetworkDataFactory(FeedbackMasterSurveyApiService api) {
        this.apiService = api;
        this.mutableLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource<Integer, DataItem> create() {
        if(feedbackMasterNetworkDataSource == null) {
            feedbackMasterNetworkDataSource = new FeedbackMasterNetworkDataSource(apiService);
        }
        mutableLiveData.postValue(feedbackMasterNetworkDataSource);
        return feedbackMasterNetworkDataSource;
    }

    public MutableLiveData<FeedbackMasterNetworkDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
