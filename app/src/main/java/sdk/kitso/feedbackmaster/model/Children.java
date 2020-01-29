package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Children implements Serializable {

	@SerializedName("data")
    @Expose
	private List<ChildrenDataItem> data;

	public void setData(List<ChildrenDataItem> data){
		this.data = data;
	}

	public List<ChildrenDataItem> getData(){
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