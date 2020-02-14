package sdk.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class BranchCountryData implements Serializable {

	@SerializedName("ref")
	private String ref;

	@SerializedName("name")
	private String name;

	@SerializedName("alias")
	private String alias;

	@SerializedName("dialcode")
	private String dialcode;

	@SerializedName("branchCurrency")
	@Expose
	private BranchCurrency branchCurrency;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("key")
	private String key;

	public void setRef(String ref){
		this.ref = ref;
	}

	public String getRef(){
		return ref;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setAlias(String alias){
		this.alias = alias;
	}

	public String getAlias(){
		return alias;
	}

	public void setDialcode(String dialcode){
		this.dialcode = dialcode;
	}

	public String getDialcode(){
		return dialcode;
	}

	public void setBranchCurrency(BranchCurrency branchCurrency){
		this.branchCurrency = branchCurrency;
	}

	public BranchCurrency getBranchCurrency(){
		return branchCurrency;
	}

	public void setFullname(String fullname){
		this.fullname = fullname;
	}

	public String getFullname(){
		return fullname;
	}

	public void setKey(String key){
		this.key = key;
	}

	public String getKey(){
		return key;
	}

	@Override
 	public String toString(){
		return 
			"BranchCountryData{" +
			"ref = '" + ref + '\'' + 
			",name = '" + name + '\'' + 
			",alias = '" + alias + '\'' + 
			",dialcode = '" + dialcode + '\'' + 
			",branchCurrency = '" + branchCurrency + '\'' +
			",fullname = '" + fullname + '\'' + 
			",key = '" + key + '\'' + 
			"}";
		}
}