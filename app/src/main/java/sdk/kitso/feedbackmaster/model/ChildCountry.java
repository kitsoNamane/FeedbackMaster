package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

public class ChildCountry {

	@SerializedName("childrenData")
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
			"ChildCountry{" +
			"childrenData = '" + childrenData + '\'' +
			"}";
		}
}