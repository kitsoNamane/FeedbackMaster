package sdk.kitso.feedbackmaster.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.textview.MaterialTextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.R;

public class NetworkStateViewHolder extends RecyclerView.ViewHolder {
    ProgressBar progressBar;
    Group group;
    MaterialTextView errorMsg;
    public NetworkStateViewHolder(@NonNull View view) {
        super(view);
        this.setIsRecyclable(false);
        progressBar = view.findViewById(R.id.progressBar);
        group = view.findViewById(R.id.network_error_group);
    }

    public void bind(NetworkState networkState) {
        if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

        if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
            //errorMsg.setVisibility(View.VISIBLE);
            //errorMsg.setText(networkState.getMsg());
            group.setVisibility(View.VISIBLE);
        } else {
            //errorMsg.setVisibility(View.GONE);
            group.setVisibility(View.GONE);
        }
    }

    public static NetworkStateViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.network_error, parent, false
        );
        return new NetworkStateViewHolder(view);
    }
}
