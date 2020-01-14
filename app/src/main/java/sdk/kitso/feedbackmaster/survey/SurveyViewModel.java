package sdk.kitso.feedbackmaster.survey;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.db.DataItem;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterNetworkDataFactory;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterSurveyApi;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterSurveyApiService;

public class SurveyViewModel extends ViewModel {
    private Executor executor;

    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<DataItem>> surveyLiveData;

    private FeedbackMasterSurveyApiService feedbackMasterSurveyApiService;
    private FeedbackMasterNetworkDataFactory feedbackMasterNetworkDataFactory;

    private int pageSize = 10;

    public void init(String device_uuid, Context context) {
        executor = Executors.newFixedThreadPool(5);
        feedbackMasterSurveyApiService = FeedbackMasterSurveyApi.getService(device_uuid, context);
        feedbackMasterNetworkDataFactory = new FeedbackMasterNetworkDataFactory(feedbackMasterSurveyApiService);
        networkState = Transformations.switchMap(
                feedbackMasterNetworkDataFactory.getMutableLiveData(), dataSource-> dataSource.getNetworkState()
        );

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize*2)
                .setEnablePlaceholders(true)
                .build();
        surveyLiveData = new LivePagedListBuilder(feedbackMasterNetworkDataFactory, config)
                .setFetchExecutor(executor)
                .build();
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
