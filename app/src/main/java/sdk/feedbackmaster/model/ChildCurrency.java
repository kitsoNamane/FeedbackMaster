package sdk.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChildCurrency implements Serializable {

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