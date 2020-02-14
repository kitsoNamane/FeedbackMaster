package sdk.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;

public class DataItem extends FeedbackMasterObject {

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

	@SerializedName("total")
	@Expose
	private Total total;

	@SerializedName("entries")
	@Expose
	private Entries entries;

	@SerializedName("intro")
	private String intro;

	@SerializedName("status")
	private int status;

	@ColumnInfo(name="is_checked")
	private boolean checked = false;

	@SerializedName("business")
    @Expose
	private Business business;

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean getChecked() {
		return this.checked;
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
			"QuestionDataItem{" +
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

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Total getTotal() {
		return total;
	}

	public void setTotal(Total total) {
		this.total = total;
	}
}