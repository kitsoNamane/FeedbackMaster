package sdk.kitso.feedbackmaster.profile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;
import sdk.kitso.feedbackmaster.Globals;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.model.Profile;
import sdk.kitso.feedbackmaster.model.SurveyDB;

public class ProfileSetup extends AppCompatActivity {
    public static NavController navController;
    public static Profile profile;
    public static SurveyDB surveyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_setup);
        surveyDB = Room.databaseBuilder(getApplicationContext(), SurveyDB.class, "userdb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
        profile = surveyDB.surveyDao().getProfile(Globals.CURRENT_USER_ID);
        if(profile == null) {
            profile = new Profile();
        }
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }
}
