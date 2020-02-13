package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Expiry implements Serializable {

	@SerializedName("status")
	private boolean status;

	@SerializedName("date")
	private String date;

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	@Override
 	public String toString(){
		return 
			"Expiry{" + 
			"status = '" + status + '\'' + 
			",date = '" + date + '\'' + 
			"}";
		}
}