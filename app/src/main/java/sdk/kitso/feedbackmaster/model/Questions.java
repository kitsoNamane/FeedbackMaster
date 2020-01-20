package sdk.kitso.feedbackmaster.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Questions{

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