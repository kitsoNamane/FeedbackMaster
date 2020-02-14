package sdk.feedbackmaster.model;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MultipleChoiceOption implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int questionId;

    private String option;

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getId() {
        return id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getOption() {
        return option;
    }
}
