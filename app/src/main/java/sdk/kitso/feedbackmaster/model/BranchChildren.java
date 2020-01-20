package sdk.kitso.feedbackmaster.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchChildren{

	@SerializedName("data")
	@Expose
	private List<BranchDataItem> data;

	public void setData(List<BranchDataItem> data){
		this.data = data;
	}

	public List<BranchDataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"BranchChildren{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}