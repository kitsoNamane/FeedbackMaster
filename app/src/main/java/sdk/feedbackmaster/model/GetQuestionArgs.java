package sdk.feedbackmaster.model;

public class GetQuestionArgs {
    private String surveyReference;
    private String businessReference;

    public GetQuestionArgs(String surveyReference, String businessReference) {
        this.surveyReference = surveyReference;
        this.businessReference = businessReference;
    }

    public String getSurveyReference() {
        return surveyReference;
    }

    public void setSurveyReference(String surveyReference) {
        this.surveyReference = surveyReference;
    }

    public String getBusinessReference() {
        return businessReference;
    }

    public void setBusinessReference(String businessReference) {
        this.businessReference = businessReference;
    }
}
