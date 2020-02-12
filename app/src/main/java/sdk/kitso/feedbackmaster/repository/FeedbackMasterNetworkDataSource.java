package sdk.kitso.feedbackmaster.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdk.kitso.feedbackmaster.Globals;
import sdk.kitso.feedbackmaster.model.DataItem;
import sdk.kitso.feedbackmaster.network.NetworkState;

public class FeedbackMasterNetworkDataSource extends PageKeyedDataSource<Integer, DataItem> {
    private static final String TAG = FeedbackMasterNetworkDataSource.class.getSimpleName();
    private FeedbackMasterSurveyApiService apiService;

    private MutableLiveData<NetworkState> networkState;
    private MutableLiveData<NetworkState> initialLoading;
    public Runnable reload;


    public FeedbackMasterNetworkDataSource(FeedbackMasterSurveyApiService api) {
        this.apiService = api;
        networkState = new MutableLiveData<NetworkState>();
        initialLoading = new MutableLiveData<NetworkState>();
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

        apiService.getNextSurveys(Globals.FIRST_PAGE).enqueue(new Callback<sdk.kitso.feedbackmaster.model.Response>() {
            @Override
            public void onResponse(Call<sdk.kitso.feedbackmaster.model.Response> call, Response<sdk.kitso.feedbackmaster.model.Response> response) {
                if(response.isSuccessful() && response.body().isSuccess()) {
                    loadInitialCallback.onResult(response.body().getData().getDataItemList(), null, 2);
                    initialLoading.postValue(NetworkState.LOADED);
                    Log.d("FMDIGILAB 1", response.body().getData().getDataItemList().toString());
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<sdk.kitso.feedbackmaster.model.Response> call, Throwable throwable) {
                String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                Log.d("FMDIGILAB 1", "Initital Error");
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                reload = () -> loadInitial(loadInitialParams, loadInitialCallback);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, DataItem> loadCallback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, DataItem> loadCallback) {
        networkState.postValue(NetworkState.LOADING);

        apiService.getNextSurveys(loadParams.key).enqueue(new Callback<sdk.kitso.feedbackmaster.model.Response>() {
              @Override
              public void onResponse(Call<sdk.kitso.feedbackmaster.model.Response> call, Response<sdk.kitso.feedbackmaster.model.Response> response) {
                  if(response.isSuccessful()) {
                      //SystemClock.sleep(3000);
                      Log.d("FMDIGILAB 2", "After Loading");
                      loadCallback.onResult(response.body().getData().getDataItemList(), loadParams.key + 1);
                      networkState.postValue(NetworkState.LOADED);
                  } else {
                      networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                  }
              }

              @Override
              public void onFailure(Call<sdk.kitso.feedbackmaster.model.Response> call, Throwable throwable) {
                  String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                  networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                  Log.d("FMDIGILAB 2", "After Error");
                  reload = () -> loadAfter(loadParams, loadCallback);
              }
          }
        );
    }
}
