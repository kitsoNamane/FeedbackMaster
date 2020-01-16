package sdk.kitso.feedbackmaster.survey;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.db.Survey;

public class SurveyLocalPagedAdapter extends PagedListAdapter<Survey, RecyclerView.ViewHolder> {
    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;
    private NetworkState networkState;

    public OnSurveyItemClickedListener onSurveyItemClickedListener;

    public SurveyLocalPagedAdapter(OnSurveyItemClickedListener onSurveyItemClickedListener) {
            super(DIFF_CALLBACK);
            this.onSurveyItemClickedListener = onSurveyItemClickedListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_PROGRESS) {
            return NetworkStateViewHolder.create(parent);
        } else{
            return SurveyLocalViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SurveyLocalViewHolder) {
            Survey survey = getItem(position);
            ((SurveyLocalViewHolder)holder).bind(survey, onSurveyItemClickedListener);
            if (survey.getChecked() == true && ((SurveyLocalViewHolder) holder).cardView.isChecked() == true) {
                ((SurveyLocalViewHolder) holder).setVisibility(View.VISIBLE);
            } else if(survey.getChecked()) {
                survey.setChecked(!survey.getChecked());
            }else {
                ((SurveyLocalViewHolder) holder).cardView.setChecked(false);
                ((SurveyLocalViewHolder) holder).setVisibility(View.GONE);
            }
        } else {
            ((NetworkStateViewHolder) holder).bind();
        }

    }

    @Override
    public int getItemViewType(int position) {
        Survey survey = getItem(position);
        if( hasExtraRow() && survey == null) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
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
    private static DiffUtil.ItemCallback<Survey> DIFF_CALLBACK =
        new DiffUtil.ItemCallback<Survey>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            @Override
            public boolean areItemsTheSame(Survey oldSurvey, Survey newSurvey) {
                return oldSurvey.getId() == newSurvey.getId();
            }

            @Override
            public boolean areContentsTheSame(Survey oldSurvey, Survey newSurvey) {
                return oldSurvey.equals(newSurvey);
            }
    };

    interface OnSurveyItemClickedListener {
        public void onItemClicked(View view, Survey survey);
    }
}
