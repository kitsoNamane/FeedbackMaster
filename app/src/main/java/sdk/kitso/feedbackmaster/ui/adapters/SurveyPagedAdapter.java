package sdk.kitso.feedbackmaster.ui.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.model.DataItem;
import sdk.kitso.feedbackmaster.model.NetworkState;
import sdk.kitso.feedbackmaster.ui.viewholders.BaseViewHolder;
import sdk.kitso.feedbackmaster.ui.viewholders.NetworkStateViewHolder;
import sdk.kitso.feedbackmaster.ui.viewholders.SurveyPagedViewHolder;
import sdk.kitso.feedbackmaster.ui.viewholders.WelcomeViewHolder;

public class SurveyPagedAdapter extends PagedListAdapter<DataItem, BaseViewHolder> {
    private static final int HERO = 0;
    private static final int ERROR = 1;
    private static final int SURVEY = 2;
    Runnable retry;
    private NetworkState networkState;

    public void setRetry(Runnable retry) {
        this.retry = retry;
    }

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
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.try_again:
                return NetworkStateViewHolder.create(parent);
            case R.layout.welcome:
                return WelcomeViewHolder.create(parent);
            case R.layout.card_survey:
            default:
                return SurveyPagedViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        switch (holder.getViewType()) {
            case ERROR:
                holder.bind(networkState);
                ((NetworkStateViewHolder)holder).setRunnable(retry);
                break;
            case HERO:
                break;
            case SURVEY:
            default:
                DataItem item = getItem(position);
                holder.bind(item);
        }
    }


   @Override
   public int getItemViewType(int position) {
       if (hasExtraRow() && position == getItemCount() - 1) {
           return R.layout.try_again;
       } else if(position == 0) {
           return R.layout.welcome;
       } else {
           return R.layout.card_survey;
       }
   }

    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean hadExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean hasExtraRow = hasExtraRow();
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }
}
