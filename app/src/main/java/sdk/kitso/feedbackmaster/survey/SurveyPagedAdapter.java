package sdk.kitso.feedbackmaster.survey;

<<<<<<< HEAD
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
=======
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.model.DataItem;

public class SurveyPagedAdapter extends PagedListAdapter<DataItem, RecyclerView.ViewHolder> {
    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;
    private NetworkState networkState;

    public static final DiffUtil.ItemCallback<DataItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<DataItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull DataItem oldSurvey, @NonNull DataItem newSurvey) {
            return oldSurvey.getReference() == newSurvey.getReference();
        }

        @Override
        public boolean areContentsTheSame(@NonNull DataItem oldSurvey, @NonNull DataItem newSurvey) {
>>>>>>> pagination
            return oldSurvey.equals(newSurvey);
        }
    };

<<<<<<< HEAD
    protected SurveyPagedAdapter() {
=======
    public SurveyPagedAdapter() {
>>>>>>> pagination
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
<<<<<<< HEAD
        if(viewType == DATA_VIEW_TYPE) {
            return SurveyViewHolder.create(parent);
        } else{
            return ListFooterViewHolder.create(parent);
        }
=======
        return SurveyViewHolder.create(parent);
>>>>>>> pagination
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
<<<<<<< HEAD

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
=======
        DataItem item = getItem(position);
        ((SurveyViewHolder)holder).bind(item);
        /**
        if (item.getChecked() == true && ((SurveyViewHolder) holder).cardView.isChecked() == true) {
            ((SurveyViewHolder) holder).setVisibility(View.VISIBLE);
        } else if(item.getChecked()) {
            item.setChecked(!item.getChecked());
        }else {
            ((SurveyViewHolder) holder).cardView.setChecked(false);
            ((SurveyViewHolder) holder).setVisibility(View.GONE);
        }
         */
    }

    /*
     * Default method of RecyclerView.Adapter
    @Override
    public int getItemViewType(int position) {
        if(getItem(position) instanceof QuestionDataItem && getNetworkState()) {
            Log.d("FMDIGILAB 7 POSITION : ", Integer.toString(position));
            return R.layout.survey_card;
        } else {
            Log.d("FMDIGILAB 8 POSITION : ", Integer.toString(position));
            return R.layout.load_more;
        }
    }
     */

    //private NetworkState.Status getNetworkState() {
    private boolean getNetworkState() {
        switch(networkState.getStatus()) {
            case FAILED:
                return false;
            case SUCCESS:
            case RUNNING:
            default:
                return true;
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    interface OnSurveyItemClickedListener {
        public void onItemClicked(View view, DataItem dataItem);
    }

>>>>>>> pagination
}
