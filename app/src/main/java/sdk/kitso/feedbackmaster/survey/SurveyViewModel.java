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
    private int pageSize = 10;
    private Executor executor = Executors.newFixedThreadPool(5);

    private FeedbackMasterNetworkDataFactory feedbackMasterNetworkDataFactory = new FeedbackMasterNetworkDataFactory(
            MainActivity.feedbackMasterSurveyApiService
    );

    private LiveData<NetworkState>  networkState = Transformations.switchMap(
                feedbackMasterNetworkDataFactory.getMutableLiveData(), dataSource-> dataSource.getNetworkState()
    );

    PagedList.Config config = new PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(200)
            .setEnablePlaceholders(true)
            .build();

    private LiveData<PagedList<DataItem>>   surveyLiveData = new LivePagedListBuilder(feedbackMasterNetworkDataFactory, config)
                .setFetchExecutor(executor)
                .build();

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
