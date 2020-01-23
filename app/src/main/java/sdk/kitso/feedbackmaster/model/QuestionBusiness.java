package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

public class QuestionBusiness {

	@SerializedName("ref")
	private String ref;

	@SerializedName("code")
	private String code;

	@SerializedName("latitude")
	private int latitude;

	@SerializedName("name")
	private String name;

	@SerializedName("longitude")
	private int longitude;

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

	public void setLatitude(int latitude){
		this.latitude = latitude;
	}

	public int getLatitude(){
		return latitude;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLongitude(int longitude){
		this.longitude = longitude;
	}

	public int getLongitude(){
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