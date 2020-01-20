package sdk.kitso.feedbackmaster.survey;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
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
        this.departments = view.findViewById(R.id.department);
        this.department = view.findViewById(R.id.department_list);
        this.branch = view.findViewById(R.id.branch_list);
        this.start = view.findViewById(R.id.start_survey);
        this.numberOfQuestions = view.findViewById(R.id.number_of_questions);
        this.numberOfRespondents = view.findViewById(R.id.number_of_respondents);
        this.surveyExpiry = view.findViewById(R.id.expiry_date);
    }

    public void bind(DataItem item, SurveyPagedAdapter.OnSurveyItemClickedListener onSurveyItemClickedListener) {
        survey.setText(item.getName());
        company.setText(item.getBusiness().getBusinessData().getName());
        surveyExpiry.setText(item.getEnds());
        numberOfRespondents.setText(Integer.toString(item.getEntries().getTotal()));
        numberOfQuestions.setText(Integer.toString(item.getQuestions().getNumberOfQuestion()));

        this.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSurveyItemClickedListener.onItemClicked(cardView, item);
            }
        });

        this.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSurveyItemClickedListener.onItemClicked(start, item);
            }
        });
    }

    public static SurveyViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_survey, parent, false
        );
        return new SurveyViewHolder(view);
    }

    public void bindDynamic(DataItem item) {
        item.setChecked(!item.getChecked());
        this.cardView.setChecked(item.getChecked());
        if(this.cardView.isChecked()) {
            renderBranches(item);
            start.setVisibility(View.VISIBLE);
        } else {
            branch.setVisibility(View.GONE);
            department.setVisibility(View.GONE);
            start.setVisibility(View.GONE);
        }
    }

    public void renderBranches(DataItem item) {
        List<ChildrenDataItem> children = item.getBusiness().getBusinessData().getChildren().getData();
        if(children.size() > 0) {
            if(branches.getChildCount() > 0) {
                branch.setVisibility(View.VISIBLE);
                return;
            }

            for(ChildrenDataItem child : children) {
                chipItem = new Chip(this.cardView.getContext());
                Log.d("FMDIGITAL", child.getName());
                chipItem.setText(child.getName());
                chipItem.setCheckable(true);
                chipItem.setCheckedIcon(
                    this.cardView.getContext().getResources().getDrawable(
                            R.drawable.ic_check_black_24dp
                    )
                );

                chipItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        renderDepartments(child);
                    }
                });

                branches.addView(chipItem);
            }
            branch.setVisibility(View.VISIBLE);
        }
    }

    public void renderDepartments(ChildrenDataItem childObj) {
        List<BranchDataItem> children = childObj.getChildren().getData();
        if(children.size() > 0) {
            if(departments.getChildCount() > 0) {
                department.setVisibility(View.VISIBLE);
                return;
            }

            for(BranchDataItem child : children) {
                chipItem = new Chip(this.cardView.getContext());
                Log.d("FMDIGITAL Dep", child.getName());
                chipItem.setText(child.getName());
                chipItem.setCheckable(true);

                chipItem.setCheckedIcon(
                        this.cardView.getContext().getResources().getDrawable(
                                R.drawable.ic_check_black_24dp
                        )
                );
                departments.addView(chipItem);
            }
            department.setVisibility(View.VISIBLE);
        } else {
            departments.removeAllViews();
            department.setVisibility(View.GONE);
        }
    }

    public void setVisibility(int VISIBILITY) {
        this.branch.setVisibility(VISIBILITY);
        this.department.setVisibility(VISIBILITY);
        this.start.setVisibility(VISIBILITY);
    }
}
