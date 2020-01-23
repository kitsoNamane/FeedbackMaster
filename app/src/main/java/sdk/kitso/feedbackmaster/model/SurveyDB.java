package sdk.kitso.feedbackmaster.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Survey.class, Branch_old.class, Department.class,
        Profile.class}, version = 8)
public abstract class SurveyDB extends RoomDatabase {
    public abstract SurveyDao surveyDao();
}
