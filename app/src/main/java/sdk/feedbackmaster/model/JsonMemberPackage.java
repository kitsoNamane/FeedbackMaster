package sdk.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JsonMemberPackage implements Serializable {

	@SerializedName("data")
	private Data data;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"JsonMemberPackage{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}