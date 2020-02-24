package sdk.feedbackmaster.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

import com.google.android.material.textview.MaterialTextView;

import sdk.feedbackmaster.MainActivity;
import sdk.feedbackmaster.R;
import sdk.feedbackmaster.controllers.TutorialsController;

public class Utils {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static PopupWindow showPopUp(View view, String hintText, int hintNumber) {
        LayoutInflater layoutInflater = (LayoutInflater) view.getContext()
                .getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.custom_pop_up, null);
        ((MaterialTextView)popupView.findViewById(R.id.tip_number)).setText(Integer.toString(hintNumber));
        ((MaterialTextView)popupView.findViewById(R.id.tip_text)).setText(hintText);
        final PopupWindow popupWindow = new PopupWindow(popupView);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });

        return popupWindow;
    }

    public static void initTutorial(View view) {
        boolean runCurrentTutorial;
        switch (view.getId()) {
            case R.id.fragment_surveys:
                runCurrentTutorial = MainActivity.appData.isSurveysTutComplete();
                break;
            case R.id.branches_departments:
                runCurrentTutorial = MainActivity.appData.isBranchesTutComplete();
                break;
            case R.id.questionnaire:
                runCurrentTutorial = MainActivity.appData.isQuestionnaireTutComplete();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        if(!runCurrentTutorial) {
            switch (view.getId()) {
                case R.id.fragment_surveys:
                    MainActivity.appData.setSurveysTutComplete(true);
                    break;
                case R.id.branches_departments:
                    MainActivity.appData.setBranchesTutComplete(true);
                    break;
                case R.id.questionnaire:
                    MainActivity.appData.setQuestionnaireTutComplete(true);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }

            MainActivity.feedbackMasterDB.surveyDao().addDevice(MainActivity.appData);
            TutorialsController tutorialsController = TutorialsController.getInstance();
            tutorialsController.initTutorial(view);
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
