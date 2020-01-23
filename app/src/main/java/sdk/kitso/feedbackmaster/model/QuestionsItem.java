package sdk.kitso.feedbackmaster.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QuestionsItem{

	@SerializedName("ref")
	private String ref;

	@SerializedName("answertype")
	private Answertype answertype;

	@SerializedName("answers")
	private List<AnswersItem> answers;

	@SerializedName("caption")
	private String caption;

	@SerializedName("surveyQuestiontype")
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

	@Override
 	public String toString(){
		return 
			"QuestionsItem{" + 
			"ref = '" + ref + '\'' + 
			",answertype = '" + answertype + '\'' + 
			",answers = '" + answers + '\'' + 
			",caption = '" + caption + '\'' + 
			",surveyQuestiontype = '" + surveyQuestiontype + '\'' +
			"}";
		}
}