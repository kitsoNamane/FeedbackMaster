package sdk.feedbackmaster.repository.local_datasource;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import sdk.feedbackmaster.model.AppData;
import sdk.feedbackmaster.model.Profile;

@Database(entities = {Profile.class, AppData.class}, version = 1)
public abstract class FeedbackMasterDatabase extends RoomDatabase {
    public abstract FeedbackMasterDao feedbackMasterDao();

    private static volatile FeedbackMasterDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static FeedbackMasterDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FeedbackMasterDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FeedbackMasterDatabase.class, "feedback_master_app_db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
