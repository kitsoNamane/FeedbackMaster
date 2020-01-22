package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildCategory {

	@SerializedName("childrenData")
    @Expose
	private ChildrenData childrenData;

	public void setChildrenData(ChildrenData childrenData){
		this.childrenData = childrenData;
	}

	public ChildrenData getChildrenData(){
		return childrenData;
	}

	@Override
 	public String toString(){
		return 
			"ChildCategory{" +
			"childrenData = '" + childrenData + '\'' +
			"}";
		}
}