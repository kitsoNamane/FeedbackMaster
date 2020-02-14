package sdk.feedbackmaster.repository.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdk.feedbackmaster.Globals;
import sdk.feedbackmaster.model.NetworkState;
import sdk.feedbackmaster.model.Survey;
import sdk.feedbackmaster.repository.FeedbackMasterSurveyApiService;

public class FeedbackMasterNetworkDataSource extends PageKeyedDataSource<Integer, Survey> {
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
    public void loadInitial(@NonNull LoadInitialParams<Integer> loadInitialParams, @NonNull LoadInitialCallback<Integer, Survey> loadInitialCallback) {
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        apiService.getNextSurveys(Globals.FIRST_PAGE).enqueue(new Callback<sdk.feedbackmaster.model.Response>() {
            @Override
            public void onResponse(Call<sdk.feedbackmaster.model.Response> call, Response<sdk.feedbackmaster.model.Response> response) {
                if(response.isSuccessful() && response.body().isSuccess()) {
                    loadInitialCallback.onResult(response.body().getData().getSurveyList(), null, 2);
                    initialLoading.postValue(NetworkState.LOADED);
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<sdk.feedbackmaster.model.Response> call, Throwable throwable) {
                String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                reload = () -> loadInitial(loadInitialParams, loadInitialCallback);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, Survey> loadCallback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, Survey> loadCallback) {
        networkState.postValue(NetworkState.LOADING);

        apiService.getNextSurveys(loadParams.key).enqueue(new Callback<sdk.feedbackmaster.model.Response>() {
              @Override
              public void onResponse(Call<sdk.feedbackmaster.model.Response> call, Response<sdk.feedbackmaster.model.Response> response) {
                  if(response.isSuccessful()) {
                      //SystemClock.sleep(3000);
                      loadCallback.onResult(response.body().getData().getSurveyList(), loadParams.key + 1);
                      networkState.postValue(NetworkState.LOADED);
                  } else {
                      networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                  }
              }

              @Override
              public void onFailure(Call<sdk.feedbackmaster.model.Response> call, Throwable throwable) {
                  String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                  networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                  reload = () -> loadAfter(loadParams, loadCallback);
              }
          }
        );
    }
}
