package sdk.kitso.feedbackmaster.survey;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
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
    private int pageSize = 10;

    public SurveyViewModel() {
        executor = Executors.newFixedThreadPool(5);
        feedbackMasterNetworkDataFactory = new FeedbackMasterNetworkDataFactory(MainActivity.feedbackMasterSurveyApiService);
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

    public void init() {
    }

    public void retry() {
        Thread thread = new Thread(
               Objects.requireNonNull(feedbackMasterNetworkDataFactory.getMutableLiveData().getValue()).reload
        );
        thread.start();
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<DataItem>> getSurveyLiveData() {
        return surveyLiveData;
    }

}
