package sdk.kitso.feedbackmaster.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.model.SearchResponse;

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
                    Log.d("FMDIGILAB 37", response.message());
                    Log.d("FMDIGILAB 37", response.body().toString());
                    searchResponse.postValue(response.body());
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    Log.d("FMDIGILAB 38", response.body().getMessage().get(0).toString());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED,
                            response.body().getMessage().get(0).toString())
                    );
                    searchResponse.postValue(response.body());
                    reload = () -> call.request();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable throwable) {
                String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                Log.d("FMDIGILAB 39", errorMessage);
                searchResponse.postValue(null);
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
}
