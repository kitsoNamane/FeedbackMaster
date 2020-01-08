package null;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("country")
	private Country country;

	@SerializedName("ref")
	private String ref;

	@SerializedName("package")
	private JsonMemberPackage jsonMemberPackage;

	@SerializedName("name")
	private String name;

	@SerializedName("alias")
	private String alias;

	@SerializedName("category")
	private Category category;

	@SerializedName("price")
	private int price;

	@SerializedName("limit")
	private Limit limit;

	@SerializedName("currency")
	private Currency currency;

	@SerializedName("expiry")
	private Expiry expiry;

	@SerializedName("dialcode")
	private String dialcode;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("key")
	private String key;

	public void setCountry(Country country){
		this.country = country;
	}

	public Country getCountry(){
		return country;
	}

	public void setRef(String ref){
		this.ref = ref;
	}

	public String getRef(){
		return ref;
	}

	public void setJsonMemberPackage(JsonMemberPackage jsonMemberPackage){
		this.jsonMemberPackage = jsonMemberPackage;
	}

	public JsonMemberPackage getJsonMemberPackage(){
		return jsonMemberPackage;
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

	public void setCategory(Category category){
		this.category = category;
	}

	public Category getCategory(){
		return category;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setLimit(Limit limit){
		this.limit = limit;
	}

	public Limit getLimit(){
		return limit;
	}

	public void setCurrency(Currency currency){
		this.currency = currency;
	}

	public Currency getCurrency(){
		return currency;
	}

	public void setExpiry(Expiry expiry){
		this.expiry = expiry;
	}

	public Expiry getExpiry(){
		return expiry;
	}

	public void setDialcode(String dialcode){
		this.dialcode = dialcode;
	}

	public String getDialcode(){
		return dialcode;
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
			"Data{" + 
			"country = '" + country + '\'' + 
			",ref = '" + ref + '\'' + 
			",package = '" + jsonMemberPackage + '\'' + 
			",name = '" + name + '\'' + 
			",alias = '" + alias + '\'' + 
			",category = '" + category + '\'' + 
			",price = '" + price + '\'' + 
			",limit = '" + limit + '\'' + 
			",currency = '" + currency + '\'' + 
			",expiry = '" + expiry + '\'' + 
			",dialcode = '" + dialcode + '\'' + 
			",fullname = '" + fullname + '\'' + 
			",key = '" + key + '\'' + 
			"}";
		}
}