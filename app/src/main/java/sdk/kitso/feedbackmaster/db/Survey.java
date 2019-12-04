package sdk.kitso.feedbackmaster.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "surveys")
public class Survey {
    @PrimaryKey
    private int id;

    @ColumnInfo(name="company_name")
    private String company;

    @ColumnInfo(name="survey_title")
    private String survey;

    public void setId(int id) {
        this.id = id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public String getCompany() {
        return this.company;
        //return "SDK Digital Labs";
    }

    public String getSurvey() {
        return this.survey;
        //return "Company Questionaire";
    }

    public int getId() {
        return this.id;
    }
}
