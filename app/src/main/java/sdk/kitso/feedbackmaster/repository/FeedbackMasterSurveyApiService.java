package sdk.kitso.feedbackmaster.repository;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import sdk.kitso.feedbackmaster.db.Survey;

public interface FeedbackMasterSurveyApiService {
    @POST("surveys")
    Call<List<Survey>> getNextSurveys(@Query("last_item_id") int pageEnd);

}
