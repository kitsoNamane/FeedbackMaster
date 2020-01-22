package sdk.kitso.feedbackmaster.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Responses{

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