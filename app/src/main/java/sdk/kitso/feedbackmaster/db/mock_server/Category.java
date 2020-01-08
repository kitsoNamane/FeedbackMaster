package sdk.kitso.feedbackmaster.db.mock_server;

import com.google.gson.annotations.SerializedName;

public class Category{

	@SerializedName("data")
	private Data data;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"Category{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}