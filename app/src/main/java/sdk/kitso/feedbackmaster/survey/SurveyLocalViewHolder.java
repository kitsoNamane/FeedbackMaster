package sdk.kitso.feedbackmaster.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.model.DataItem;

public class SurveyLocalViewHolder extends RecyclerView.ViewHolder {
    MaterialTextView company;
    MaterialTextView survey;
    MaterialCardView cardView;
    Group branch;
    Group department;
    MaterialButton start;

    public SurveyLocalViewHolder(View view) {
        super(view);
        this.cardView = (MaterialCardView) view;
        this.company = view.findViewById(R.id.survey_title);
        this.survey = view.findViewById(R.id.company_name);
        this.branch = view.findViewById(R.id.department_list);
        this.department = view.findViewById(R.id.branch_list);
        this.start = view.findViewById(R.id.start_survey);
    }

    public static SurveyLocalViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_survey, parent, false
        );
        return new SurveyLocalViewHolder(view);
    }

    public void bind(final DataItem survey, SurveyLocalPagedAdapter.OnSurveyItemClickedListener onSurveyItemClickedListener) {
        this.company.setText(survey.getBusiness().getBusinessData().getName());
        this.survey.setText(survey.getName());

        //this.cardView.setId(survey.getId());
        this.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onSurveyItemClickedListener.onItemClicked(cardView, survey);
            }
        });

        this.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onSurveyItemClickedListener.onItemClicked(start, survey);
            }
        });
    }

    /**
    public void bindDynamicContent(Survey survey) {
        survey.setChecked(!survey.getChecked());
        this.cardView.setChecked(survey.getChecked());
        
        if (this.cardView.isChecked()) {
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
    }
     */

    public void setVisibility(int VISIBILITY) {
        this.branch.setVisibility(VISIBILITY);
        this.department.setVisibility(VISIBILITY);
        this.start.setVisibility(VISIBILITY);
    }
}
