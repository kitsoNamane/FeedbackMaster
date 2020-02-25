package sdk.feedbackmaster.repository.remote_datasource.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdk.feedbackmaster.model.NetworkState;
import sdk.feedbackmaster.model.SearchResponse;
import sdk.feedbackmaster.repository.remote_datasource.FeedbackMasterSurveyApiService;

public class FeedbackMasterSearchApi {
    private static FeedbackMasterSearchApi instance;
    public Runnable reload;
    private MutableLiveData<NetworkState> networkState;
    private MutableLiveData<SearchResponse> searchResponse;
    private FeedbackMasterSurveyApiService apiService;

    private FeedbackMasterSearchApi(FeedbackMasterSurveyApiService api) {
        this.apiService = api;
        this.networkState = new MutableLiveData<>();
        this.searchResponse = new MutableLiveData<>();
    }

    public static FeedbackMasterSearchApi getInstance(FeedbackMasterSurveyApiService api) {
        if(instance == null) {
            instance = new FeedbackMasterSearchApi(api);
        }
        return instance;
    }

    public MutableLiveData<SearchResponse> getSearchResults(String keyword) {
        networkState.postValue(NetworkState.LOADING);
        apiService.searchCompany(keyword).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if(response.isSuccessful() && response.body().isSuccess()) {
                    searchResponse.postValue(response.body());
                    networkState.postValue(NetworkState.LOADED);
                    Log.d("FMDIGILAB 12", response.body().toString());
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED,
                            response.body().getMessage().get(0).toString())
                    );
                    Log.d("FMDIGILAB 12", response.body().toString());
                    searchResponse.postValue(response.body());
                    reload = () -> call.request();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable throwable) {
                String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                searchResponse.postValue(null);
                Log.d("FMDIGILAB 12", "Exception"+throwable.getMessage());
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                reload = () -> call.request();
            }
        });
        return searchResponse;
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        if(networkState == null) {
            networkState = new MutableLiveData<>();
        }
        return networkState;
    }

    public void invalidate() {
        networkState.postValue(new NetworkState(NetworkState.Status.NULL, "Current Value Null"));
    }
}
