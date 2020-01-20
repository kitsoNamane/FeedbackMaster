package sdk.kitso.feedbackmaster.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Department {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int surveyId;

    public String name;

    public void setDepartment(String name, int surveyId) {
        this.name = name;
        this.surveyId = surveyId;
    }

    public String getDept() {
        return this.name;
    }
}
