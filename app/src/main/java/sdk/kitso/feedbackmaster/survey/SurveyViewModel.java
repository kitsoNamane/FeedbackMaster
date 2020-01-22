package sdk.kitso.feedbackmaster.survey;

import android.content.Context;
<<<<<<< HEAD

import com.google.gson.JsonObject;
=======
import android.util.Log;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
>>>>>>> pagination

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
<<<<<<< HEAD
import io.reactivex.disposables.CompositeDisposable;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.db.Survey;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterNetworkDataSource;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterNetworkDataSourceFactory;
=======
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.model.DataItem;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterNetworkDataFactory;
>>>>>>> pagination
import sdk.kitso.feedbackmaster.repository.FeedbackMasterSurveyApi;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterSurveyApiService;

public class SurveyViewModel extends ViewModel {
<<<<<<< HEAD
    public LiveData<PagedList<JsonObject>> surveys;
    public DataSource.Factory factory;
    private FeedbackMasterSurveyApiService feedbackMasterSurveyApiService;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int pageSize = 10;
    private FeedbackMasterNetworkDataSourceFactory feedbackMasterNetworkDataSourceFactory;

    public void init(String device_uuid, Context context) {
        feedbackMasterSurveyApiService = FeedbackMasterSurveyApi.getService(device_uuid, context);
        feedbackMasterNetworkDataSourceFactory = new FeedbackMasterNetworkDataSourceFactory(feedbackMasterSurveyApiService, compositeDisposable);
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize*2)
                .setEnablePlaceholders(true)
                .build();
        surveys = new LivePagedListBuilder(feedbackMasterNetworkDataSourceFactory, config).build();
    }

    public LiveData<State> getState() {
        return Transformations.switchMap(feedbackMasterNetworkDataSourceFactory.feedbackMasterLiveData,  state ->{
            return FeedbackMasterNetworkDataSource.state;
        });
    }

    public void retry() {
        feedbackMasterNetworkDataSourceFactory.feedbackMasterLiveData.getValue().retry();
    }

    @Override
    public void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
=======
    private Executor executor;

    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<DataItem>> surveyLiveData;

    private FeedbackMasterSurveyApiService feedbackMasterSurveyApiService;
    private FeedbackMasterNetworkDataFactory feedbackMasterNetworkDataFactory;

    private DataSource<Integer, DataItem> mostRecentDataSource;

    private int pageSize = 10;

    public void init(String device_uuid, Context context) {
        executor = Executors.newFixedThreadPool(5);
        feedbackMasterSurveyApiService = FeedbackMasterSurveyApi.getService(device_uuid, context);
        feedbackMasterNetworkDataFactory = new FeedbackMasterNetworkDataFactory(feedbackMasterSurveyApiService);
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
>>>>>>> pagination
    }
}
