package sdk.kitso.feedbackmaster.repository;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdk.kitso.feedbackmaster.db.DataItem;
import sdk.kitso.feedbackmaster.db.Survey;

public class FeedbackMasterNetworkDataFactory extends DataSource.Factory<Integer, DataItem> {
    FeedbackMasterNetworkDataSource feedbackMasterNetworkDataSource;
    FeedbackMasterSurveyApiService apiService;
    private MutableLiveData<FeedbackMasterNetworkDataSource> mutableLiveData;

    public FeedbackMasterNetworkDataFactory(FeedbackMasterSurveyApiService api) {
        this.apiService = api;
        this.mutableLiveData = new MutableLiveData<FeedbackMasterNetworkDataSource>();
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
