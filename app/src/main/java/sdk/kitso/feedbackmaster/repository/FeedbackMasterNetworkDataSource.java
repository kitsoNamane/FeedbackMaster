package sdk.kitso.feedbackmaster.repository;


import android.net.Network;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.db.DataItem;

public class FeedbackMasterNetworkDataSource extends PageKeyedDataSource<Integer, DataItem> {
    private static final String TAG = FeedbackMasterNetworkDataSource.class.getSimpleName();
    private FeedbackMasterSurveyApiService apiService;

    private MutableLiveData networkState;
    private MutableLiveData initialLoading;

    public FeedbackMasterNetworkDataSource(FeedbackMasterSurveyApiService api) {
        this.apiService = api;
        networkState = new MutableLiveData<>();
        initialLoading = new MutableLiveData<>();
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public MutableLiveData<NetworkState> getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> loadInitialParams, @NonNull LoadInitialCallback<Integer, DataItem> loadInitialCallback) {
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        apiService.getNextSurveys(1).enqueue(new Callback<List<DataItem>>() {
            @Override
            public void onResponse(Call<List<DataItem>> call, Response<List<DataItem>> response) {
                if(response.isSuccessful()) {
                    loadInitialCallback.onResult(response.body(), null, 2);
                    initialLoading.postValue(NetworkState.LOADED);
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<DataItem>> call, Throwable throwable) {
                String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, DataItem> loadCallback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, DataItem> loadCallback) {
        Log.i(TAG, "Loading Page " + loadParams.key);
        networkState.postValue(NetworkState.LOADING);

        apiService.getNextSurveys(loadParams.key).enqueue(new Callback<List<DataItem>>() {
              @Override
              public void onResponse(Call<List<DataItem>> call, Response<List<DataItem>> response) {
                  if(response.isSuccessful()) {
                      loadCallback.onResult(response.body(), loadParams.key + 1);
                      networkState.postValue(NetworkState.LOADED);
                  } else {
                      networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                  }
              }

              @Override
              public void onFailure(Call<List<DataItem>> call, Throwable throwable) {
                  String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                  networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
              }
          }
        );
    }
}
