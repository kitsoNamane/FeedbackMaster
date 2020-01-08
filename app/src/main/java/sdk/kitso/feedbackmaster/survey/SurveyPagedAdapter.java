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
import com.google.gson.JsonObject;

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

public class SurveyPagedAdapter extends PagedListAdapter<JsonObject, RecyclerView.ViewHolder> {
    private int DATA_VIEW_TYPE = 1;
    private int FOOTER_VIEW_TYPE = 2;
    private State state = State.LOADING;

    public static final DiffUtil.ItemCallback<JsonObject> DIFF_CALLBACK = new DiffUtil.ItemCallback<JsonObject>() {
        @Override
        public boolean areItemsTheSame(@NonNull JsonObject oldSurvey, @NonNull JsonObject newSurvey) {
            return oldSurvey.toString() == newSurvey.toString();
        }

        @Override
        public boolean areContentsTheSame(@NonNull JsonObject oldSurvey, @NonNull JsonObject newSurvey) {
            return oldSurvey.equals(newSurvey);
        }
    };

    protected SurveyPagedAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == DATA_VIEW_TYPE) {
            return SurveyViewHolder.create(parent);
        } else{
            return ListFooterViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
    /**List<SurveyAndAllBranches> branches_list;
    List<SurveyAndAllDepartments> depts;
    RadioButton checkBox;
    private State state = State.LOADING;




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
    */

    @Override
    public int getItemViewType(int position) {
        if(position < super.getItemCount()) {
            return DATA_VIEW_TYPE;
        } else {
            return FOOTER_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        int i = 1;
        if(hasFooter()) {
            i = 1;
        } else {
            i = 0;
        }
        return super.getItemCount() + i;
    }

    public boolean hasFooter() {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR);
    }

    public void setState(State state) {
        this.state = state;
        notifyItemChanged(super.getItemCount());
    }
}
