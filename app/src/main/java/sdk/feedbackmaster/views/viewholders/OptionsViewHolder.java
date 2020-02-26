package sdk.feedbackmaster.views.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.feedbackmaster.R;
import sdk.feedbackmaster.model.ChildrenDataItem;

public class OptionsViewHolder extends RecyclerView.ViewHolder {
    MaterialButton option;

    public OptionsViewHolder(@NonNull View itemView) {
        super(itemView);
        option = (MaterialButton)itemView;
    }

    public void bind(ChildrenDataItem item) {
    }

    public static OptionsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.option_item, parent, false
        );
        return new OptionsViewHolder(view);
    }
}
