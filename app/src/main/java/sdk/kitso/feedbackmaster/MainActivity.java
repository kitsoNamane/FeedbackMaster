package sdk.kitso.feedbackmaster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import sdk.kitso.feedbackmaster.db.SurveyDB;
import sdk.kitso.feedbackmaster.survey.SurveysFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    View setup_profile;
    View view_profile;
    LayoutInflater layoutInflater;
    ListView listView;
    public static SurveyDB surveyDB;
    NavController navController;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //linearLayout = findViewById(R.id.scroll_layout);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        surveyDB = Room.databaseBuilder(getApplicationContext(), SurveyDB.class, "userdb")
                .allowMainThreadQueries().build();
        setSupportActionBar(toolbar);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //appBarConfiguration = new AppBarConfiguration.Builder(
        //        R.id.navigation_survey, R.id.navigation_profile)
        //        .build();
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

    public View user_profile(ViewGroup container, LayoutInflater layoutInflater, int layoutRes) {
        String[] COUNTRIES = new String[] {"Item 1", "Item 2", "Item 3", "Item 4"};
        View view = layoutInflater.inflate(layoutRes, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getApplicationContext(),
                        R.layout.dropdown_menu_popup_item,
                        COUNTRIES);
        final AutoCompleteTextView profileSetUpDropdown =
                view.findViewById(R.id.filled_exposed_dropdown);
        profileSetUpDropdown.setAdapter(adapter);
        return view;
    }

    public void disable_profile(View view) {
        RadioButton male = view.findViewById(R.id.male);
        RadioButton female = view.findViewById(R.id.female);
        TextInputEditText materialTextView = view.findViewById(R.id.cellphone);
        AutoCompleteTextView textInputLayout = view.findViewById(R.id.filled_exposed_dropdown);
        male.setChecked(true);
        male.setEnabled(false);
        female.setEnabled(false);
        materialTextView.setText("75757698");
        materialTextView.setEnabled(false);
        textInputLayout.setText("25-30");
        textInputLayout.setEnabled(false);
    }

}
