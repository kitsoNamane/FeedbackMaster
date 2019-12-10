package sdk.kitso.feedbackmaster.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SurveyDao {
    @Insert
    public void addSurvey(Survey survey);

    @Insert
    void addBranch(Branch branch);

    @Insert
    void addDepartment(Department dept);

    @Query("select * from surveys")
    public List<Survey> getSurveys();

    @Query("SELECT * from surveys where id = :surveyId")
    public List<SurveyAndAllBranches> getBranches(int surveyId);

    @Query("SELECT * from surveys where id = :surveyId")
    public List<SurveyAndAllDepartments> getDepartments(int surveyId);
}

