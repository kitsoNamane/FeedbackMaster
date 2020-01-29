package sdk.kitso.feedbackmaster.survey;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.model.DataItem;

public class SurveyLocalPagedAdapter extends PagedListAdapter<DataItem, RecyclerView.ViewHolder> {
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
            DataItem survey = getItem(position);
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
        DataItem survey = getItem(position);
        if( hasExtraRow() && survey == null) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }


    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
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

    private static DiffUtil.ItemCallback<DataItem> DIFF_CALLBACK =
        new DiffUtil.ItemCallback<DataItem>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            @Override
            public boolean areItemsTheSame(DataItem oldSurvey, DataItem newSurvey) {
                return oldSurvey.getReference() == newSurvey.getReference();
            }

            @Override
            public boolean areContentsTheSame(DataItem oldSurvey, DataItem newSurvey) {
                return oldSurvey.equals(newSurvey);
            }
    };

    interface OnSurveyItemClickedListener {
        void onItemClicked(View view, DataItem survey);
    }
}
