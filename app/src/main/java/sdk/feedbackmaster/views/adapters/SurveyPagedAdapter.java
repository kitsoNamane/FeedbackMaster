package sdk.feedbackmaster.views.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import sdk.feedbackmaster.R;
import sdk.feedbackmaster.model.FeedbackMasterObject;
import sdk.feedbackmaster.model.NetworkState;
import sdk.feedbackmaster.model.Survey;
import sdk.feedbackmaster.views.viewholders.BaseViewHolder;
import sdk.feedbackmaster.views.viewholders.NetworkStateViewHolder;
import sdk.feedbackmaster.views.viewholders.SurveyPagedViewHolder;
import sdk.feedbackmaster.views.viewholders.WelcomeViewHolder;

public class SurveyPagedAdapter extends PagedListAdapter<Survey, BaseViewHolder> {
    private static final int HERO = 0;
    private static final int ERROR = 1;
    private static final int SURVEY = 2;
    Runnable retry;
    private Runnable tutorials;
    private NetworkState networkState;
    private boolean hasAppRan;
    private boolean welcomeRemoved = false;


    public void setRetry(Runnable retry) {
        this.retry = retry;
    }

    public static final DiffUtil.ItemCallback<Survey> DIFF_CALLBACK = new DiffUtil.ItemCallback<Survey>() {
        @Override
        public boolean areItemsTheSame(@NonNull Survey oldSurvey, @NonNull Survey newSurvey) {
            return oldSurvey.getReference() == newSurvey.getReference();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Survey oldSurvey, @NonNull Survey newSurvey) {
            return oldSurvey.equals(newSurvey);
        }
    };

    public SurveyPagedAdapter() {
        super(DIFF_CALLBACK);
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.try_again:
                return NetworkStateViewHolder.create(parent);
            case R.layout.welcome:
                return WelcomeViewHolder.create(parent);
            case R.layout.card_survey:
            default:
                return SurveyPagedViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        switch (holder.getViewType()) {
            case ERROR:
                holder.bind(networkState);
                ((NetworkStateViewHolder)holder).setRunnable(retry);
                break;
            case HERO:
                FeedbackMasterObject obj = new FeedbackMasterObject();
                Runnable runnable = ()-> {
                    setWelcomeRemoved(true);
                    this.notifyItemRemoved(-1);
                };
                obj.setRunnable(runnable);
                holder.bind(obj);
                ((WelcomeViewHolder)holder).setTutorials(tutorials);
                break;
            case SURVEY:
            default:
                Survey item = getItem(position);
                holder.bind(item);
        }
    }


   @Override
   public int getItemViewType(int position) {
       if (hasExtraRow() && position == getItemCount() - 1) {
           return R.layout.try_again;
       } else if(position == 0 && !isWelcomeRemoved()) {
           return R.layout.welcome;
       } else {
           return R.layout.card_survey;
       }
   }

    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean hadExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean hasExtraRow = hasExtraRow();
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public boolean isHasAppRan() {
        return hasAppRan;
    }

    public void setHasAppRan(boolean hasAppRan) {
        this.hasAppRan = hasAppRan;
    }

    public boolean isWelcomeRemoved() {
        return welcomeRemoved;
    }

    public void setWelcomeRemoved(boolean welcomeRemoved) {
        this.welcomeRemoved = welcomeRemoved;
    }

    public Runnable getTutorials() {
        return tutorials;
    }

    public void setTutorials(Runnable tutorials) {
        this.tutorials = tutorials;
    }
}
