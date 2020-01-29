package sdk.kitso.feedbackmaster.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FeedbackMasterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProfile(Profile profile);

    @Query("select * from profile where id = :profile_id")
    Profile getProfile(int profile_id);
}

