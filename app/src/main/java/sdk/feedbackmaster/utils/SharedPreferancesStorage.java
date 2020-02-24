package sdk.feedbackmaster.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferancesStorage {
    private static SharedPreferancesStorage instance;
    private SharedPreferences preferances;
    private String PREFS_NAME = "FeedbackMasterStorage";
    private String PREFS_VERSION_CODE_KEY = "1.0";
    private int DOESNT_EXIST = -1;

    private SharedPreferancesStorage(Context context) {
        preferances  = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferancesStorage getInstance(Context context) {
        instance = instance != null ? instance : new SharedPreferancesStorage(context);
        return instance;
    }

    public boolean checkAppHasRan() {
        if(preferances.getBoolean("appHasRanBefore", false)) {
            return false;
        } else {
            preferances.edit().putBoolean("appHasRanBefore", true).commit();
            return true;
        }
    }
}
