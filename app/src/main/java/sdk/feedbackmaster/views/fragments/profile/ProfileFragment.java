package sdk.feedbackmaster.views.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import sdk.feedbackmaster.Globals;
import sdk.feedbackmaster.MainActivity;
import sdk.feedbackmaster.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MaterialTextView viewPhone;
    MaterialTextView viewAge;
    MaterialTextView viewGender;
    MaterialTextView completedSurveys;
    MaterialTextView totalWins;
    MaterialButton editUser;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Feedback Master");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Profile");
        viewPhone = view.findViewById(R.id.phone_view);
        editUser = view.findViewById(R.id.edit_profile);
        viewAge = view.findViewById(R.id.age_input);
        viewGender = view.findViewById(R.id.gender_view);
        viewPhone.setText(Integer.toString(MainActivity.profile.getPhone()));
        viewAge.setText(Integer.toString(MainActivity.profile.getAge()));
        viewGender.setText(MainActivity.profile.getGender());

        completedSurveys = view.findViewById(R.id.surveys_completed);
        totalWins = view.findViewById(R.id.total_wins);

        completedSurveys.setText(
                String.format(Locale.getDefault(),"%d",
                        MainActivity.feedbackMasterDB.surveyDao().getProfile(Globals.CURRENT_USER_ID).getNumberOfSurveysCompleted()
                )
        );
        totalWins.setText("0");

        editUser.setOnClickListener(v -> Toast.makeText(getContext(), "Go to edit page", Toast.LENGTH_LONG).show());
        return view;
    }
}
