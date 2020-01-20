package sdk.kitso.feedbackmaster.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Response implements Serializable {

	@SerializedName("success")
	private boolean success;

	@SerializedName("error_code")
	private int errorCode;

	@SerializedName("message")
	@Expose
	private List<String> message;

	@SerializedName("data")
	@Expose
	private Data data;

	@SerializedName("status_code")
	@Expose
	private Object statusCode;

	@SerializedName("posted")
	@Expose
	private Posted posted;

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setErrorCode(int errorCode){
		this.errorCode = errorCode;
	}

	public int getErrorCode(){
		return errorCode;
	}

	public void setMessage(List<String> message){
		this.message = message;
	}

	public List<String> getMessage(){
		return message;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setStatusCode(Object statusCode){
		this.statusCode = statusCode;
	}

	public Object getStatusCode(){
		return statusCode;
	}

	public void setPosted(Posted posted){
		this.posted = posted;
	}

	public Posted getPosted(){
		return posted;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"success = '" + success + '\'' + 
			",error_code = '" + errorCode + '\'' + 
			",message = '" + message + '\'' + 
			",data = '" + data + '\'' + 
			",status_code = '" + statusCode + '\'' + 
			",posted = '" + posted + '\'' + 
			"}";
		}
}