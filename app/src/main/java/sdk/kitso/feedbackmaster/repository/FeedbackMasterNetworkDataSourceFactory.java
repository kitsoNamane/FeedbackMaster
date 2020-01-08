package sdk.kitso.feedbackmaster.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import io.reactivex.disposables.CompositeDisposable;

public class FeedbackMasterNetworkDataSourceFactory extends DataSource.Factory {
    CompositeDisposable compositeDisposable;
    FeedbackMasterSurveyApiService feedbackMasterSurveyApi;
    public MutableLiveData<FeedbackMasterNetworkDataSource> feedbackMasterLiveData = new MutableLiveData<>();

    public FeedbackMasterNetworkDataSourceFactory(FeedbackMasterSurveyApiService feedbackMasterSurveyApi, CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
        this.feedbackMasterSurveyApi = feedbackMasterSurveyApi;
    }

    @NonNull
    @Override
    public DataSource create() {
        FeedbackMasterNetworkDataSource feedbackMasterNetworkDataSource = new FeedbackMasterNetworkDataSource(feedbackMasterSurveyApi, compositeDisposable);
        feedbackMasterLiveData.postValue(feedbackMasterNetworkDataSource);
        return feedbackMasterNetworkDataSource;
    }
}
