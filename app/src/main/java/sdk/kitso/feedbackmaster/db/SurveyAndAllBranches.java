package sdk.kitso.feedbackmaster.db;

import java.util.List;

import androidx.room.Relation;

public class SurveyAndAllBranches {
    public int id;
    @Relation(parentColumn = "id", entityColumn = "surveyId", projection = {"name"})
    public List<Branch> branches;

    public List<Branch> getBranches() {
        return this.branches;
    }
}
