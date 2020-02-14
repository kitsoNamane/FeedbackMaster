package sdk.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class BranchDataItem implements Serializable {

	@SerializedName("ref")
	private String ref;

	@SerializedName("departmentChildren")
	@Expose
	private DepartmentChildren departmentChildren;

	@SerializedName("name")
	private String name;

	@SerializedName("alias")
	private String alias;

	@SerializedName("branchCountry")
	@Expose
	private BranchCountry branchCountry;

	@SerializedName("branchCategory")
	@Expose
	private BranchCategory branchCategory;

	public void setRef(String ref){
		this.ref = ref;
	}

	public String getRef(){
		return ref;
	}

	public void setDepartmentChildren(DepartmentChildren departmentChildren){
		this.departmentChildren = departmentChildren;
	}

	public DepartmentChildren getDepartmentChildren(){
		return departmentChildren;
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

	public void setBranchCountry(BranchCountry branchCountry){
		this.branchCountry = branchCountry;
	}

	public BranchCountry getBranchCountry(){
		return branchCountry;
	}

	public void setBranchCategory(BranchCategory branchCategory){
		this.branchCategory = branchCategory;
	}

	public BranchCategory getBranchCategory(){
		return branchCategory;
	}

	@Override
 	public String toString(){
		return 
			"BranchDataItem{" +
			"ref = '" + ref + '\'' + 
			",departmentChildren = '" + departmentChildren + '\'' +
			",name = '" + name + '\'' + 
			",alias = '" + alias + '\'' + 
			",branchCountry = '" + branchCountry + '\'' +
			",branchCategory = '" + branchCategory + '\'' +
			"}";
		}
}