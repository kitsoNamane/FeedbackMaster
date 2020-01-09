package sdk.kitso.feedbackmaster.db;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Business implements Serializable {

	public class Data {
		@SerializedName("name")
		private String name;

		@SerializedName("ref")
		private String ref;

		@SerializedName("alias")
		private String alias;

		@SerializedName("category")
		private Category category;

		@SerializedName("country")
		private Country country;

		@SerializedName("package")
		private Package aPackage;

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
		public void setCategory(Category category){
			this.category = category;
		}

		public Category getCategory(){
			return category;
		}
		public void setCountry(Country country){
			this.country = country;
		}

		public Country getCountry(){
			return country;
		}

		public void setPackage(Package aPackage){
			this.aPackage = aPackage;
		}

		public Package getPackage(){
			return aPackage;
		}
	}
}


