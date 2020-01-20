package sdk.kitso.feedbackmaster.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepartmentChildren {

	@SerializedName("data")
	@Expose
	private List<Object> data;

	public void setData(List<Object> data){
		this.data = data;
	}

	public List<Object> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"DepartmentChildren{" +
			"data = '" + data + '\'' + 
			"}";
		}
}