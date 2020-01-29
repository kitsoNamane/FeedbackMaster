package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question implements Serializable {
	@PrimaryKey(autoGenerate = true)
	public int id;

	@SerializedName("reference")
	private String reference;

	@SerializedName("title")
	private String title;

	public void setReference(String reference){
		this.reference = reference;
	}

	public String getReference(){
		return reference;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"Question{" + 
			"reference = '" + reference + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}