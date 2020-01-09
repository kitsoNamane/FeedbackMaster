package sdk.kitso.feedbackmaster.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.textview.MaterialTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.R;

public class NetworkStateViewHolder extends RecyclerView.ViewHolder {
    ProgressBar progressBar;
    MaterialTextView errorMsg;
    public NetworkStateViewHolder(@NonNull View view) {
        super(view);
        progressBar = view.findViewById(R.id.progressBar);
        errorMsg = view.findViewById(R.id.errorMgs);
    }

    public void bind(NetworkState networkState) {
        if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

        if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
            errorMsg.setVisibility(View.VISIBLE);
            errorMsg.setText(networkState.getMsg());
        } else {
            errorMsg.setVisibility(View.GONE);
        }
    }

    public static NetworkStateViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.network_error, parent, false
        );
        return new NetworkStateViewHolder(view);
    }
}
