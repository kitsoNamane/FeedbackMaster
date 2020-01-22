package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildrenDataItem {

	@SerializedName("ref")
	private String ref;

	@SerializedName("children")
    @Expose
	private BranchChildren children;

	@SerializedName("name")
	private String name;

	@SerializedName("alias")
	private String alias;

	@SerializedName("childCountry")
	@Expose
	private ChildCountry childCountry;

	@SerializedName("childCategory")
	@Expose
	private ChildCategory childCategory;

	public void setRef(String ref){
		this.ref = ref;
	}

	public String getRef(){
		return ref;
	}

	public void setChildren(BranchChildren children){
		this.children = children;
	}

	public BranchChildren getChildren(){
		return children;
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

	public void setChildCountry(ChildCountry childCountry){
		this.childCountry = childCountry;
	}

	public ChildCountry getChildCountry(){
		return childCountry;
	}

	public void setChildCategory(ChildCategory childCategory){
		this.childCategory = childCategory;
	}

	public ChildCategory getChildCategory(){
		return childCategory;
	}

	@Override
 	public String toString(){
		return 
			"ChildrenDataItem{" +
			"ref = '" + ref + '\'' + 
			",children = '" + children + '\'' + 
			",name = '" + name + '\'' + 
			",alias = '" + alias + '\'' + 
			",childCountry = '" + childCountry + '\'' +
			",childCategory = '" + childCategory + '\'' +
			"}";
		}
}