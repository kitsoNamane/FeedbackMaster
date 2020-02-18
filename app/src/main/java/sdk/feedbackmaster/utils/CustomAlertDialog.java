package sdk.feedbackmaster.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.textview.MaterialTextView;

import java.util.Locale;

import sdk.feedbackmaster.R;

public class CustomAlertDialog {
    public static void showAlert(Context context, int hintNummber, String hintText) {
        //LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View popupView = LayoutInflater.from(context).inflate(R.layout.custom_pop_up, null);
        //View popupView = layoutInflater.inflate(R.layout.custom_pop_up, null);

        ((MaterialTextView)popupView.findViewById(R.id.tip_number)).setText(
                String.format(Locale.getDefault(),"%d", hintNummber)
        );

        ((MaterialTextView)popupView.findViewById(R.id.tip_text)).setText(hintText);

        Dialog alertDialog = new Dialog(
                context, R.style.Theme_AppCompat_DayNight_NoActionBar);

        alertDialog.setCanceledOnTouchOutside(false);
        popupView.setOnTouchListener((v, event) -> {
            v.performClick();
            alertDialog.dismiss();
            return true;
        });

        alertDialog.setContentView(popupView);
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
