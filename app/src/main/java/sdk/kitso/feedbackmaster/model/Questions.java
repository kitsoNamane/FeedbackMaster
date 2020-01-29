package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Questions implements Serializable {

	@SerializedName("data")
	private List<QuestionDataItem> data;

	public void setData(List<QuestionDataItem> data){
		this.data = data;
	}

	public List<QuestionDataItem> getData(){
		return data;
	}

	public int getNumberOfQuestion() {
		return data.size();
	}

	@Override
 	public String toString(){
		return 
			"Questions{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}