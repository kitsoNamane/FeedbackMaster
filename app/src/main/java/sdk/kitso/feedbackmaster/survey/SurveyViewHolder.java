package sdk.kitso.feedbackmaster.survey;

<<<<<<< HEAD
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.R;
=======
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.model.ChildrenDataItem;
import sdk.kitso.feedbackmaster.model.DataItem;
>>>>>>> pagination

public class SurveyViewHolder extends RecyclerView.ViewHolder {
    MaterialTextView company;
    MaterialTextView survey;
    MaterialCardView cardView;
<<<<<<< HEAD
    LinearLayout branches;
    LinearLayout departments;
    MaterialButton start;

    public SurveyViewHolder(View view) {
=======
    ChipGroup branches;
    MaterialTextView numberOfQuestions;
    MaterialTextView numberOfRespondents;
    MaterialTextView surveyExpiry;


    public SurveyViewHolder(@NonNull View view) {
>>>>>>> pagination
        super(view);
        this.cardView = (MaterialCardView) view;
        this.company = view.findViewById(R.id.company_name);
        this.survey = view.findViewById(R.id.survey_title);
<<<<<<< HEAD
        this.branches = view.findViewById(R.id.branch);
        this.departments = view.findViewById(R.id.department);
        this.start = view.findViewById(R.id.start_survey);
    }

    public void bind(JsonObject survey) {
        this.company.setText(survey.getAsJsonObject("data").toString());
        this.survey.setText(survey.getAsJsonObject("data").getAsJsonArray("data").toString());
        //this.cardView.setId(survey.getId());
    }

    public static SurveyViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.survey_card, parent, false
        );
        return new SurveyViewHolder(view);
    }
=======
        this.branches = view.findViewById(R.id.branchOld);
        this.numberOfQuestions = view.findViewById(R.id.number_of_questions);
        this.numberOfRespondents = view.findViewById(R.id.number_of_respondents);
        this.surveyExpiry = view.findViewById(R.id.expiry_date);
    }

    public void bind(DataItem item) {
        Log.d("FMDIGILAB", "Question : "+item.getQuestions().getNumberOfQuestion());
        item.setChecked(false);
        survey.setText(item.getName());
        company.setText(item.getBusiness().getBusinessData().getName());
        surveyExpiry.setText(item.getEnds());
        numberOfRespondents.setText(Integer.toString(item.getEntries().getTotal()));
        numberOfQuestions.setText(Integer.toString(item.getQuestions().getNumberOfQuestion()));

        this.cardView.setOnClickListener(v -> gotoQuestionnaire(item));
    }


    public static SurveyViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_survey, parent, false
        );
        return new SurveyViewHolder(view);
    }

    public void gotoQuestionnaire(DataItem item) {
        List<ChildrenDataItem> children = item.getBusiness().getBusinessData().getChildren().getData();
        Log.d("FMDIGILAB", "NUMBER OF Branches : "+children.size());
        if(children.size() > 0) {
            // goto select Branches and/or departments
            SurveysFragmentDirections.ActionBranches actionBranches = SurveysFragmentDirections.actionBranches(item);
            MainActivity.navController.navigate(actionBranches);
        } else {
            // go straight to questionnaire
            SurveysFragmentDirections.ActionStartSurvey actionStartSurvey = SurveysFragmentDirections.actionStartSurvey(item);
            MainActivity.navController.navigate(actionStartSurvey);
        }
    }
>>>>>>> pagination
}
