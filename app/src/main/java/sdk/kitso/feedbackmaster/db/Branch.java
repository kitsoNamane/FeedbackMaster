package sdk.kitso.feedbackmaster.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Branch {
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
