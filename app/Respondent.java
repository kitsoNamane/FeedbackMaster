package null;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Respondent{

	@SerializedName("max")
	private int max;

	@SerializedName("has")
	private int has;

	public void setMax(int max){
		this.max = max;
	}

	public int getMax(){
		return max;
	}

	public void setHas(int has){
		this.has = has;
	}

	public int getHas(){
		return has;
	}

	@Override
 	public String toString(){
		return 
			"Respondent{" + 
			"max = '" + max + '\'' + 
			",has = '" + has + '\'' + 
			"}";
		}
}