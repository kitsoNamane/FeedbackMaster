package sdk.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class QuestionType implements Serializable {
	@SerializedName("data")
	@Expose
	private QuestionTypeData questionData;

	public void setQuestionData(QuestionTypeData questionData) {
		this.questionData = questionData;
	}

	public QuestionTypeData getQuestionData() {
		return questionData;
	}
}
