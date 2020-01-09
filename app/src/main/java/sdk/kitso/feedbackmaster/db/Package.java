package sdk.kitso.feedbackmaster.db;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;


public class Package implements Serializable {
    public class Data {
        @SerializedName("name")
        private String name;

        @SerializedName("ref")
        private String ref;

        @SerializedName("alias")
        private String alias;

        @SerializedName("price")
        private int price;

        @SerializedName("currency")
        private String currency;

        @SerializedName("limit")
        private Limit limit;

        @SerializedName("expiry")
        private Expiry expiry;

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


        public void setExpiry(Expiry expiry){
            this.expiry = expiry;
        }

        public Expiry getExpiry(){
            return expiry;
        }
    }
}
