package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Country implements Serializable {
    @SerializedName("data")
    @Expose
    private CountryData country;

    public void setCountryData(CountryData country) {
        this.country = country;
    }

    public CountryData getCountryData() {
        return country;
    }

}

