package sdk.kitso.feedbackmaster.repository;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.model.QuestionResponse;
import sdk.kitso.feedbackmaster.model.Result;

public class FeedbackMasterQuestions {

    private static Runnable reload;
    private static Result result;

    public static Result getQuestions(String surveyReference, String businessReference) {
        MainActivity.feedbackMasterSurveyApiService.getQuestions(surveyReference, businessReference).enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if(response.isSuccessful()) {
                    //SystemClock.sleep(3000);
                    Log.d("FMDIGILAB 20", response.message());
                    //loadInitialCallback.onResult(response.body().getData().getDataItemList(), null, 2);
                    result = response.body().getResult();
                    //initialLoading.postValue(NetworkState.LOADED);
                    //networkState.postValue(NetworkState.LOADED);
                } else {
                    Log.d("FMDIGILAB 20", response.message());
                    //initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    //networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    result = null;
                    reload = () -> getQuestions(surveyReference, businessReference);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable throwable) {
                String errorMessage = throwable == null ? "unknown error" : throwable.getMessage();
                Log.d("FMDIGILAB 20", errorMessage);
                reload = () -> getQuestions(surveyReference, businessReference);
                result = null;
            }
        });

        return result;
    }
}
