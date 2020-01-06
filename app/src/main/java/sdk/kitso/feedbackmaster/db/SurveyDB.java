package sdk.kitso.feedbackmaster.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Survey.class, Branch.class, Department.class}, version = 2)
public abstract class SurveyDB extends RoomDatabase {
    public abstract SurveyDao surveyDao();
}
