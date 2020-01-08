package sdk.kitso.feedbackmaster.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.R;

public class ListFooterViewHolder extends RecyclerView.ViewHolder {
    public ListFooterViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public static ListFooterViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.survey_card, parent, false
        );
        return new ListFooterViewHolder(view);
    }
}
