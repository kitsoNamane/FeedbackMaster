package sdk.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class BranchChildren implements Serializable {

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