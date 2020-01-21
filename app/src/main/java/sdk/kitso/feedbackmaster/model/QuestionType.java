package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionType {
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
