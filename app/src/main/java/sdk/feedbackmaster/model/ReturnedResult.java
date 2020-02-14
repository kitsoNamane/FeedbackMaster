package sdk.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReturnedResult implements Serializable {

	@SerializedName("canEditMobile")
	private boolean canEditMobile;

	public void setCanEditMobile(boolean canEditMobile){
		this.canEditMobile = canEditMobile;
	}

	public boolean isCanEditMobile(){
		return canEditMobile;
	}

	@Override
 	public String toString(){
		return 
			"ReturnedResult{" + 
			"canEditMobile = '" + canEditMobile + '\'' + 
			"}";
		}
}