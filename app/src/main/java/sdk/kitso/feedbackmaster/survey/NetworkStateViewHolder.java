package sdk.kitso.feedbackmaster.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.chip.Chip;
import com.google.android.material.textview.MaterialTextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.R;

public class NetworkStateViewHolder extends RecyclerView.ViewHolder {
    Chip loadMore;
    public NetworkStateViewHolder(@NonNull View view) {
        super(view);
        loadMore = view.findViewById(R.id.load_more);

    }

    public void bind() {
        loadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SurveysFragment.retry();
            }
        });
    }

    public static NetworkStateViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.load_more, parent, false
        );
        return new NetworkStateViewHolder(view);
    }
}
