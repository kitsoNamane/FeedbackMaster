package sdk.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class QuestionsItem implements Serializable {

	@SerializedName("ref")
	private String ref;

	@SerializedName("answertype")
	@Expose
	private Answertype answertype;

	@SerializedName("question_answered")
	private boolean questionAnswered = false;

	@SerializedName("answers")
	@Expose
	private List<AnswersItem> answers;

	@SerializedName("caption")
	private String caption;

	@SerializedName("questiontype")
	@Expose
	private SurveyQuestiontype surveyQuestiontype;

	public void setRef(String ref){
		this.ref = ref;
	}

	public String getRef(){
		return ref;
	}

	public void setAnswertype(Answertype answertype){
		this.answertype = answertype;
	}

	public Answertype getAnswertype(){
		return answertype;
	}

	public void setAnswers(List<AnswersItem> answers){
		this.answers = answers;
	}

	public List<AnswersItem> getAnswers(){
		return answers;
	}

	public void setCaption(String caption){
		this.caption = caption;
	}

	public String getCaption(){
		return caption;
	}

	public void setSurveyQuestiontype(SurveyQuestiontype surveyQuestiontype){
		this.surveyQuestiontype = surveyQuestiontype;
	}

	public SurveyQuestiontype getSurveyQuestiontype(){
		return surveyQuestiontype;
	}

	public boolean isQuestionAnswered() {
		return questionAnswered;
	}

	public void setQuestionAnswered(boolean questionAnswered) {
		this.questionAnswered = questionAnswered;
	}

	@Override
 	public String toString(){
		return 
			"QuestionsItem{" + 
			"ref = '" + ref + '\'' + 
			",answertype = '" + answertype + '\'' + 
			",answers = '" + answers + '\'' + 
			",caption = '" + caption + '\'' + 
			",questionAnswered = '" + questionAnswered + '\'' +
			",surveyQuestiontype = '" + surveyQuestiontype + '\'' +
			"}";
		}
}