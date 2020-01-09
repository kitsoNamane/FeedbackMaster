package sdk.kitso.feedbackmaster.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class DataItem implements Serializable {

	@SerializedName("name")
	private String name;

	@SerializedName("alias")
	private String alias;

	@SerializedName("reference")
	private String reference;

	@SerializedName("starts")
	private String starts;

	@SerializedName("ends")
	private String ends;

	@SerializedName("entries")
	@Expose
	private Entries entries;

	@SerializedName("status")
	private int status;

	@SerializedName("business")
    @Expose
	private Business business;

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

	public void setReference(String reference){
		this.reference = reference;
	}

	public String getReference(){
		return reference;
	}

	public void setStarts(String starts){
		this.starts = starts;
	}

	public String getStarts(){
		return starts;
	}

	public void setEnds(String ends){
		this.ends = ends;
	}

	public String getEnds(){
		return ends;
	}

	public void setEntries(Entries entries){
		this.entries = entries;
	}

	public Entries getEntries(){
		return entries;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setBusiness(Business business){
		this.business = business;
	}

	public Business getBusiness(){
		return this.business;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"name = '" + name + '\'' + 
			",alias = '" + alias + '\'' + 
			",reference = '" + reference + '\'' + 
			",starts = '" + starts + '\'' + 
			",ends = '" + ends + '\'' + 
			",entries = '" + entries + '\'' + 
			",status = '" + status + '\'' + 
			",business = '" + business + '\'' + 
			"}";
	}

	@Override
	public boolean equals(Object other) {
		return this == other;
	}
}