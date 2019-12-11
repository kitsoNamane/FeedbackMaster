package sdk.kitso.feedbackmaster.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.db.Survey;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder> {
    public final List<Survey> surveys;

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
        if(survey.getChecked() == true && holder.cardView.isChecked() == true) {
            setVisibility(holder, View.VISIBLE);
        } else {
            holder.cardView.setChecked(survey.getChecked());
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
            this.cardView.setId(survey.getId());
            this.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSurveyItemClickedListener.onItemClicked(cardView, survey);
                }
            });
        }
    }

    interface OnSurveyItemClickedListener {
        public void onItemClicked(View view, Survey survey);
    }
}
