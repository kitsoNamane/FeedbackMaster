package sdk.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class SearchResponse implements Serializable {

	@SerializedName("status_code")
	@Expose
	private Object statusCode;

	@SerializedName("data")
    @Expose
    private Data data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("error_code")
	private int errorCode;

	@SerializedName("message")
	@Expose
	private List<Object> message;

	public void setStatusCode(Object statusCode){
		this.statusCode = statusCode;
	}

	public Object getStatusCode(){
		return statusCode;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
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
			"SearchResponse{" + 
			"status_code = '" + statusCode + '\'' + 
			",data = '" + data + '\'' + 
			",success = '" + success + '\'' + 
			",error_code = '" + errorCode + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}