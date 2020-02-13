package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Links implements Serializable {

	@SerializedName("next")
	private String next;

	public void setNext(String next){
		this.next = next;
	}

	public String getNext(){
		return next;
	}

	@Override
 	public String toString(){
		return 
			"Links{" + 
			"next = '" + next + '\'' + 
			"}";
		}
}