package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

public class ChildCurrency {

	@SerializedName("code")
	private String code;

	@SerializedName("name")
	private String name;

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
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
			"ChildCurrency{" +
			"code = '" + code + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}