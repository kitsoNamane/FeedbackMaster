package sdk.kitso.feedbackmaster.survey;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.db.DataItem;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterNetworkDataFactory;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterNetworkDataSource;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterSurveyApi;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterSurveyApiService;

public class SurveyViewModel extends ViewModel {
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<DataItem>> surveyLiveData;

    private DataSource.Factory factory;
    private FeedbackMasterSurveyApiService feedbackMasterSurveyApiService;
    private int pageSize = 10;
    private FeedbackMasterNetworkDataFactory feedbackMasterNetworkDataFactory;

    public SurveyViewModel(String device_uuid, Context context) {
       init(device_uuid, context);
    }

    public void init(String device_uuid, Context context) {
        feedbackMasterSurveyApiService = FeedbackMasterSurveyApi.getService(device_uuid, context);
        feedbackMasterNetworkDataFactory = new FeedbackMasterNetworkDataFactory(feedbackMasterSurveyApiService);
        networkState = Transformations.switchMap(
                feedbackMasterNetworkDataFactory.getMutableLiveData(), dataSource-> getNetworkState()
        );

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize)
                .setEnablePlaceholders(true)
                .build();
        surveyLiveData = new LivePagedListBuilder(feedbackMasterNetworkDataFactory, config).build();
    }

    /*
     * Getter method for the network state
     */
    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    /*
     * Getter method for the pageList
     */
    public LiveData<PagedList<DataItem>> getSurveyLiveData() {
        return surveyLiveData;
    }
}
