package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class QuestionResponse implements Serializable {

	@SerializedName("result")
	private Result result;

	@SerializedName("success")
	private boolean success;

	@SerializedName("error_code")
	private int errorCode;

	@SerializedName("message")
	private List<Object> message;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

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

	public void setMessage(List<Object> message){
		this.message = message;
	}

	public List<Object> getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"QuestionResponse{" + 
			"result = '" + result + '\'' + 
			",success = '" + success + '\'' + 
			",error_code = '" + errorCode + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}