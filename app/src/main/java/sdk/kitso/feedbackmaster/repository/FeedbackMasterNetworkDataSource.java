package sdk.kitso.feedbackmaster.repository;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
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
                }
            }

            @Override
            public void onFailure(Call<JsonObject>call, Throwable throwable) {
            }
        });
        return api_response;
    }
   */
}
