package sdk.kitso.feedbackmaster.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.fragment.app.Fragment;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AgeAndGenderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AgeAndGenderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgeAndGenderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextInputEditText ageInput;
    private RadioGroup gender;
    private RadioButton male;
    private RadioButton female;
    private RadioButton genderId;
    private String genderState;


    //private OnFragmentInteractionListener mListener;

    public AgeAndGenderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgeAndGenderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgeAndGenderFragment newInstance(String param1, String param2) {
        AgeAndGenderFragment fragment = new AgeAndGenderFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_age_and_gender, container, false);
        ageInput = view.findViewById(R.id.age_input);
        gender = view.findViewById(R.id.gender_input);
        male = gender.findViewById(R.id.male_radio);
        female = gender.findViewById(R.id.female_radio);
        MaterialButton gotoMainActivity = view.findViewById(R.id.goto_main);
        gotoMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signup(ageInput, gender)) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    public boolean signup(TextInputEditText textInputEditText, RadioGroup radioGroup) {
        String age = textInputEditText.getText().toString().trim();
        boolean isAgeValid = false;
        boolean isGenderValid = false;
        if(!age.isEmpty()) {
            try {
                ProfileSetup.profile.setAge(new Integer(age));
            } catch (Exception e) {
                textInputEditText.setError("Age Invalid");
                e.printStackTrace();
            }
            isAgeValid = true;
        } else {
            textInputEditText.setError("Age Required");
        }

        if(!(radioGroup.getCheckedRadioButtonId() == -1)) {
            male.setError(null);
            female.setError(null);
            genderId = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            genderState = genderId.getText().toString();
            try{
                Toast.makeText(this.getContext(), "Gender :"+genderState, Toast.LENGTH_LONG).show();
                ProfileSetup.profile.setGender(genderState);
            } catch (Exception e) {
                e.printStackTrace();
            }
            isGenderValid = true;
        } else {
            male.setError("");
            female.setError("");
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    male.setError(null);
                    female.setError(null);
                }
            });
        }

        if(isAgeValid == true && isGenderValid == true) {
            ProfileSetup.profile.setProfile(true);
            ProfileSetup.surveyDB.surveyDao().addProfile(ProfileSetup.profile);
        }
        // Binary End-Gate guarantees that we'll get the right results nomatter the combinations
        return isAgeValid && isGenderValid;
    }

    /**
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
     */
}
