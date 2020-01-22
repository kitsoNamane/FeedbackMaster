package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionDataItem {

	@SerializedName("reference")
	private String reference;

	@SerializedName("caption")
	private String caption;

	@SerializedName("responses")
	private Responses responses;

	@SerializedName("type")
	@Expose
	private QuestionType type;

	@SerializedName("question")
	private Question question;

	@SerializedName("answer")
	private String answer;

	@SerializedName("created")
	private String created;

	public void setQuestionType(QuestionType type) {
		this.type = type;
	}

	public QuestionType getQuestionType() {
		return type;
	}

	public void setReference(String reference){
		this.reference = reference;
	}

	public String getReference(){
		return reference;
	}

	public void setCaption(String caption){
		this.caption = caption;
	}

	public String getCaption(){
		return caption;
	}

	public void setResponses(Responses responses){
		this.responses = responses;
	}

	public Responses getResponses(){
		return responses;
	}

	public void setQuestion(Question question){
		this.question = question;
	}

	public Question getQuestion(){
		return question;
	}

	public void setAnswer(String answer){
		this.answer = answer;
	}

	public String getAnswer(){
		return answer;
	}

	public void setCreated(String created){
		this.created = created;
	}

	public String getCreated(){
		return created;
	}

	@Override
 	public String toString(){
		return 
			"QuestionDataItem{" +
			"reference = '" + reference + '\'' + 
			",caption = '" + caption + '\'' + 
			",responses = '" + responses + '\'' + 
			",question = '" + question + '\'' +
			",answer = '" + answer + '\'' + 
			",created = '" + created + '\'' + 
			"}";
		}
}

