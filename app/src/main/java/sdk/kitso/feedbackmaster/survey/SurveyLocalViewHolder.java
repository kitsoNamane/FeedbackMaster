package sdk.kitso.feedbackmaster.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.db.Survey;
import sdk.kitso.feedbackmaster.db.SurveyAndAllBranches;
import sdk.kitso.feedbackmaster.db.SurveyAndAllDepartments;

public class SurveyLocalViewHolder extends RecyclerView.ViewHolder {
    MaterialTextView company;
    MaterialTextView survey;
    MaterialCardView cardView;
    Group branch;
    Group department;
    Chip chipItem;
    List<SurveyAndAllBranches> branches_list;
    List<SurveyAndAllDepartments> depts;
    ChipGroup branches;
    ChipGroup departments;
    MaterialButton start;

    public SurveyLocalViewHolder(View view) {
        super(view);
        this.cardView = (MaterialCardView) view;
        this.company = view.findViewById(R.id.survey_title);
        this.survey = view.findViewById(R.id.company_name);
        this.branches = view.findViewById(R.id.branchOld);
        this.departments = view.findViewById(R.id.department);
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

    public void bind(final Survey survey, SurveyLocalPagedAdapter.OnSurveyItemClickedListener onSurveyItemClickedListener) {
        this.company.setText(survey.getCompany());
        this.survey.setText(survey.getSurvey());

        //this.cardView.setId(survey.getId());
        this.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSurveyItemClickedListener.onItemClicked(cardView, survey);
            }
        });

        this.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSurveyItemClickedListener.onItemClicked(start, survey);
            }
        });
    }

    public void bindDynamicContent(Survey survey) {
        survey.setChecked(!survey.getChecked());
        this.cardView.setChecked(survey.getChecked());
        if (this.cardView.isChecked() == true && this.departments.getChildCount() <= 2) {
            branches_list = MainActivity.surveyDB.surveyDao().getBranches(survey.getId());
            depts = MainActivity.surveyDB.surveyDao().getDepartments(survey.getId());
            // Currently O(X^2) complexity
            // find way to speed it up to O(X) complexity
            for (int j = 0; j < branches_list.get(0).getBranchOlds().size(); j++) {
                chipItem = new Chip(this.cardView.getContext());
                chipItem.setText(branches_list.get(0).getBranchOlds().get(j).getBranch());
                chipItem.setCheckable(true);
                chipItem.setCheckedIcon(this.cardView.getContext().getResources().getDrawable(R.drawable.ic_check_black_24dp));
                chipItem.setTextSize(Float.parseFloat("16"));
                //chipItem.setPadding(10, 10, 10, 10);
                this.branches.addView(chipItem);
            }
            for (int j = 0; j < depts.get(0).getDepartments().size(); j++) {
                chipItem = new Chip(this.cardView.getContext());
                chipItem.setText(depts.get(0).getDepartments().get(j).getDept());
                chipItem.setCheckable(true);
                chipItem.setCheckedIcon(this.cardView.getContext().getResources().getDrawable(R.drawable.ic_check_black_24dp));
                chipItem.setTextSize(Float.parseFloat("16"));
                this.departments.addView(chipItem);
            }
        }
        if (this.cardView.isChecked()) {
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
    }

    public void setVisibility(int VISIBILITY) {
        this.branch.setVisibility(VISIBILITY);
        this.department.setVisibility(VISIBILITY);
        this.start.setVisibility(VISIBILITY);
    }
}
