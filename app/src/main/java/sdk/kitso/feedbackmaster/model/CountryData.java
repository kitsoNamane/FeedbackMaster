package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryData {
    @SerializedName("name")
    private String name;

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("alias")
    private String alias;

    @SerializedName("key")
    private String key;

    @SerializedName("dialcode")
    private String dialcode;

    @SerializedName("currency")
    @Expose
    private Currency currency;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setFullname(String fullname){
        this.fullname = fullname;
    }

    public String getFullname(){
        return fullname;
    }
    public void setAlias(String alias){
        this.alias = alias;
    }

    public String getAlias(){
        return alias;
    }
    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
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
}
