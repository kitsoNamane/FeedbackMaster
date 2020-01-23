package sdk.kitso.feedbackmaster.survey;

import android.content.Context;
import android.util.Log;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.model.DataItem;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterNetworkDataFactory;

public class SurveyViewModel extends ViewModel {
    private Executor executor;

    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<DataItem>> surveyLiveData;

    private FeedbackMasterNetworkDataFactory feedbackMasterNetworkDataFactory;

    private DataSource<Integer, DataItem> mostRecentDataSource;

    private int pageSize = 10;

    public void init() {
        executor = Executors.newFixedThreadPool(5);
        feedbackMasterNetworkDataFactory = new FeedbackMasterNetworkDataFactory(MainActivity.feedbackMasterSurveyApiService);
        mostRecentDataSource = feedbackMasterNetworkDataFactory.create();
        networkState = Transformations.switchMap(
                feedbackMasterNetworkDataFactory.getMutableLiveData(), dataSource-> dataSource.getNetworkState()
        );

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(200)
                .setEnablePlaceholders(true)
                .build();
        surveyLiveData = new LivePagedListBuilder(feedbackMasterNetworkDataFactory, config)
                .setFetchExecutor(executor)
                .build();
    }

    public void retry() {
        Thread thread = new Thread(
               Objects.requireNonNull(feedbackMasterNetworkDataFactory.getMutableLiveData().getValue()).reload
        );
        thread.start();
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
