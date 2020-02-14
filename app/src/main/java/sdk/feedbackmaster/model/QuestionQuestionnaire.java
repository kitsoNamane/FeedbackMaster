package sdk.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QuestionQuestionnaire implements Serializable {

	@SerializedName("ref")
	private String ref;

	@SerializedName("name")
	private String name;

	@SerializedName("key")
	private String key;

	public void setRef(String ref){
		this.ref = ref;
	}

	public String getRef(){
		return ref;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setKey(String key){
		this.key = key;
	}

	public String getKey(){
		return key;
	}

	@Override
 	public String toString(){
		return 
			"QuestionQuestionnaire{" +
			"ref = '" + ref + '\'' + 
			",name = '" + name + '\'' + 
			",key = '" + key + '\'' + 
			"}";
		}
}