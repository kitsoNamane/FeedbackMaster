package sdk.kitso.feedbackmaster.db;

import java.util.List;

import androidx.room.Relation;

public class SurveyAndAllQuestions {
    public int id;
    @Relation(parentColumn = "id", entityColumn = "surveyId", projection = {"question"})
    public List<Question> questions;
}
