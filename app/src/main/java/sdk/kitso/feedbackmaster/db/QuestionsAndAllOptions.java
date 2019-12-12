package sdk.kitso.feedbackmaster.db;

import java.util.List;

import androidx.room.Relation;

public class QuestionsAndAllOptions {
    public int id;
    @Relation(parentColumn = "id", entityColumn = "questionId", projection = {"option"})
    public List<MultipleChoiceOption> options;
}
