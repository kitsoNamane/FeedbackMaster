package sdk.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Responses implements Serializable {

	@SerializedName("data")
	private List<QuestionDataItem> data;

	public void setData(List<QuestionDataItem> data){
		this.data = data;
	}

	public List<QuestionDataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"Responses{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}