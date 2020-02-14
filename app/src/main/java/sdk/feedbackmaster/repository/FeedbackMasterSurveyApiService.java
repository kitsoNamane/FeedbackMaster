package sdk.feedbackmaster.repository;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sdk.feedbackmaster.model.AnswerResponse;
import sdk.feedbackmaster.model.QuestionResponse;
import sdk.feedbackmaster.model.QuestionnaireAnswer;
import sdk.feedbackmaster.model.Response;
import sdk.feedbackmaster.model.SearchResponse;

public interface FeedbackMasterSurveyApiService {
    @POST("survey")
    Call<AnswerResponse> sendAnswer(@Body QuestionnaireAnswer questionnaireAnswer);

    @POST("surveys")
    Call<Response> getNextSurveys(@Query("page") int pageNumber);

    @POST("survey/questionnaire")
    Call<QuestionResponse> getQuestions(@Query("b") String surveyReference, @Query("c") String businessReference);

    @POST("surveys/search")
    Call<SearchResponse> searchCompany(@Query("keyword") String searchString);
}
