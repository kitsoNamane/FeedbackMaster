package sdk.kitso.feedbackmaster.survey;

import android.content.Context;

import com.google.gson.JsonObject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.reactivex.disposables.CompositeDisposable;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.db.Survey;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterNetworkDataSource;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterNetworkDataSourceFactory;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterSurveyApi;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterSurveyApiService;

public class SurveyViewModel extends ViewModel {
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
    }
}
