package sdk.kitso.feedbackmaster;

import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import sdk.kitso.feedbackmaster.model.Profile;
import sdk.kitso.feedbackmaster.model.SurveyDB;
import sdk.kitso.feedbackmaster.survey.SurveyPagedAdapter;
import sdk.kitso.feedbackmaster.survey.SurveyViewModel;
import sdk.kitso.feedbackmaster.survey.SurveysFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import sdk.kitso.feedbackmaster.db.SurveyDB;

public class MainActivity extends AppCompatActivity {
    public static SurveyDB surveyDB;
    public static NavController navController;
    public static SurveyViewModel surveyViewModel;
    public static SurveyPagedAdapter pagedAdapter;
    public static Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID
        );

        surveyViewModel = ViewModelProviders.of(this).get(SurveyViewModel.class);
        surveyViewModel.init(androidId, this);

        pagedAdapter = new SurveyPagedAdapter();

        surveyViewModel.getSurveyLiveData().observe(this, pagedList->{
            pagedAdapter.submitList(pagedList);
        });

        surveyViewModel.getNetworkState().observe(this, networkState->{
            pagedAdapter.setNetworkState(networkState);
            SurveysFragment.toggleReload(networkState.getStatus());
        });

        surveyDB = Room.databaseBuilder(getApplicationContext(), SurveyDB.class, "userdb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();

        profile = surveyDB.surveyDao().getProfile(Globals.CURRENT_USER_ID);
        if(profile == null) {
            profile = new Profile();
        }

        final MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                // Do nothing
            }
        });

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                switch (navDestination.getId()) {
                    // use the ID of the navigation graph not the ID of the Question fragment
                    case R.id.signUpFragment:
                    case R.id.questionFragment:
                        navView.setVisibility(View.GONE);
                        toolbar.setVisibility(View.GONE);
                        break;
                    default:
                        navView.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "I'M NOT AT Questions", Toast.LENGTH_LONG).show();
                }
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
