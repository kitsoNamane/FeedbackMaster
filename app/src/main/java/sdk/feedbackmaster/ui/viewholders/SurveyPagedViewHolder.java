package sdk.feedbackmaster.ui.viewholders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.Locale;

import androidx.annotation.NonNull;
import sdk.feedbackmaster.MainActivity;
import sdk.feedbackmaster.R;
import sdk.feedbackmaster.model.DataItem;
import sdk.feedbackmaster.model.FeedbackMasterObject;
import sdk.feedbackmaster.ui.survey_fragments.SearchFragmentDirections;
import sdk.feedbackmaster.ui.survey_fragments.SurveysFragmentDirections;

public class SurveyPagedViewHolder extends BaseViewHolder {
    MaterialTextView company;
    MaterialTextView survey;
    MaterialCardView cardView;
    MaterialTextView numberOfQuestions;
    MaterialTextView numberOfRespondents;
    MaterialTextView surveyExpiry;
    static int viewId;


    public SurveyPagedViewHolder(@NonNull View view) {
        super(view);
        setViewType(2);
        this.cardView = (MaterialCardView) view;
        this.company = view.findViewById(R.id.company_name);
        this.survey = view.findViewById(R.id.survey_title);
        this.numberOfQuestions = view.findViewById(R.id.number_of_questions);
        this.numberOfRespondents = view.findViewById(R.id.number_of_respondents);
        this.surveyExpiry = view.findViewById(R.id.expiry_date);
    }

    @Override
    public void bind(FeedbackMasterObject obj) {

        ((DataItem)obj).setChecked(false);
        survey.setText(((DataItem)obj).getName());
        company.setText(((DataItem)obj).getBusiness().getBusinessData().getName());
        surveyExpiry.setText(((DataItem)obj).getEnds());
        numberOfRespondents.setText(String.format(Locale.getDefault(),"%d", ((DataItem)obj).getEntries().getTotal()));
        numberOfQuestions.setText(String.format(Locale.getDefault(),"%d", ((DataItem)obj).getTotal().getQuestions()));

        this.cardView.setOnClickListener(v -> gotoQuestionnaire(((DataItem)obj)));
    }


    public static SurveyPagedViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_survey, parent, false
        );
        viewId = parent.getId();
        return new SurveyPagedViewHolder(view);
    }

    public void gotoQuestionnaire(DataItem item) {
        switch (viewId) {
            case R.id.search_result_list:
                Log.d("FMDIGILAB 10", item.toString());
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
