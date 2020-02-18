package sdk.feedbackmaster.utils;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.google.android.material.textview.MaterialTextView;

import sdk.feedbackmaster.R;

public class CustomPopUp {
    private static PopupWindow popupWindow;
    public static void showPopUp(View view, String hintText, int hintNumber) {
        LayoutInflater layoutInflater = (LayoutInflater) view.getContext()
                .getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.custom_pop_up, null);

        ((MaterialTextView)popupView.findViewById(R.id.tip_number)).setText(Integer.toString(hintNumber));
        ((MaterialTextView)popupView.findViewById(R.id.tip_text)).setText(hintText);

        popupWindow = new PopupWindow();
        popupWindow.setContentView(popupView);
        //popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        }, 1000);

        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

}
