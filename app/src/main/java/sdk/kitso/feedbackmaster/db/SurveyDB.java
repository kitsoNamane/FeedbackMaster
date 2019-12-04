package sdk.kitso.feedbackmaster.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Survey.class}, version = 1)
public abstract class SurveyDB extends RoomDatabase {
    public abstract SurveyDao surveyDao();
}
