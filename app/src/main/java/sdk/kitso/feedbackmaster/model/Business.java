package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Business implements Serializable {
	@SerializedName("data")
    @Expose
	private BusinessData business;

	public void setBusinessData(BusinessData business) {
		this.business = business;
	}

	public BusinessData getBusinessData() {
		return business;
	}

}

