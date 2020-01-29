package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Answertype implements Serializable {

	@SerializedName("ref")
	private String ref;

	@SerializedName("name")
	private String name;

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

	@Override
 	public String toString(){
		return 
			"Answertype{" + 
			"ref = '" + ref + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}