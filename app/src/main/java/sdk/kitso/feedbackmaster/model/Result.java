package sdk.kitso.feedbackmaster.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("questionQuestionnaire")
	private QuestionQuestionnaire questionQuestionnaire;

	@SerializedName("questionBusiness")
	private QuestionBusiness questionBusiness;

	@SerializedName("questions")
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