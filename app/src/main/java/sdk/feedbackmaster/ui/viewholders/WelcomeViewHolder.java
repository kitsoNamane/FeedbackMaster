package sdk.feedbackmaster.ui.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sdk.feedbackmaster.R;
import sdk.feedbackmaster.model.FeedbackMasterObject;

public class WelcomeViewHolder extends BaseViewHolder {

    ImageView close;

    public WelcomeViewHolder(View view) {
        super(view);
        setViewType(0);
        close = view.findViewById(R.id.close_btn);
    }

    @Override
    public void bind(FeedbackMasterObject obj) {
        close.setOnClickListener(v -> {
        });
    }

    public static WelcomeViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.welcome, parent, false
        );
        return new WelcomeViewHolder(view);
    }
}
