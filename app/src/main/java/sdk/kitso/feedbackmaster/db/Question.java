package sdk.kitso.feedbackmaster.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import sdk.kitso.feedbackmaster.Globals;

@Entity(tableName = "questions")
public class Question {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int surveyId;

    private String question;

    private int type = Globals.RATING_STARS;

    public void setId(int id) {
        this.id = id;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getSurveyId() {
        return this.surveyId;
    }

    public String getQuestion() {
        return this.question;
    }

    public int getType() {
        return this.type;
    }
}
