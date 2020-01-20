package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Currency implements Serializable {

	@SerializedName("name")
	private String name;

	@SerializedName("code")
	private String code;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	@Override
 	public String toString(){
		return 
			"ChildCurrency{" +
			"name = '" + name + '\'' + 
			",code = '" + code + '\'' + 
			"}";
		}
}