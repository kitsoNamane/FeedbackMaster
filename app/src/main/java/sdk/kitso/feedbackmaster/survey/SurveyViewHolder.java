package sdk.kitso.feedbackmaster.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textview.MaterialTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.db.DataItem;

public class SurveyViewHolder extends RecyclerView.ViewHolder {
    MaterialTextView company;
    MaterialTextView survey;
    public SurveyViewHolder(@NonNull View view) {
        super(view);
        this.company = view.findViewById(R.id.company_name);
        this.survey = view.findViewById(R.id.survey_title);
    }

    public void bindTo(DataItem item) {
        survey.setText(item.getName());
        company.setText(item.getBusiness().getBusinessData().getName());
    }

    public static SurveyViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_survey, parent, false
        );
        return new SurveyViewHolder(view);
    }
}
