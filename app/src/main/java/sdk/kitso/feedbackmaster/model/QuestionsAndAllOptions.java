package sdk.kitso.feedbackmaster.model;

import java.util.List;

import androidx.room.Relation;

public class QuestionsAndAllOptions {
    public int id;
    @Relation(parentColumn = "id", entityColumn = "questionId", projection = {"option"})
    public List<MultipleChoiceOption> options;
    public List<MultipleChoiceOption> getOptions() {
        return this.options;
    }
}
