package sdk.feedbackmaster.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import sdk.feedbackmaster.model.AppData;
import sdk.feedbackmaster.model.Profile;
import sdk.feedbackmaster.repository.FeedbackMasterLocalRepository;

public class ProfileViewModel extends ViewModel {
    private FeedbackMasterLocalRepository feedbackMasterLocalRepository;

    public void init(Context context) {
        feedbackMasterLocalRepository = new FeedbackMasterLocalRepository(context);
    }

    public AppData getAppData() {
        return feedbackMasterLocalRepository.getAppData();
    }

    public void setAppData(AppData appData) {
        Log.d("AppData", "Adding AppData");
        feedbackMasterLocalRepository.addAppData(appData);
    }

    public Profile getProfile() {
        return feedbackMasterLocalRepository.getProfile();
    }

    public void addProfile(Profile profile) {
        feedbackMasterLocalRepository.addProfile(profile);
    }
}
