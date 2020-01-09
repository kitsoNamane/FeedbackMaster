package sdk.kitso.feedbackmaster.db;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Meta implements Serializable {

	@SerializedName("pagination")
	private Pagination pagination;

	public void setPagination(Pagination pagination){
		this.pagination = pagination;
	}

	public Pagination getPagination(){
		return pagination;
	}

	@Override
 	public String toString(){
		return 
			"Meta{" + 
			"pagination = '" + pagination + '\'' + 
			"}";
		}
}