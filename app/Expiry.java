package null;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Expiry{

	@SerializedName("date")
	private String date;

	@SerializedName("status")
	private boolean status;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Expiry{" + 
			"date = '" + date + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}