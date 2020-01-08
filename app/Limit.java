package null;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Limit{

	@SerializedName("duration")
	private Duration duration;

	@SerializedName("questionnaire")
	private Questionnaire questionnaire;

	@SerializedName("question")
	private int question;

	@SerializedName("campaign")
	private Campaign campaign;

	@SerializedName("respondent")
	private Respondent respondent;

	@SerializedName("units")
	private Units units;

	public void setDuration(Duration duration){
		this.duration = duration;
	}

	public Duration getDuration(){
		return duration;
	}

	public void setQuestionnaire(Questionnaire questionnaire){
		this.questionnaire = questionnaire;
	}

	public Questionnaire getQuestionnaire(){
		return questionnaire;
	}

	public void setQuestion(int question){
		this.question = question;
	}

	public int getQuestion(){
		return question;
	}

	public void setCampaign(Campaign campaign){
		this.campaign = campaign;
	}

	public Campaign getCampaign(){
		return campaign;
	}

	public void setRespondent(Respondent respondent){
		this.respondent = respondent;
	}

	public Respondent getRespondent(){
		return respondent;
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
			"duration = '" + duration + '\'' + 
			",questionnaire = '" + questionnaire + '\'' + 
			",question = '" + question + '\'' + 
			",campaign = '" + campaign + '\'' + 
			",respondent = '" + respondent + '\'' + 
			",units = '" + units + '\'' + 
			"}";
		}
}