package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Entries implements Serializable {

	@SerializedName("unique")
	private int unique;

	@SerializedName("anonymous")
	private boolean anonymous;

	@SerializedName("hoursdelay")
	private int hoursdelay;

	@SerializedName("total")
	private int total;

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotal() {
		return total;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setUnique(int unique){
		this.unique = unique;
	}

	public int getUnique(){
		return unique;
	}

	public void setHoursdelay(int hoursdelay){
		this.hoursdelay = hoursdelay;
	}

	public int getHoursdelay(){
		return hoursdelay;
	}

	@Override
 	public String toString(){
		return 
			"Entries{" + 
			"unique = '" + unique + '\'' + 
			",hoursdelay = '" + hoursdelay + '\'' + 
			"}";
		}
}