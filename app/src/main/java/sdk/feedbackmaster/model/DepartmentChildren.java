package sdk.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DepartmentChildren implements Serializable {

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