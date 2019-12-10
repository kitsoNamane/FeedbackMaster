package sdk.kitso.feedbackmaster.profile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import sdk.kitso.feedbackmaster.R;

public class ProfileSetup extends AppCompatActivity {
    public static NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.ageAndGenderFragment);
    }
}
