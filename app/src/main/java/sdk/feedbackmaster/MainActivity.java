package sdk.feedbackmaster;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import sdk.feedbackmaster.model.AppData;
import sdk.feedbackmaster.model.Profile;
import sdk.feedbackmaster.model.QuestionnaireAnswer;
import sdk.feedbackmaster.repository.remote_datasource.FeedbackMasterSurveyApiService;
import sdk.feedbackmaster.repository.remote_datasource.factories.FeedbackMasterSurveyServiceFactory;

public class MainActivity extends AppCompatActivity {
    public static NavController navController;
    public static Profile profile;
    public static AppData appData;
    public static FeedbackMasterSurveyApiService feedbackMasterSurveyApiService;
    public static QuestionnaireAnswer questionnaireAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID
        );

        if(questionnaireAnswer == null) {
            questionnaireAnswer = new QuestionnaireAnswer();
        }

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        questionnaireAnswer.setDevice(androidId);

        feedbackMasterSurveyApiService = FeedbackMasterSurveyServiceFactory.getService(androidId, this);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemReselectedListener(item -> {
            // Do nothing
        });

        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            switch (navDestination.getId()) {
                // use the ID of the navigation graph not the ID of the Question fragment
                case R.id.signUpFragment:
                    navView.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                    toolbar.setNavigationIcon(null);
                    break;
                case R.id.questionFragment:
                case R.id.questionnaireCompletedFragment:
                case R.id.searchFragment:
                case R.id.branchesDepartmentsFragment:
                default:
                    navView.setVisibility(View.GONE);
                    toolbar.setVisibility(View.VISIBLE);
            }
        });

        NavigationUI.setupActionBarWithNavController(this, navController, new AppBarConfiguration.Builder(navController.getGraph()).build());
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, (DrawerLayout) null);
    }
}
