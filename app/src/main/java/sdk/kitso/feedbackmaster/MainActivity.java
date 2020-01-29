package sdk.kitso.feedbackmaster;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import sdk.kitso.feedbackmaster.model.FeedbackMasterDB;
import sdk.kitso.feedbackmaster.model.Profile;
import sdk.kitso.feedbackmaster.model.QuestionnaireAnswer;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterSurveyApi;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterSurveyApiService;
import sdk.kitso.feedbackmaster.survey.SurveyPagedAdapter;
import sdk.kitso.feedbackmaster.survey.SurveyViewModel;
import sdk.kitso.feedbackmaster.survey.SurveysFragment;

public class MainActivity extends AppCompatActivity {
    public static FeedbackMasterDB feedbackMasterDB;
    public static NavController navController;
    public static SurveyViewModel surveyViewModel;
    public static SurveyPagedAdapter pagedAdapter;
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

        surveyViewModel = ViewModelProviders.of(this).get(SurveyViewModel.class);

        feedbackMasterSurveyApiService = FeedbackMasterSurveyApi.getService(androidId, this);
        surveyViewModel.init();

        pagedAdapter = new SurveyPagedAdapter();

        surveyViewModel.getSurveyLiveData().observe(this, pagedList->{
            pagedAdapter.submitList(pagedList);
        });

        surveyViewModel.getNetworkState().observe(this, networkState->{
            pagedAdapter.setNetworkState(networkState);
            switch (navController.getCurrentDestination().getId()) {
                case R.id.branchesDepartmentsFragment:
                    // do something here
                    break;
                case R.id.signUpFragment:
                    break;
                case R.id.questionFragment:
                    break;
                case R.id.navigation_survey:
                default:
                    SurveysFragment.toggleReload(networkState.getStatus());
            }
        });



        final MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemReselectedListener(item -> {
            // Do nothing
        });


        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            switch (navDestination.getId()) {
                // use the ID of the navigation graph not the ID of the Question fragment
                case R.id.signUpFragment:
                case R.id.questionFragment:
                case R.id.surveyCompletedFragment:
                    navView.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                    break;
                default:
                    navView.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "I'M NOT AT Questions", Toast.LENGTH_LONG).show();
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
