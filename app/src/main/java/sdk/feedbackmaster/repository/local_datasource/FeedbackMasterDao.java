package sdk.feedbackmaster.repository.local_datasource;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import sdk.feedbackmaster.model.AppData;
import sdk.feedbackmaster.model.Profile;

@Dao
public interface FeedbackMasterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProfile(Profile profile);

    @Query("select * from profile where id = :profile_id")
    Profile getProfile(int profile_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDevice(AppData data);

    @Query("select * from app_data where  id = :app_id")
    AppData getAppData(int app_id);
}

