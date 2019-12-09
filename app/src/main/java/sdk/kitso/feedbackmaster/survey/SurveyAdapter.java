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
    private SelectionTracker<Long> selectionTracker;



    public SurveyAdapter(List<Survey> surveys) {
        this.surveys = surveys;
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    static class Details extends ItemDetailsLookup.ItemDetails<Long> {
        long position;

        Details() {}

        @Override
        public int getPosition() {
            return (int) position;
        }

        @Nullable
        @Override
        public Long getSelectionKey() {
            return position;
        }

        @Override
        public boolean inSelectionHotspot(@NonNull MotionEvent e) {
            return true;
        }
    }

    static class KeyProvider extends ItemKeyProvider<Long> {
        KeyProvider(RecyclerView.Adapter adapter) {
            super(ItemKeyProvider.SCOPE_MAPPED);
        }

        @Nullable
        @Override
        public Long getKey(int position) {
            return (long) position;
        }

        @Override
        public int getPosition(@NonNull Long key) {
            long value = key;
            return (int) value;
        }
    }

    static class DetailsLookUp extends ItemDetailsLookup<Long> {
        private RecyclerView recyclerView;
        DetailsLookUp(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @NonNull
        @Override
        public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if(view != null) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                if(viewHolder instanceof SurveyViewHolder) {
                    return ((SurveyViewHolder) viewHolder).getItemDetails();
                }
            }
            return null;
        }
    }

    static class Predicate extends SelectionTracker.SelectionPredicate<Long> {
        @Override
        public boolean canSetStateForKey(@NonNull Long key, boolean nextState) {
            return true;
        }

        @Override
        public boolean canSetStateAtPosition(int position, boolean nextState) {
            return true;
        }

        @Override
        public boolean canSelectMultiple() {
            return false;
        }
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
        MaterialTextView company = holder.company;
        MaterialTextView survey = holder.survey;
        final MaterialCardView survey_card = holder.survey_card;
        LinearLayout branches = holder.branches;
        LinearLayout departments = holder.departments;
        MaterialButton start = holder.start;

        company.setText(surveys.get(position).getCompany());
        survey.setText(surveys.get(position).getSurvey());
        /**
        survey_card.setOnClickListener(new View.OnClickListener() {
            boolean checked = false;
            @Override
            public void onClick(View v) {
                checked = !checked;
                survey_card.setChecked(checked);
                if(survey_card.isChecked()) {
                    //Toast.Toast.makeText(v.getContext(), "Hello CardView", LENGTH_LONG).show();
                    //SurveysFragment.surveyList.scrollToPosition(position);
                    survey_card.setCardElevation(15);
                } else{
                }
            }
        });
        */
    }



    @Override
    public int getItemCount() {
        return surveys.size();
    }

    public class SurveyViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView company;
        MaterialTextView survey;
        MaterialCardView survey_card;
        LinearLayout branches;
        LinearLayout departments;
        MaterialButton start;
        private Details details;
        boolean checked = false;

        public SurveyViewHolder(View view) {
            super(view);
            this.company = view.findViewById(R.id.company_name);
            this.survey = view.findViewById(R.id.survey_title);
            //this.survey_card = view.findViewById(R.id.survey_card_layout);
            this.branches = view.findViewById(R.id.branch);
            this.departments = view.findViewById(R.id.department);
            this.start = view.findViewById(R.id.start_survey);
            this.details = new Details();
        }

        Details getItemDetails() {
            return details;
        }
    }

}
