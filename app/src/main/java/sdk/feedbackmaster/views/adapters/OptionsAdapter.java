package sdk.feedbackmaster.views.adapters;

import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.feedbackmaster.model.ChildrenDataItem;
import sdk.feedbackmaster.views.viewholders.OptionsViewHolder;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsViewHolder>{
    private List<ChildrenDataItem> questionDataItems;
    private List<ChildrenDataItem> removeQuestionDataItems;


    public void setQuestionDataItems(List<ChildrenDataItem> questionDataItems) {
        this.questionDataItems = questionDataItems;
        this.removeQuestionDataItems = questionDataItems;
        this.notifyDataSetChanged();
    }

    public void clearSearchResult() {
        this.questionDataItems.removeAll(this.removeQuestionDataItems);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public  OptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return OptionsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionsViewHolder holder, int position) {
        holder.bind(questionDataItems.get(position));
    }

    @Override
    public int getItemCount() {
        return questionDataItems.size();
    }
}
