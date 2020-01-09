package sdk.kitso.feedbackmaster.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Category implements Serializable {

	@SerializedName("data")
	@Expose
	private CategoryData category;

	public void setCategoryData(CategoryData category) {
		this.category = category;
	}

	public CategoryData getCategoryData() {
	    return category;
	}

}
