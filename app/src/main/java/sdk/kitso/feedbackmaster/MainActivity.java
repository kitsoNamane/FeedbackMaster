package sdk.kitso.feedbackmaster;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    LinearLayout questionContainer;
    View setup_profile;
    View view_profile;
    LayoutInflater layoutInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionContainer = findViewById(R.id.populate_question);
        layoutInflater = getLayoutInflater();
        linearLayout = findViewById(R.id.scroll_layout);
        setup_profile = user_profile(linearLayout, layoutInflater, R.layout.setup_profile);
        view_profile = user_profile(linearLayout, layoutInflater, R.layout.setup_profile);
        linearLayout.addView(setup_profile);
        linearLayout.addView(view_profile);
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

    }
}
