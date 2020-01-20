package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchCategory {

	@SerializedName("branchCountryData")
	@Expose
	private BranchCountryData branchCountryData;

	public void setBranchCountryData(BranchCountryData branchCountryData){
		this.branchCountryData = branchCountryData;
	}

	public BranchCountryData getBranchCountryData(){
		return branchCountryData;
	}

	@Override
 	public String toString(){
		return 
			"BranchCategory{" +
			"branchCountryData = '" + branchCountryData + '\'' +
			"}";
		}
}