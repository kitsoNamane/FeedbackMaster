package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Posted implements Serializable {

	@SerializedName("device")
	private String device;

	public void setDevice(String device){
		this.device = device;
	}

	public String getDevice(){
		return device;
	}

	@Override
 	public String toString(){
		return 
			"Posted{" + 
			"device = '" + device + '\'' + 
			"}";
		}
}