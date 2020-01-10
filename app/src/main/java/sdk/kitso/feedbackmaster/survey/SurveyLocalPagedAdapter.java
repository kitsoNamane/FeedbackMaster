package sdk.kitso.feedbackmaster.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.db.Survey;
import sdk.kitso.feedbackmaster.db.SurveyAndAllBranches;
import sdk.kitso.feedbackmaster.db.SurveyAndAllDepartments;

public class SurveyLocalPagedAdapter extends PagedListAdapter<Survey, SurveyLocalPagedAdapter.SurveyLocalViewHolder> {
    List<SurveyAndAllBranches> branches_list;
    List<SurveyAndAllDepartments> depts;
    RadioButton checkBox;
        public SurveyLocalPagedAdapter() {
            super(DIFF_CALLBACK);
        }
    public static void setVisibility(SurveyLocalViewHolder holder, int VISIBILITY) {
        holder.branches.setVisibility(VISIBILITY);
        holder.departments.setVisibility(VISIBILITY);
        holder.start.setVisibility(VISIBILITY);
    }
    @NonNull
    @Override
    public SurveyLocalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.survey_card, parent, false
        );
        SurveyLocalViewHolder surveyLocalViewHolder = new SurveyLocalViewHolder(view);
        return surveyLocalViewHolder;
    }

    @Override
        public void onBindViewHolder(@NonNull SurveyLocalViewHolder holder,
                                     int position) {
            Survey survey = getItem(position);
        holder.bind(survey);
        if (survey.getChecked() == true && holder.cardView.isChecked() == true) {
            setVisibility(holder, View.VISIBLE);
        } else if(survey.getChecked()) {
            survey.setChecked(!survey.getChecked());
        }else {
            holder.cardView.setChecked(false);
            setVisibility(holder, View.GONE);
        }
        }

        private static DiffUtil.ItemCallback<Survey> DIFF_CALLBACK =
                new DiffUtil.ItemCallback<Survey>() {
                    // Concert details may have changed if reloaded from the database,
                    // but ID is fixed.
                    @Override
                    public boolean areItemsTheSame(Survey oldSurvey, Survey newSurvey) {
                        return oldSurvey.getId() == newSurvey.getId();
                    }

                    @Override
                    public boolean areContentsTheSame(Survey oldSurvey,
                                                      Survey newSurvey) {
                        return oldSurvey.equals(newSurvey);
                    }
                };

    public class SurveyLocalViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView company;
        MaterialTextView survey;
        MaterialCardView cardView;
        LinearLayout branches;
        LinearLayout departments;
        MaterialButton start;

        public SurveyLocalViewHolder(View view) {
            super(view);
            this.cardView = (MaterialCardView) view;
            this.company = view.findViewById(R.id.company_name);
            this.survey = view.findViewById(R.id.survey_title);
            this.branches = view.findViewById(R.id.branch);
            this.departments = view.findViewById(R.id.department);
            this.start = view.findViewById(R.id.start_survey);
        }

        public void bind(final Survey survey) {
            this.company.setText(survey.getCompany());
            this.survey.setText(survey.getSurvey());
            //this.cardView.setId(survey.getId());
        }
    }

    public void bindDynamicContent(SurveyLocalViewHolder holder, Survey survey) {
        survey.setChecked(!survey.getChecked());
        holder.cardView.setChecked(survey.getChecked());
        if (holder.cardView.isChecked() == true && holder.departments.getChildCount() <= 2) {
            branches_list = MainActivity.surveyDB.surveyDao().getBranches(survey.getId());
            depts = MainActivity.surveyDB.surveyDao().getDepartments(survey.getId());
            // Currently O(X^2) complexity
            // find way to speed it up to O(X) complexity
            for (int j = 0; j < branches_list.get(0).getBranches().size(); j++) {
                checkBox = new RadioButton(holder.cardView.getContext());
                checkBox.setText(branches_list.get(0).getBranches().get(j).getBranch());
                checkBox.setTextSize(Float.parseFloat("16"));
                checkBox.setPadding(10, 10, 10, 10);
                holder.branches.addView(checkBox);
            }
            for (int j = 0; j < depts.get(0).getDepartments().size(); j++) {
                checkBox = new RadioButton(holder.cardView.getContext());
                checkBox.setText(depts.get(0).getDepartments().get(j).getDept());
                checkBox.setTextSize(Float.parseFloat("16"));
                checkBox.setPadding(10, 10, 10, 10);
                holder.departments.addView(checkBox);
            }
        }
        if (holder.cardView.isChecked()) {
            setVisibility(holder, View.VISIBLE);
        } else {
            setVisibility(holder, View.GONE);
        }
    }
    interface OnSurveyItemClickedListener {
        public void onItemClicked(View view, Survey survey);
    }
}
