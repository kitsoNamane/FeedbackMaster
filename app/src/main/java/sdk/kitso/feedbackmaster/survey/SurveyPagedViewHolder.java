package sdk.kitso.feedbackmaster.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.model.ChildrenDataItem;
import sdk.kitso.feedbackmaster.model.DataItem;

public class SurveyPagedViewHolder extends RecyclerView.ViewHolder {
    MaterialTextView company;
    MaterialTextView survey;
    MaterialCardView cardView;
    MaterialTextView numberOfQuestions;
    MaterialTextView numberOfRespondents;
    MaterialTextView surveyExpiry;
    static int viewId;


    public SurveyPagedViewHolder(@NonNull View view) {
        super(view);
        this.cardView = (MaterialCardView) view;
        this.company = view.findViewById(R.id.company_name);
        this.survey = view.findViewById(R.id.survey_title);
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

        this.cardView.setOnClickListener(v -> gotoQuestionnaire(item));
    }


    public static SurveyPagedViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_survey, parent, false
        );
        viewId = parent.getId();
        return new SurveyPagedViewHolder(view);
    }

    public void gotoQuestionnaire(DataItem item) {
        List<ChildrenDataItem> children = item.getBusiness().getBusinessData().getChildren().getData();
        switch (viewId) {
            case R.id.search_result_list:
                SearchFragmentDirections.ActionDepartments actionDepartments = SearchFragmentDirections.actionDepartments(item, null, null, null);
                MainActivity.navController.navigate(actionDepartments);
                break;
            case R.id.survey_list:
            default:
                SurveysFragmentDirections.ActionBranches actionBranches = SurveysFragmentDirections.actionBranches(item, null, null, null);
                MainActivity.navController.navigate(actionBranches);
        }
    }
}
