package sdk.kitso.feedbackmaster.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.R;

public class NetworkStateViewHolder extends RecyclerView.ViewHolder {
    Chip loadMore;
    public NetworkStateViewHolder(@NonNull View view) {
        super(view);
        loadMore = view.findViewById(R.id.load_more);

    }

    public void bind() {
        loadMore.setOnClickListener(v -> SurveysFragment.retry());
    }

    public static NetworkStateViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.load_more, parent, false
        );
        return new NetworkStateViewHolder(view);
    }
}
