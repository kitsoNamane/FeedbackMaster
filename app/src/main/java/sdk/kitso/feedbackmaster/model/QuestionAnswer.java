package sdk.kitso.feedbackmaster.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class QuestionAnswer {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int questionId;

    private String answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
