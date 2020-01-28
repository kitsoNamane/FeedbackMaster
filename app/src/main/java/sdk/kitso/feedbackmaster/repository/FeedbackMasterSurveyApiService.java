package sdk.kitso.feedbackmaster.repository;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sdk.kitso.feedbackmaster.model.AnswerResponse;
import sdk.kitso.feedbackmaster.model.QuestionResponse;
import sdk.kitso.feedbackmaster.model.QuestionnaireAnswer;
import sdk.kitso.feedbackmaster.model.Response;

public interface FeedbackMasterSurveyApiService {
    @POST("survey")
    Call<AnswerResponse> sendAnswer(@Body QuestionnaireAnswer questionnaireAnswer);

    @POST("surveys")
    Call<Response> getNextSurveys(@Query("page") int pageNumber);

    @POST("survey/questionnaire")
    Call<QuestionResponse> getQuestions(@Query("b") String surveyReference, @Query("c") String businessReference);
}
