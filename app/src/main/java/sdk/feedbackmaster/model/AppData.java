package sdk.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import sdk.feedbackmaster.Globals;

@Entity(tableName="app_data")
public class AppData implements Serializable {
    @PrimaryKey(autoGenerate = false)
    int id = Globals.CURRENT_USER_ID;

    @SerializedName("app_has_ran")
    private boolean hasAppRan = false;

    @SerializedName("app_surveys_tut_complete")
    private boolean surveysTutComplete = false;

    @SerializedName("app_branches_tut_complete")
    private boolean branchesTutComplete = false;

    @SerializedName("app_questionnaire_tut_complete")
    private boolean questionnaireTutComplete = false;

    public boolean isHasAppRan() {
        return hasAppRan;
    }

    public void setHasAppRan(boolean hasAppRan) {
        this.hasAppRan = hasAppRan;
    }

    public boolean isSurveysTutComplete() {
        return surveysTutComplete;
    }

    public void setSurveysTutComplete(boolean surveysTutComplete) {
        this.surveysTutComplete = surveysTutComplete;
    }

    public boolean isBranchesTutComplete() {
        return branchesTutComplete;
    }

    public void setBranchesTutComplete(boolean branchesTutComplete) {
        this.branchesTutComplete = branchesTutComplete;
    }

    public boolean isQuestionnaireTutComplete() {
        return questionnaireTutComplete;
    }

    public void setQuestionnaireTutComplete(boolean questionnaireTutComplete) {
        this.questionnaireTutComplete = questionnaireTutComplete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
