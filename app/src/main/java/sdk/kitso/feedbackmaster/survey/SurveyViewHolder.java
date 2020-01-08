package sdk.kitso.feedbackmaster.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.R;

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

    public void bind(JsonObject survey) {
        this.company.setText(survey.getAsJsonObject("data").toString());
        this.survey.setText(survey.getAsJsonObject("data").getAsJsonArray("data").toString());
        //this.cardView.setId(survey.getId());
    }

    public static SurveyViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.survey_card, parent, false
        );
        return new SurveyViewHolder(view);
    }
}
