package sdk.kitso.feedbackmaster.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ProfileDao {
    @Insert
    public void addProfile(Profile profile);

    @Query("select * from profile")
    public List<Profile> getProfile();
}
