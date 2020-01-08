package sdk.kitso.feedbackmaster.repository;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import sdk.kitso.feedbackmaster.db.Survey;

public interface FeedbackMasterSurveyApiService {
    @POST("/api/v1/surveys")
    Single<Response> getNextSurveys(@Query("page") int pageNumber);
}
