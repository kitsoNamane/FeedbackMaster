package sdk.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {

	@SerializedName("questionnaire")
	@Expose
	private QuestionQuestionnaire questionQuestionnaire;

	@SerializedName("business")
	@Expose
	private QuestionBusiness questionBusiness;

	@SerializedName("questions")
	@Expose
	private List<QuestionsItem> questions;

	public void setQuestionQuestionnaire(QuestionQuestionnaire questionQuestionnaire){
		this.questionQuestionnaire = questionQuestionnaire;
	}

	public QuestionQuestionnaire getQuestionQuestionnaire(){
		return questionQuestionnaire;
	}

	public void setQuestionBusiness(QuestionBusiness questionBusiness){
		this.questionBusiness = questionBusiness;
	}

	public QuestionBusiness getQuestionBusiness(){
		return questionBusiness;
	}

	public void setQuestions(List<QuestionsItem> questions){
		this.questions = questions;
	}

	public List<QuestionsItem> getQuestions(){
		return questions;
	}

	@Override
 	public String toString(){
		return 
			"Result{" + 
			"questionQuestionnaire = '" + questionQuestionnaire + '\'' +
			",questionBusiness = '" + questionBusiness + '\'' +
			",questions = '" + questions + '\'' + 
			"}";
		}
}