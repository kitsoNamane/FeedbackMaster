package sdk.feedbackmaster.ui.adapters;

import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.feedbackmaster.model.DataItem;
import sdk.feedbackmaster.ui.viewholders.SurveyPagedViewHolder;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<DataItem> searchResult;
    private List<DataItem> removeSearchResult;


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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SurveyPagedViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataItem item = searchResult.get(position);
        ((SurveyPagedViewHolder)holder).bind(item);
    }

    @Override
    public int getItemCount() {
        return searchResult.size();
    }
}
