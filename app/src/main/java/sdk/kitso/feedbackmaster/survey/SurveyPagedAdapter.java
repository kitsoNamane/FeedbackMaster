package sdk.kitso.feedbackmaster.survey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.db.DataItem;
import sdk.kitso.feedbackmaster.db.Survey;

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
            return oldSurvey.equals(newSurvey);
        }
    };
    protected SurveyPagedAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_PROGRESS) {
            return NetworkStateViewHolder.create(parent);
        } else{
            return SurveyViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SurveyViewHolder) {
            ((SurveyViewHolder) holder).bindTo(getItem(position));
        } else {
            ((NetworkStateViewHolder) holder).bind(networkState);
        }
    }

    /*
     * Default method of RecyclerView.Adapter
     */
    @Override
    public int getItemViewType(int position) {
        /**if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
         */
        if(getItem(position) instanceof DataItem) {
            return TYPE_ITEM;
        } else {
            return TYPE_PROGRESS;
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
        public void onItemClicked(View view, Survey survey);
    }

}
