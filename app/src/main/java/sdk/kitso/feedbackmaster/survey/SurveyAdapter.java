package sdk.kitso.feedbackmaster.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.db.Survey;
import sdk.kitso.feedbackmaster.db.SurveyAndAllBranches;
import sdk.kitso.feedbackmaster.db.SurveyAndAllDepartments;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder> {
    public final List<Survey> surveys;
    List<SurveyAndAllBranches> branches_list;
    List<SurveyAndAllDepartments> depts;
    RadioButton checkBox;

    public OnSurveyItemClickedListener onSurveyItemClickedListener;


    public SurveyAdapter(List<Survey> surveys, OnSurveyItemClickedListener onSurveyItemClickedListener) {
        this.surveys = surveys;
        this.onSurveyItemClickedListener = onSurveyItemClickedListener;
    }


    @NonNull
    @Override
    public SurveyAdapter.SurveyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.survey_card, parent, false
        );
        SurveyViewHolder surveyViewHolder = new SurveyViewHolder(view);
        return surveyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SurveyAdapter.SurveyViewHolder holder, final int position) {
        Survey survey = surveys.get(position);
        holder.bind(survey, onSurveyItemClickedListener);
        if (survey.getChecked() == true && holder.cardView.isChecked() == true) {
            setVisibility(holder, View.VISIBLE);
        } else if(survey.getChecked()) {
            survey.setChecked(!survey.getChecked());
            bindDynamicContent(holder, survey);
        }else {
            holder.cardView.setChecked(false);
            setVisibility(holder, View.GONE);
        }
    }


    public static void setVisibility(SurveyAdapter.SurveyViewHolder holder, int VISIBILITY) {
        holder.branches.setVisibility(VISIBILITY);
        holder.departments.setVisibility(VISIBILITY);
        holder.start.setVisibility(VISIBILITY);
    }


    @Override
    public int getItemCount() {
        return surveys.size();
    }

    public class SurveyViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView company;
        MaterialTextView survey;
        MaterialCardView cardView;
        LinearLayout branches;
        LinearLayout departments;
        MaterialButton start;

        public SurveyViewHolder(View view) {
            super(view);
            this.cardView = (MaterialCardView) view;
            this.company = view.findViewById(R.id.company_name);
            this.survey = view.findViewById(R.id.survey_title);
            this.branches = view.findViewById(R.id.branch);
            this.departments = view.findViewById(R.id.department);
            this.start = view.findViewById(R.id.start_survey);
        }

        public void bind(final Survey survey, final OnSurveyItemClickedListener onSurveyItemClickedListener) {
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
    }

    public void bindDynamicContent(SurveyViewHolder holder, Survey survey) {
        survey.setChecked(!survey.getChecked());
        holder.cardView.setChecked(survey.getChecked());
        if (holder.cardView.isChecked() == true && holder.departments.getChildCount() <= 2) {
            branches_list = MainActivity.surveyDB.surveyDao().getBranches(survey.getId());
            depts = MainActivity.surveyDB.surveyDao().getDepartments(survey.getId());
            // Currently O(X^2) complexity
            // find way to speed it up to O(X) complexity
            for (int i = 0; i < branches_list.size(); i++) {
                for (int j = 0; j < branches_list.get(i).getBranches().size(); j++) {
                    checkBox = new RadioButton(holder.cardView.getContext());
                    checkBox.setText(branches_list.get(i).getBranches().get(j).getBranch());
                    checkBox.setTextSize(Float.parseFloat("16"));
                    checkBox.setPadding(10, 10, 10, 10);
                    holder.branches.addView(checkBox);
                }
            }
            for (int i = 0; i < depts.size(); i++) {
                for (int j = 0; j < depts.get(i).getDepartments().size(); j++) {
                    checkBox = new RadioButton(holder.cardView.getContext());
                    checkBox.setText(depts.get(i).getDepartments().get(j).getDept());
                    checkBox.setTextSize(Float.parseFloat("16"));
                    checkBox.setPadding(10, 10, 10, 10);
                    holder.departments.addView(checkBox);
                }
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
