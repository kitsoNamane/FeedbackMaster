package sdk.kitso.feedbackmaster.db;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Category implements Serializable {
	public class Data {
		@SerializedName("name")
		private String name;

		@SerializedName("ref")
		private String ref;

		@SerializedName("alias")
		private String alias;

		public void setName(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}

		public void setRef(String ref){
			this.ref = ref;
		}

		public String getRef(){
			return ref;
		}
		public void setAlias(String alias){
			this.alias = alias;
		}

		public String getAlias(){
			return alias;
		}
	}
}