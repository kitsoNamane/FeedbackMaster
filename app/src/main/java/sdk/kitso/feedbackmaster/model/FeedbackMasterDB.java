package sdk.kitso.feedbackmaster.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Profile.class}, version = 9)
public abstract class FeedbackMasterDB extends RoomDatabase {
    public abstract FeedbackMasterDao surveyDao();
}
