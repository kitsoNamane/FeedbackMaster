package sdk.kitso.feedbackmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import sdk.kitso.feedbackmaster.db.Profile;
import sdk.kitso.feedbackmaster.db.SurveyDB;
import sdk.kitso.feedbackmaster.profile.ProfileSetup;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    public static SurveyDB surveyDB;
    NavController navController;
    Profile profile;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surveyDB = Room.databaseBuilder(getApplicationContext(), SurveyDB.class, "userdb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
        profile = surveyDB.surveyDao().getProfile(Globals.CURRENT_USER_ID);
        if(profile == null) {
            intent = new Intent(this, ProfileSetup.class);
            startActivity(intent);
        }

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, (DrawerLayout) null);
    }

    public void setup_answers(ArrayList<String> answers, int CHOICE_MODE) {
        /** ! ChOICE_MODE OPTIONS:
         AbsListView.CHOICE_MODE_SINGLE
         AbsListView.CHOICE_MODE_MULTIPLE
        */
        listView = findViewById(R.id.answer_list);
        listView.setAdapter(new MyListAdapter(this, answers));
        listView.setChoiceMode(CHOICE_MODE);
        MyListAdapter.setListViewHeightBasedOnChildren(listView);
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = (String) listView.getItemAtPosition(position);
                System.out.println(text);
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
