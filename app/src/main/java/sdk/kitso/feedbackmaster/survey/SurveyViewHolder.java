package sdk.kitso.feedbackmaster.survey;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.model.BranchChildren;
import sdk.kitso.feedbackmaster.model.BranchDataItem;
import sdk.kitso.feedbackmaster.model.ChildrenData;
import sdk.kitso.feedbackmaster.model.ChildrenDataItem;
import sdk.kitso.feedbackmaster.model.DataItem;

public class SurveyViewHolder extends RecyclerView.ViewHolder {
    MaterialTextView company;
    MaterialTextView survey;
    MaterialCardView cardView;
    Group branch;
    Group department;
    ChipGroup branches;
    ChipGroup departments;
    Chip chipItem;
    ImageView start;
    MaterialTextView numberOfQuestions;
    MaterialTextView numberOfRespondents;
    MaterialTextView surveyExpiry;


    public SurveyViewHolder(@NonNull View view) {
        super(view);
        this.cardView = (MaterialCardView) view;
        this.company = view.findViewById(R.id.company_name);
        this.survey = view.findViewById(R.id.survey_title);
        this.branches = view.findViewById(R.id.branchOld);
        this.numberOfQuestions = view.findViewById(R.id.number_of_questions);
        this.numberOfRespondents = view.findViewById(R.id.number_of_respondents);
        this.surveyExpiry = view.findViewById(R.id.expiry_date);
    }

    public void bind(DataItem item) {
        item.setChecked(false);
        survey.setText(item.getName());
        company.setText(item.getBusiness().getBusinessData().getName());
        surveyExpiry.setText(item.getEnds());
        numberOfRespondents.setText(Integer.toString(item.getEntries().getTotal()));
        numberOfQuestions.setText(Integer.toString(item.getQuestions().getNumberOfQuestion()));

        this.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onSurveyItemClickedListener.onItemClicked(cardView, item);
                //SurveysFragmentDirections.ActionBranches actionBranches = SurveysFragmentDirections.actionBranches(item);
                //MainActivity.navController.navigate(actionBranches);
                gotoQuestionnaire(item);
            }
        });
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
}
