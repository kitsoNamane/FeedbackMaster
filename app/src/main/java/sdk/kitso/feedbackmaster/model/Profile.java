package sdk.kitso.feedbackmaster.model;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import sdk.kitso.feedbackmaster.Globals;

@Entity(tableName="profile")
public class Profile implements Serializable {
    @PrimaryKey(autoGenerate = false)
    int id = Globals.CURRENT_USER_ID;

    @ColumnInfo(name = "phone_number")
    private int phone;

    @ColumnInfo(name = "profile_set")
    private boolean profile = false;

    @ColumnInfo(name = "surveys_completed")
    private int numberOfSurveysCompleted;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "age_group")
    private int age;

    public void setId(int id) {
        this.id = id;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setProfile(boolean profile) {
        this.profile = profile;
    }
    public void setNumberOfSurveysCompleted(int numberOfSurveysCompleted) {
        this.numberOfSurveysCompleted = numberOfSurveysCompleted;
    }

    public int getNumberOfSurveysCompleted() {
        return numberOfSurveysCompleted;
    }

    public int getPhone() {
        return this.phone;
    }
    public boolean getProfile() {
        return this.profile;
    }
    public int getId() {
        return this.id;
    }
    public String getGender() {
        return this.gender;
    }
    public int getAge() {
        return this.age;
    }
}
