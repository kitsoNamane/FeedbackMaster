package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryData implements Serializable {
	@SerializedName("name")
	private String name;

	@SerializedName("ref")
	private String ref;

	@SerializedName("alias")
	private String alias;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setRef(String ref){
		this.ref = ref;
	}

	public String getRef(){
		return ref;
	}
	public void setAlias(String alias){
		this.alias = alias;
	}

	public String getAlias(){
		return alias;
	}
}
