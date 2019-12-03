package sdk.kitso.feedbackmaster;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="profile")
public class Profile {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "phone_number")
    private int phone;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "age_group")
    private String age;

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    int getPhone() {
        return this.phone;
    }

    String getGender() {
        return this.gender;
    }

    String getAge() {
        return this.age;
    }
}
