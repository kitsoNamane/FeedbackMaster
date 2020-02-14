package sdk.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Duration implements Serializable {

	@SerializedName("has")
	private int has;

	@SerializedName("max")
	private int max;

	public void setHas(int has){
		this.has = has;
	}

	public int getHas(){
		return has;
	}

	public void setMax(int max){
		this.max = max;
	}

	public int getMax(){
		return max;
	}

	@Override
 	public String toString(){
		return 
			"Duration{" + 
			"has = '" + has + '\'' + 
			",max = '" + max + '\'' + 
			"}";
		}
}