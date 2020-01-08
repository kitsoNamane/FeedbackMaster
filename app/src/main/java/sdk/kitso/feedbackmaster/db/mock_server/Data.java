package sdk.kitso.feedbackmaster.db.mock_server;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("country")
	private Country country;

	@SerializedName("ref")
	private String ref;

	@SerializedName("name")
	private String name;

	@SerializedName("alias")
	private String alias;

	@SerializedName("category")
	private Category category;

	@SerializedName("dialcode")
	private String dialcode;

	@SerializedName("currency")
	private Currency currency;

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

	public void setDialcode(String dialcode){
		this.dialcode = dialcode;
	}

	public String getDialcode(){
		return dialcode;
	}

	public void setCurrency(Currency currency){
		this.currency = currency;
	}

	public Currency getCurrency(){
		return currency;
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
			",name = '" + name + '\'' + 
			",alias = '" + alias + '\'' + 
			",category = '" + category + '\'' + 
			",dialcode = '" + dialcode + '\'' + 
			",currency = '" + currency + '\'' + 
			",fullname = '" + fullname + '\'' + 
			",key = '" + key + '\'' + 
			"}";
		}
}