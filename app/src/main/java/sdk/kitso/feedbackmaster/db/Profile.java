package sdk.kitso.feedbackmaster.db;

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

    String getPhone() {
        return Integer.toString(this.phone);
    }

    String getGender() {
        return this.gender;
    }

    String getAge() {
        return Integer.toString(this.age);
    }
}
