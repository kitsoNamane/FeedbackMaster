package sdk.feedbackmaster.views.viewholders;

import android.util.Log;
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
        close.setClickable(true);
    }

    @Override
    public void bind(FeedbackMasterObject obj) {
        close.setOnClickListener(v -> {
            Log.d("FMDIGILAB", "Click Click Click");
            obj.getRunnable().run();
        });
    }

    public static WelcomeViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.welcome, parent, false
        );
        return new WelcomeViewHolder(view);
    }
}
