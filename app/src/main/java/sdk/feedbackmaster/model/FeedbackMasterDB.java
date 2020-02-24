package sdk.feedbackmaster.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Profile.class, AppData.class}, version = 11)
public abstract class FeedbackMasterDB extends RoomDatabase {
    public abstract FeedbackMasterDao surveyDao();
}
