package sdk.kitso.feedbackmaster.model;

import java.util.List;

import androidx.room.Relation;

public class SurveyAndAllDepartments {
    public int id;
    @Relation(parentColumn = "id", entityColumn = "surveyId", projection = {"name"})
    public List<Department> departments;

    public List<Department> getDepartments() {
        return this.departments;
    }
}
