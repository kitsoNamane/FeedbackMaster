package null;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Entries{

	@SerializedName("hoursdelay")
	private int hoursdelay;

	@SerializedName("unique")
	private int unique;

	public void setHoursdelay(int hoursdelay){
		this.hoursdelay = hoursdelay;
	}

	public int getHoursdelay(){
		return hoursdelay;
	}

	public void setUnique(int unique){
		this.unique = unique;
	}

	public int getUnique(){
		return unique;
	}

	@Override
 	public String toString(){
		return 
			"Entries{" + 
			"hoursdelay = '" + hoursdelay + '\'' + 
			",unique = '" + unique + '\'' + 
			"}";
		}
}