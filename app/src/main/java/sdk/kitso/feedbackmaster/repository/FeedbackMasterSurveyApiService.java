package sdk.kitso.feedbackmaster.repository;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sdk.kitso.feedbackmaster.model.Response;
import sdk.kitso.feedbackmaster.model.QuestionResponse;

public interface FeedbackMasterSurveyApiService {
    @POST("surveys")
    Call<Response> getNextSurveys(@Query("page") int pageNumber);

    @POST("survey/questionnaire")
    Call<QuestionResponse> getQuestions(@Query("b") String surveyReference, @Query("c") String businessReference);
}
