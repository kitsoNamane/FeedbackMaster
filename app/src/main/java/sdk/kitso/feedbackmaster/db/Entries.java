package sdk.kitso.feedbackmaster.db;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Entries implements Serializable {

	@SerializedName("unique")
	private int unique;

	@SerializedName("hoursdelay")
	private int hoursdelay;

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