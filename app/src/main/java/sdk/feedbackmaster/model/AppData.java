package sdk.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import sdk.feedbackmaster.Globals;

@Entity(tableName = "app_data")
public class AppData implements Serializable {
    @PrimaryKey(autoGenerate = false)
    int id = Globals.CURRENT_USER_ID;

    @SerializedName("app_has_ran")
    private boolean hasRan = false;

    public boolean hasRan() {
        return hasRan;
    }

    public void setHasRan(boolean hasRan) {
        this.hasRan = hasRan;
    }
}
