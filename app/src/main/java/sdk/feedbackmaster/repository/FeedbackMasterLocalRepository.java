package sdk.feedbackmaster.repository;

import android.content.Context;

import sdk.feedbackmaster.Globals;
import sdk.feedbackmaster.model.AppData;
import sdk.feedbackmaster.model.Profile;
import sdk.feedbackmaster.repository.local_datasource.FeedbackMasterDao;
import sdk.feedbackmaster.repository.local_datasource.FeedbackMasterDatabase;

public class FeedbackMasterLocalRepository {
    private FeedbackMasterDao feedbackMasterDao;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public FeedbackMasterLocalRepository(Context context) {
        FeedbackMasterDatabase db = FeedbackMasterDatabase.getDatabase(context);
        feedbackMasterDao = db.feedbackMasterDao();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public Profile getProfile() {
        return feedbackMasterDao.getProfile(Globals.CURRENT_USER_ID);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void addProfile(Profile profile) {
        FeedbackMasterDatabase.databaseWriteExecutor.execute(() -> {
            feedbackMasterDao.addProfile(profile);
        });
    }

    public AppData getAppData() {
        return feedbackMasterDao.getAppData(Globals.CURRENT_USER_ID);
    }

    public void addAppData(AppData appData) {
        FeedbackMasterDatabase.databaseWriteExecutor.execute(() -> {
            feedbackMasterDao.addDevice(appData);
        });
    }
}
