package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnswerResponse{

	@SerializedName("returned_result")
	private ReturnedResult returnedResult;

	@SerializedName("success")
	private boolean success;

	@SerializedName("error_code")
	private int errorCode;

	@SerializedName("message")
	private List<Object> message;

	public void setReturnedResult(ReturnedResult returnedResult){
		this.returnedResult = returnedResult;
	}

	public ReturnedResult getReturnedResult(){
		return returnedResult;
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
			"AnswerResponse{" + 
			"returned_result = '" + returnedResult + '\'' + 
			",success = '" + success + '\'' + 
			",error_code = '" + errorCode + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}