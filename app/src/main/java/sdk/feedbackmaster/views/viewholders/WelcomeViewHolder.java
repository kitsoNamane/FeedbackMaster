package sdk.feedbackmaster.views.viewholders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import sdk.feedbackmaster.R;
import sdk.feedbackmaster.model.FeedbackMasterObject;

public class WelcomeViewHolder extends BaseViewHolder {
    MaterialButton tutorials;
    ImageView close;

    public WelcomeViewHolder(View view) {
        super(view);
        setViewType(0);
        tutorials = view.findViewById(R.id.enable_tutorials);
        close = view.findViewById(R.id.close_btn);
        close.setVisibility(View.GONE);
        close.setClickable(true);
    }

    @Override
    public void bind(FeedbackMasterObject obj) {
        close.setOnClickListener(v -> {
            Log.d("FMDIGILAB", "Click Click Click");
            obj.getRunnable().run();
        });
    }

    public void setTutorials(Runnable runnable) {
        Log.d("AppData", "Click Click Click");
        tutorials.setOnClickListener(v -> {
            runnable.run();
        });
    }

    public static WelcomeViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.welcome, parent, false
        );
        return new WelcomeViewHolder(view);
    }
}
