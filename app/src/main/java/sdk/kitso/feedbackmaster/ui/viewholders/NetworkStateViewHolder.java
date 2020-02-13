package sdk.kitso.feedbackmaster.ui.viewholders;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.Utils;
import sdk.kitso.feedbackmaster.model.FeedbackMasterObject;
import sdk.kitso.feedbackmaster.model.NetworkState;

public class NetworkStateViewHolder extends BaseViewHolder {
    private NetworkState networkState;
    private Group group;
    private CountDownTimer countDownTimer;
    private LottieAnimationView lottieProgressBar;
    private LottieAnimationView wifi;
    private MaterialButton retryBtn;
    private MaterialTextView internetText;
    private Runnable retry;

    public NetworkStateViewHolder(@NonNull View view) {
        super(view);
        setViewType(1);
        retryBtn = view.findViewById(R.id.try_again);
        wifi = view.findViewById(R.id.network_img_error);
        internetText = view.findViewById(R.id.errorMgs);
        lottieProgressBar = view.findViewById(R.id.progressBar);
        toggleVisibility(View.VISIBLE);
    }

    public void toggleVisibility(int VISIBILITY) {
        if(VISIBILITY == View.INVISIBLE) {
            lottieProgressBar.setVisibility(View.VISIBLE);
            wifi.setVisibility(View.INVISIBLE);
            internetText.setVisibility(View.INVISIBLE);
            retryBtn.setVisibility(View.INVISIBLE);

        } else {
            lottieProgressBar.setVisibility(View.INVISIBLE);
            wifi.setVisibility(View.VISIBLE);
            internetText.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
        }
    }

    public void setRunnable(Runnable retry) {
        this.retry = retry;
    }

    @Override
    public void bind(FeedbackMasterObject obj) {
        this.networkState = ((NetworkState)obj);
        retryBtn.setOnClickListener(v -> {
            toggleVisibility(View.INVISIBLE);
            Thread thread = new Thread(
                    retry
            );
            thread.start();
            countDownTimer = new CountDownTimer(3000, 1) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    if(! Utils.isOnline(retryBtn.getContext())) toggleVisibility(View.VISIBLE);
                }
            }.start();
        });
    }

    public static NetworkStateViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.try_again, parent, false
        );
        return new NetworkStateViewHolder(view);
    }
}
