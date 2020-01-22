package sdk.kitso.feedbackmaster.repository;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sdk.kitso.feedbackmaster.model.Response;

public interface FeedbackMasterSurveyApiService {
    @POST("surveys")
    Call<Response> getNextSurveys(@Query("page") int pageNumber);

}
