package sdk.kitso.feedbackmaster.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireAnswer {
    @SerializedName("a")
    private String business;

    @SerializedName("b")
    private String campaign;

    @SerializedName("c")
    private String mobileNumber;

    @SerializedName("d")
    private String timer;

    @SerializedName("e")
    @Expose
    private List<Answer> answers;

    @SerializedName("f")
    private String device;

    @SerializedName("g")
    private String location;

    @SerializedName("h")
    private String averageAge;

    @SerializedName("i")
    private String gender;

    @SerializedName("k")
    private String country;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endDate;

    public QuestionnaireAnswer() {
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answer) {
        this.answers = answer;
    }

    public void setAnswer(Answer answer) {
        if(this.answers == null) {
            this.answers = new ArrayList<>();
        }
        this.answers.add(answer);
    }

    public void removeNullAnswers() {
        List<Answer> transfer = new ArrayList<>();
        for(Answer answer: answers) {
            if(answer.getAnswer() != null) {
                transfer.add(answer);
            }
        }
        answers = transfer;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(String averageAge) {
        this.averageAge = averageAge;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void showMe() {
        for(Answer answer: answers) {
            Log.d("FMDIGILAB 27", "Answer : "+answer.getAnswer()+", Question : "+answer.getQuestion());
        }
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
