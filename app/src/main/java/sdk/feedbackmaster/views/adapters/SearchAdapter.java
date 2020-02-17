package sdk.feedbackmaster.views.adapters;

import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.feedbackmaster.R;
import sdk.feedbackmaster.model.NetworkState;
import sdk.feedbackmaster.model.Survey;
import sdk.feedbackmaster.views.viewholders.BaseViewHolder;
import sdk.feedbackmaster.views.viewholders.NetworkStateViewHolder;
import sdk.feedbackmaster.views.viewholders.SurveyPagedViewHolder;
import sdk.feedbackmaster.views.viewholders.WelcomeViewHolder;

public class SearchAdapter extends RecyclerView.Adapter<BaseViewHolder>{
    private List<Survey> searchResult;
    private List<Survey> removeSearchResult;
    private NetworkState networkState;
    private static final int HERO = 0;
    private static final int ERROR = 1;
    private static final int SURVEY = 2;
    Runnable retry;

    public void setRetry(Runnable retry) {
        this.retry = retry;
    }

    public void setSearchResult(List<Survey> searchResult) {
        //searchResult.add(0, new Survey());
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
                Survey item = searchResult.get(position);
                holder.bind(item);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.try_again;
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
