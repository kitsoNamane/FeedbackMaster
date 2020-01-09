package sdk.kitso.feedbackmaster.db;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Limit implements Serializable {

	@SerializedName("campaign")
	private Campaign campaign;

	@SerializedName("duration")
	private Duration duration;

	@SerializedName("respondent")
	private Respondent respondent;

	@SerializedName("question")
	private int question;

	@SerializedName("questionnaire")
	private Questionnaire questionnaire;

	@SerializedName("units")
	private Units units;

	public void setCampaign(Campaign campaign){
		this.campaign = campaign;
	}

	public Campaign getCampaign(){
		return campaign;
	}

	public void setDuration(Duration duration){
		this.duration = duration;
	}

	public Duration getDuration(){
		return duration;
	}

	public void setRespondent(Respondent respondent){
		this.respondent = respondent;
	}

	public Respondent getRespondent(){
		return respondent;
	}

	public void setQuestion(int question){
		this.question = question;
	}

	public int getQuestion(){
		return question;
	}

	public void setQuestionnaire(Questionnaire questionnaire){
		this.questionnaire = questionnaire;
	}

	public Questionnaire getQuestionnaire(){
		return questionnaire;
	}

	public void setUnits(Units units){
		this.units = units;
	}

	public Units getUnits(){
		return units;
	}

	@Override
 	public String toString(){
		return 
			"Limit{" + 
			"campaign = '" + campaign + '\'' + 
			",duration = '" + duration + '\'' + 
			",respondent = '" + respondent + '\'' + 
			",question = '" + question + '\'' + 
			",questionnaire = '" + questionnaire + '\'' + 
			",units = '" + units + '\'' + 
			"}";
		}
}