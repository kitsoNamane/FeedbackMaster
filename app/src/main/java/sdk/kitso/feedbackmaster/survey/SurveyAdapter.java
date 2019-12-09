package sdk.kitso.feedbackmaster.survey;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.db.Survey;

import static android.widget.Toast.*;

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
        holder.bind(surveys.get(position), onSurveyItemClickedListener);
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
        boolean checked = false;

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
                    onSurveyItemClickedListener.onItemClicked(survey);
                }
            });
        }
    }

    interface OnSurveyItemClickedListener {
        public void onItemClicked(Survey survey);
    }
}
