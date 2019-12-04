package sdk.kitso.feedbackmaster.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SurveyDao {
    @Insert
    public void addSurvey(Survey survey);

    @Query("select * from surveys")
    public List<Survey> getSurveys();
}
