package sdk.feedbackmaster.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import sdk.feedbackmaster.R;
import sdk.feedbackmaster.controllers.TutorialsController;
import sdk.feedbackmaster.model.AppData;
import sdk.feedbackmaster.viewmodels.ProfileViewModel;

public class Utils {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static void initTutorial(View view, ProfileViewModel profileViewModel) {
        boolean runCurrentTutorial;
        AppData appData = profileViewModel.getAppData();
        if(appData == null) {
            appData = new AppData();
        }

        switch (view.getId()) {
            case R.id.fragment_surveys:
                runCurrentTutorial = appData.isSurveysTutComplete();
                break;
            case R.id.branches_departments:
                runCurrentTutorial = appData.isBranchesTutComplete();
                break;
            case R.id.questionnaire:
                runCurrentTutorial = appData.isQuestionnaireTutComplete();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        if(!runCurrentTutorial) {
            switch (view.getId()) {
                case R.id.fragment_surveys:
                    appData.setSurveysTutComplete(true);
                    break;
                case R.id.branches_departments:
                    appData.setBranchesTutComplete(true);
                    break;
                case R.id.questionnaire:
                    appData.setQuestionnaireTutComplete(true);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }

            profileViewModel.setAppData(appData);
            TutorialsController tutorialsController = TutorialsController.getInstance();
            tutorialsController.initTutorial(view);
        }
    }

    public static void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(view.requestFocus()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
