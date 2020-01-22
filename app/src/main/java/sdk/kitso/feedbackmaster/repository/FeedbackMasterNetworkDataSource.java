package sdk.kitso.feedbackmaster.repository;

<<<<<<< HEAD
import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.util.ArrayList;
import java.util.List;
=======
import android.util.Log;
>>>>>>> pagination

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
<<<<<<< HEAD
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import sdk.kitso.feedbackmaster.db.mock_server.Survey;
import sdk.kitso.feedbackmaster.survey.State;

public class FeedbackMasterNetworkDataSource extends PageKeyedDataSource<Integer, JsonObject> {
    FeedbackMasterSurveyApiService apiService;
    CompositeDisposable compositeDisposable;
    public static MutableLiveData<State> state = new MutableLiveData();
    Completable retryCompletable;
    JsonObject jsonResponse = new JsonObject();
    List<JsonObject> jsonObjectList = new ArrayList<>();

    public FeedbackMasterNetworkDataSource(FeedbackMasterSurveyApiService api, CompositeDisposable compositeDisposable) {
       this.apiService = api;
       this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> loadInitialParams, @NonNull LoadInitialCallback<Integer, JsonObject> loadInitialCallback) {
        updateState(State.LOADING);
        compositeDisposable.add(
            apiService.getNextSurveys(1).subscribe((response) ->{
               updateState(State.DONE);
               jsonResponse.getAsJsonObject(response.body().toString());
               jsonObjectList.add(jsonResponse);
               loadInitialCallback.onResult(jsonObjectList, null, 2);
            })
        );

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, JsonObject> loadCallback) {
        updateState(State.LOADING);
        compositeDisposable.add(
            apiService.getNextSurveys(1).subscribe((response) ->{
                updateState(State.DONE);
                jsonResponse.getAsJsonObject(response.body().toString());
                jsonObjectList.add(jsonResponse);
                loadCallback.onResult(jsonObjectList, loadParams.key-1);
            })
        );
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, JsonObject> loadCallback) {
        updateState(State.LOADING);
        compositeDisposable.add(
            apiService.getNextSurveys(1).subscribe((response) ->{
                updateState(State.DONE);
                jsonResponse.getAsJsonObject(response.body().toString());
                jsonObjectList.add(jsonResponse);
                loadCallback.onResult(jsonObjectList, loadParams.key+1);
            })
        );
    }

    public void updateState(State state) {
        this.state.postValue(state);
    }

    public State getState(State state) {
        return this.state.getValue();
    }

    public void retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                    retryCompletable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe());
        }
    }

    private void setRetry(Action action) {
        if(action == null) {
            retryCompletable = Completable.fromAction(action);
        }
        retryCompletable = null;
    }
    /** @Overrie
    public JsonObject fetchSurveys(int pageNumber) {
        final JsonObject api_response = new JsonObject();
        Call<JsonObject> call = apiService.getNextSurveys(pageNumber);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject>response) {
                if(response.isSuccessful()) {
                    //surveylist.addAll(response.body());
                    api_response.getAsJsonObject(response.body().toString());
=======
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.model.DataItem;

public class FeedbackMasterNetworkDataSource extends PageKeyedDataSource<Integer, DataItem> {
    private static final String TAG = FeedbackMasterNetworkDataSource.class.getSimpleName();
    private FeedbackMasterSurveyApiService apiService;

    private MutableLiveData networkState;
    public Runnable reload;
    private MutableLiveData initialLoading;


    public FeedbackMasterNetworkDataSource(FeedbackMasterSurveyApiService api) {
        this.apiService = api;
        networkState = new MutableLiveData<NetworkState>();
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
        Log.d("FMDIGILAB", "HELLO THE REQUEST IS GOING THROUGH");

        apiService.getNextSurveys(1).enqueue(new Callback<sdk.kitso.feedbackmaster.model.Response>() {
            @Override
            public void onResponse(Call<sdk.kitso.feedbackmaster.model.Response> call, Response<sdk.kitso.feedbackmaster.model.Response> response) {
                if(response.isSuccessful()) {
                    //SystemClock.sleep(3000);
                    Log.d("FMDIGILAB 2", response.message());
                    loadInitialCallback.onResult(response.body().getData().getDataItemList(), null, 2);
                    initialLoading.postValue(NetworkState.LOADED);
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    Log.d("FMDIGILAB 2", response.message());
                    initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
>>>>>>> pagination
                }
            }

            @Override
<<<<<<< HEAD
            public void onFailure(Call<JsonObject>call, Throwable throwable) {
            }
        });
        return api_response;
    }
   */
=======
            public void onFailure(Call<sdk.kitso.feedbackmaster.model.Response> call, Throwable throwable) {
                String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                Log.d("FMDIGILAB 3", errorMessage);
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                reload = () -> loadInitial(loadInitialParams, loadInitialCallback);
            }
        });
        //Log.d("FMDIGILAB 18", networkState.getValue().getMsg());
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, DataItem> loadCallback) {
        Log.d("FMDIGILAB", "LOAD BEFORE RUNNNING");
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, DataItem> loadCallback) {
        Log.d(TAG, "FMDIGILAB 9" + loadParams.key);
        networkState.postValue(NetworkState.LOADING);

        apiService.getNextSurveys(loadParams.key).enqueue(new Callback<sdk.kitso.feedbackmaster.model.Response>() {
              @Override
              public void onResponse(Call<sdk.kitso.feedbackmaster.model.Response> call, Response<sdk.kitso.feedbackmaster.model.Response> response) {
                  if(response.isSuccessful()) {
                      //SystemClock.sleep(3000);
                      Log.d("FMDIGILAB 11", response.message());
                      loadCallback.onResult(response.body().getData().getDataItemList(), loadParams.key + 1);
                      networkState.postValue(NetworkState.LOADED);
                  } else {
                      Log.d("FMDIGILAB 11", response.message());
                      networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                  }
              }

              @Override
              public void onFailure(Call<sdk.kitso.feedbackmaster.model.Response> call, Throwable throwable) {
                  String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                  Log.d("FMDIGILAB 10", errorMessage);
                  networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                  reload = () -> loadAfter(loadParams, loadCallback);
              }
          }
        );
    }

    public void retry() {

    }
>>>>>>> pagination
}
