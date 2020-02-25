package sdk.feedbackmaster.views.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.Locale;

import androidx.annotation.NonNull;
import sdk.feedbackmaster.MainActivity;
import sdk.feedbackmaster.R;
import sdk.feedbackmaster.model.FeedbackMasterObject;
import sdk.feedbackmaster.model.Survey;
import sdk.feedbackmaster.views.fragments.survey.SearchSurveysFragmentDirections;
import sdk.feedbackmaster.views.fragments.survey.SurveysFragmentDirections;

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
        ((Survey)obj).setChecked(false);
        survey.setText(((Survey)obj).getName());
        company.setText(((Survey)obj).getBusiness().getBusinessData().getName());
        surveyExpiry.setText(((Survey)obj).getEnds());
        numberOfRespondents.setText(String.format(Locale.getDefault(),"%d", ((Survey)obj).getEntries().getTotal()));
        numberOfQuestions.setText(String.format(Locale.getDefault(),"%d", ((Survey)obj).getTotal().getQuestions()));

        this.cardView.setOnClickListener(v -> gotoQuestionnaire(((Survey)obj)));
    }


    public static SurveyPagedViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_survey, parent, false
        );
        viewId = parent.getId();
        return new SurveyPagedViewHolder(view);
    }

    public void gotoQuestionnaire(Survey item) {
        switch (viewId) {
            case R.id.search_result_list:
                SearchSurveysFragmentDirections.ActionDepartments actionDepartments = SearchSurveysFragmentDirections.actionDepartments(
                        item, null, null, null);
                MainActivity.navController.navigate(actionDepartments);
                break;
            case R.id.survey_list:
            default:
                SurveysFragmentDirections.ActionBranches actionBranches = SurveysFragmentDirections.actionBranches(
                        item, null, null, null);
                MainActivity.navController.navigate(actionBranches);
        }
    }
}
