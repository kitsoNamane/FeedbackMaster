package sdk.kitso.feedbackmaster.repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sdk.kitso.feedbackmaster.db.DataItem;

public interface FeedbackMasterSurveyApiService {
    @POST("surveys")
    Call<List<DataItem>> getNextSurveys(@Query("page") int pageNumber);

}
