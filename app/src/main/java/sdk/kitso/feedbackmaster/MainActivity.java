package sdk.kitso.feedbackmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    View setup_profile;
    View view_profile;
    LayoutInflater layoutInflater;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.scroll_layout);

        String[] ANSWERS = new String[] {"Answer 1", "Answer 2", "Answer 3", "Answer 4"};
        ArrayList<String> answers = new ArrayList<String>();
        answers.addAll( Arrays.asList(ANSWERS));
        setup_answers(answers, AbsListView.CHOICE_MODE_SINGLE);

        layoutInflater = getLayoutInflater();
        setup_profile = user_profile(linearLayout, layoutInflater, R.layout.setup_profile);
        view_profile = user_profile(linearLayout, layoutInflater, R.layout.setup_profile);
        disable_profile(view_profile);
        linearLayout.addView(setup_profile);
        linearLayout.addView(view_profile);
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
