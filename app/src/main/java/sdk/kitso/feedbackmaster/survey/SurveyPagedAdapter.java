package sdk.kitso.feedbackmaster.survey;

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
            return oldSurvey.equals(newSurvey);
        }
    };

    public SurveyPagedAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SurveyViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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

}
