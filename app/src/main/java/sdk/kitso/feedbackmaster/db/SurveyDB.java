package sdk.kitso.feedbackmaster.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Survey.class, Branch_old.class, Department.class,
        Profile.class}, version = 6)
public abstract class SurveyDB extends RoomDatabase {
    public abstract SurveyDao surveyDao();
}
