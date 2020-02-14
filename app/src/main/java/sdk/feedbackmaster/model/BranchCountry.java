package sdk.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BranchCountry implements Serializable {

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
			"BranchCountry{" +
			"branchCountryData = '" + branchCountryData + '\'' +
			"}";
		}
}