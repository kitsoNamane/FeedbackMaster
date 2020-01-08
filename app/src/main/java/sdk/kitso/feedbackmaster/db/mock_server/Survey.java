package sdk.kitso.feedbackmaster.db.mock_server;

import com.google.gson.annotations.SerializedName;

public class Survey{

	@SerializedName("reference")
	private String reference;

	@SerializedName("entries")
	private Entries entries;

	@SerializedName("business")
	private Business business;

	@SerializedName("ends")
	private String ends;

	@SerializedName("name")
	private String name;

	@SerializedName("alias")
	private String alias;

	@SerializedName("starts")
	private String starts;

	@SerializedName("status")
	private int status;

	public void setReference(String reference){
		this.reference = reference;
	}

	public String getReference(){
		return reference;
	}

	public void setEntries(Entries entries){
		this.entries = entries;
	}

	public Entries getEntries(){
		return entries;
	}

	public void setBusiness(Business business){
		this.business = business;
	}

	public Business getBusiness(){
		return business;
	}

	public void setEnds(String ends){
		this.ends = ends;
	}

	public String getEnds(){
		return ends;
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

	public void setStarts(String starts){
		this.starts = starts;
	}

	public String getStarts(){
		return starts;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Survey{" + 
			"reference = '" + reference + '\'' + 
			",entries = '" + entries + '\'' + 
			",business = '" + business + '\'' + 
			",ends = '" + ends + '\'' + 
			",name = '" + name + '\'' + 
			",alias = '" + alias + '\'' + 
			",starts = '" + starts + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}