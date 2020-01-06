package sdk.kitso.feedbackmaster.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Survey.class, Branch.class, Department.class,
        Profile.class}, version = 5)
public abstract class SurveyDB extends RoomDatabase {
    public abstract SurveyDao surveyDao();
}
