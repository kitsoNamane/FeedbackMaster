package sdk.feedbackmaster;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import sdk.feedbackmaster.model.FeedbackMasterDB;
import sdk.feedbackmaster.model.Profile;
import sdk.feedbackmaster.model.QuestionnaireAnswer;
import sdk.feedbackmaster.repository.FeedbackMasterSurveyApiService;
import sdk.feedbackmaster.repository.factories.FeedbackMasterSurveyServiceFactory;

public class MainActivity extends AppCompatActivity {
    public static FeedbackMasterDB feedbackMasterDB;
    public static NavController navController;
    public static Profile profile;
    public static FeedbackMasterSurveyApiService feedbackMasterSurveyApiService;
    public static QuestionnaireAnswer questionnaireAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID
        );

        feedbackMasterDB = Room.databaseBuilder(getApplicationContext(), FeedbackMasterDB.class, "userdb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();

        profile = feedbackMasterDB.surveyDao().getProfile(Globals.CURRENT_USER_ID);

        if(profile == null) {
            profile = new Profile();
        }

        if(questionnaireAnswer == null) {
            questionnaireAnswer = new QuestionnaireAnswer();
        }

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        questionnaireAnswer.setDevice(androidId);

        feedbackMasterSurveyApiService = FeedbackMasterSurveyServiceFactory.getService(androidId, this);

        final MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setElevation(new Float(2));
        final BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemReselectedListener(item -> {
            // Do nothing
        });

        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            switch (navDestination.getId()) {
                // use the ID of the navigation graph not the ID of the Question fragment
                case R.id.signUpFragment:
                case R.id.searchFragment:
                    navView.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                    break;
                case R.id.questionnaireCompletedFragment:
                case R.id.questionFragment:
                default:
                    navView.setVisibility(View.GONE);
                    toolbar.setVisibility(View.VISIBLE);
            }
        });

        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, (DrawerLayout) null);
    }
}
