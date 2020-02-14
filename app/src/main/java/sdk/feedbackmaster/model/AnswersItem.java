package sdk.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AnswersItem implements Serializable {

	@SerializedName("image")
	private Object image;

	@SerializedName("ref")
	private String ref;

	@SerializedName("caption")
	private String caption;

	public void setImage(Object image){
		this.image = image;
	}

	public Object getImage(){
		return image;
	}

	public void setRef(String ref){
		this.ref = ref;
	}

	public String getRef(){
		return ref;
	}

	public void setCaption(String caption){
		this.caption = caption;
	}

	public String getCaption(){
		return caption;
	}

	@Override
 	public String toString(){
		return 
			"AnswersItem{" + 
			"image = '" + image + '\'' + 
			",ref = '" + ref + '\'' + 
			",caption = '" + caption + '\'' + 
			"}";
		}
}