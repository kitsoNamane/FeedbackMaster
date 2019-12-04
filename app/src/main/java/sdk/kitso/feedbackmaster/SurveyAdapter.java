package sdk.kitso.feedbackmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.db.Survey;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder> {
    public static List<Survey> surveys;

    public SurveyAdapter(List<Survey> surveys) {
        this.surveys = surveys;
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
    public void onBindViewHolder(@NonNull SurveyAdapter.SurveyViewHolder holder, int position) {
        MaterialTextView company = holder.company;
        MaterialTextView survey = holder.survey;
        company.setText(surveys.get(position).getCompany());
        survey.setText(surveys.get(position).getSurvey());
    }

    @Override
    public int getItemCount() {
        return surveys.size();
    }

    public class SurveyViewHolder extends RecyclerView.ViewHolder{
        MaterialTextView company;
        MaterialTextView survey;

        public SurveyViewHolder(View view) {
            super(view);
            this.company = view.findViewById(R.id.company_name);
            this.survey = view.findViewById(R.id.survey_title);
        }
    }
}
