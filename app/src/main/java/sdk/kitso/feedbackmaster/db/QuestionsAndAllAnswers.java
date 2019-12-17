package sdk.kitso.feedbackmaster.db;

import java.util.List;

import androidx.room.Relation;

public class QuestionsAndAllAnswers {
    public int id;
    @Relation(parentColumn = "id", entityColumn = "questionId", projection = {"answer"})
    public List<QuestionAnswer> answers;
}
