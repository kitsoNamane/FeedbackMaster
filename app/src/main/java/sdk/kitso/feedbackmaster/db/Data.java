package sdk.kitso.feedbackmaster.db;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

	@SerializedName("data")
	private List<DataItem> dataItemList;

	@SerializedName("meta")
	private Meta meta;

	public void setDataItemList(List<DataItem> dataItemList){
		this.dataItemList = dataItemList;
	}

	public List<DataItem> getDataItemList(){
		return dataItemList;
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

	public void setCurrency(String currency){
		this.currency = currency;
	}

	public String getCurrency(){
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
			"Data{" + 
			"data = '" + dataItemList + '\'' +
			",meta = '" + meta + '\'' +
			"}";
		}
}