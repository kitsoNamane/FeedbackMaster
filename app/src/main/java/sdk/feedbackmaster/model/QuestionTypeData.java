package sdk.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QuestionTypeData implements Serializable {
	@SerializedName("name")
    private String name;
	@SerializedName("reference")
	private String reference;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getReference() {
		return reference;
	}
}
