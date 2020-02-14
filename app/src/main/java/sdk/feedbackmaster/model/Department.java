package sdk.feedbackmaster.model;


import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Department implements Serializable {
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
