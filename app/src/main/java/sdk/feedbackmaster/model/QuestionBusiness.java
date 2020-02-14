package sdk.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QuestionBusiness implements Serializable {

	@SerializedName("ref")
	private String ref;

	@SerializedName("code")
	private String code;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("name")
	private String name;

	@SerializedName("longitude")
	private double longitude;

	public void setRef(String ref){
		this.ref = ref;
	}

	public String getRef(){
		return ref;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setLatitude(double latitude){
		this.latitude = latitude;
	}

	public double getLatitude(){
		return latitude;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLongitude(double longitude){
		this.longitude = longitude;
	}

	public double getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"QuestionBusiness{" +
			"ref = '" + ref + '\'' + 
			",code = '" + code + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",name = '" + name + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}