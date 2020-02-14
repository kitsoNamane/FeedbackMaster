package sdk.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

	@SerializedName("data")
	@Expose
	private List<Survey> surveyList;

	@SerializedName("meta")
    @Expose
	private Meta meta;

	public void setSurveyList(List<Survey> surveyList){
		this.surveyList = surveyList;
	}

	public List<Survey> getSurveyList(){
		return surveyList;
	}

	public void setMeta(Meta meta){
		this.meta = meta;
	}

	public Meta getMeta(){
		return meta;
	}

	/**
	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setChildCurrency(String currency){
		this.currency = currency;
	}

	public String getChildCurrency(){
		return currency;
	}

	public void setLimit(Limit limit){
		this.limit = limit;
	}

	public Limit getLimit(){
		return limit;
	}

	public void setExpiry(Expiry expiry){
		this.expiry = expiry;
	}

	public Expiry getExpiry(){
		return expiry;
	}
	 */

	@Override
 	public String toString(){
		return 
			"ChildrenData{" +
			"data = '" + surveyList + '\'' +
			",meta = '" + meta + '\'' +
			"}";
		}
}