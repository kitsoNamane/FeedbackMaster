package sdk.feedbackmaster.views.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.feedbackmaster.model.FeedbackMasterObject;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    private int viewType = 0;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public abstract void bind(FeedbackMasterObject obj);
}
