package sdk.kitso.feedbackmaster.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Branch_old {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int surveyId;

    public String name;

    public void setBranch(String name, int surveyId) {
        this.name = name;
        this.surveyId = surveyId;
    }

    public String getBranch() {
        return this.name;
    }
}
