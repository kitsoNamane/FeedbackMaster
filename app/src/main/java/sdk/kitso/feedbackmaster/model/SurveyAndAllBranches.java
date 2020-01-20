package sdk.kitso.feedbackmaster.model;

import java.util.List;

import androidx.room.Relation;

public class SurveyAndAllBranches {
    public int id;
    @Relation(parentColumn = "id", entityColumn = "surveyId", projection = {"name"})
    public List<Branch_old> branchOlds;

    public List<Branch_old> getBranchOlds() {
        return this.branchOlds;
    }
}
