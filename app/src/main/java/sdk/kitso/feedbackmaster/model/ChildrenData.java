package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChildrenData implements Serializable {

	@SerializedName("ref")
	private String ref;

	@SerializedName("name")
	private String name;

	@SerializedName("alias")
	private String alias;

	@SerializedName("dialcode")
	private String dialcode;

	@SerializedName("childCurrency")
    @Expose
	private ChildCurrency childCurrency;

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

	public void setChildCurrency(ChildCurrency childCurrency){
		this.childCurrency = childCurrency;
	}

	public ChildCurrency getChildCurrency(){
		return childCurrency;
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
			"ChildrenData{" +
			"ref = '" + ref + '\'' + 
			",name = '" + name + '\'' + 
			",alias = '" + alias + '\'' + 
			",dialcode = '" + dialcode + '\'' + 
			",childCurrency = '" + childCurrency + '\'' +
			",fullname = '" + fullname + '\'' + 
			",key = '" + key + '\'' + 
			"}";
		}
}