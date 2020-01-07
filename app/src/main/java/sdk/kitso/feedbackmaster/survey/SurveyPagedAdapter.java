package sdk.kitso.feedbackmaster.survey;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.github.javafaker.Pokemon;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.db.Survey;
import sdk.kitso.feedbackmaster.db.SurveyAndAllBranches;
import sdk.kitso.feedbackmaster.db.SurveyAndAllDepartments;

public class SurveyPagedAdapter extends PagedListAdapter<Survey, SurveyPagedAdapter.SurveyViewHolder> {
    List<SurveyAndAllBranches> branches_list;
    List<SurveyAndAllDepartments> depts;
    RadioButton checkBox;

    public static final DiffUtil.ItemCallback<Survey> DIFF_CALLBACK = new DiffUtil.ItemCallback<Survey>() {
        @Override
        public boolean areItemsTheSame(@NonNull Survey oldSurvey, @NonNull Survey newSurvey) {
            return oldSurvey.getId() == newSurvey.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Survey oldSurvey, @NonNull Survey newSurvey) {
            return oldSurvey.equals(newSurvey);
        }
    };


    protected SurveyPagedAdapter() {
        super(SurveyPagedAdapter.DIFF_CALLBACK);
    }


    @NonNull
    @Override
    public SurveyPagedAdapter.SurveyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.survey_card, parent, false
        );
        SurveyViewHolder surveyViewHolder = new SurveyViewHolder(view);
        return surveyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SurveyViewHolder holder, int position) {
        Survey survey = getItem(position);
        holder.bind(survey);
    }

    public static void setVisibility(SurveyPagedAdapter.SurveyViewHolder holder, int VISIBILITY) {
        holder.branches.setVisibility(VISIBILITY);
        holder.departments.setVisibility(VISIBILITY);
        holder.start.setVisibility(VISIBILITY);
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

        public void bind(Survey survey) {
            this.company.setText(survey.getCompany());
            this.survey.setText(survey.getSurvey());
            //this.cardView.setId(survey.getId());
        }
    }
}
