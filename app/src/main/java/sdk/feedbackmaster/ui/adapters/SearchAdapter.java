package sdk.feedbackmaster.ui.adapters;

import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.feedbackmaster.R;
import sdk.feedbackmaster.model.DataItem;
import sdk.feedbackmaster.model.NetworkState;
import sdk.feedbackmaster.ui.viewholders.BaseViewHolder;
import sdk.feedbackmaster.ui.viewholders.NetworkStateViewHolder;
import sdk.feedbackmaster.ui.viewholders.SurveyPagedViewHolder;
import sdk.feedbackmaster.ui.viewholders.WelcomeViewHolder;

public class SearchAdapter extends RecyclerView.Adapter<BaseViewHolder>{
    private List<DataItem> searchResult;
    private List<DataItem> removeSearchResult;
    private NetworkState networkState;
    private static final int HERO = 0;
    private static final int ERROR = 1;
    private static final int SURVEY = 2;
    Runnable retry;

    public void setRetry(Runnable retry) {
        this.retry = retry;
    }

    public void setSearchResult(List<DataItem> searchResult) {
        this.searchResult = searchResult;
        this.removeSearchResult = searchResult;
        this.notifyDataSetChanged();
    }

    public void clearSearchResult() {
        this.searchResult.removeAll(this.removeSearchResult);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public  BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.try_again:
                Log.d("FMDIGILAB 13", "Creating Error Card ");
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
                DataItem item = searchResult.get(position);
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
        Log.d("FMDIGILAB 13", "Network Setting");
        boolean hadExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean hasExtraRow = hasExtraRow();
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                this.notifyItemRemoved(this.getItemCount());
            } else {
                this.notifyItemInserted(this.getItemCount());
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            this.notifyItemChanged(this.getItemCount() - 1);
        }
    }

    @Override
    public int getItemCount() {
        return searchResult.size();
    }
}
