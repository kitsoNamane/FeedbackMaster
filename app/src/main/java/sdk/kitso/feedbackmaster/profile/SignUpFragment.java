package sdk.kitso.feedbackmaster.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import sdk.kitso.feedbackmaster.Globals;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.db.Profile;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Profile profile;
    private TextInputEditText ageInput;
    private ChipGroup gender;
    private Chip male;
    private Chip female;
    private Chip genderId;
    private String genderState;

    //private OnFragmentInteractionListener mListener;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        profile = MainActivity.surveyDB.surveyDao().getProfile(Globals.CURRENT_USER_ID);
        if(profile == null) {
            profile = new Profile();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        final TextInputEditText phoneInput = view.findViewById(R.id.phone_input);
        MaterialButton continueBtn = view.findViewById(R.id.continue_btn);
        ageInput = view.findViewById(R.id.age_input);
        gender = view.findViewById(R.id.gender_input);
        male = gender.findViewById(R.id.male);
        female = gender.findViewById(R.id.female);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate input
                if(signupPhone(phoneInput)) {
                    if(signup(ageInput, gender)) {
                        hideSoftKeyboard(v);
                        MainActivity.navController.navigate(SignUpFragmentDirections.actionSignUpFragmentPop());
                    }
                } else {
                    signup(ageInput, gender);
                }
            }
        });
        return view;
    }

    public boolean signupPhone(TextInputEditText textInputEditText) {
        String phone = textInputEditText.getText().toString().trim();
        Toast.makeText(this.getContext(), "Phone :"+phone+" "+Integer.toString(phone.length()), Toast.LENGTH_LONG).show();
        if(!phone.isEmpty()==true && phone.length()==8) {
            try {
                profile.setPhone(new Integer(phone));
            } catch (Exception e) {
                textInputEditText.setError("Number Invalid");
                e.printStackTrace();
                return false;
            }
            return true;
        }
        textInputEditText.setError("Number Required");
        return false;
    }

    public boolean signup(TextInputEditText textInputEditText, ChipGroup radioGroup) {
        String age = textInputEditText.getText().toString().trim();
        boolean isAgeValid = false;
        boolean isGenderValid = false;
        if(!age.isEmpty()) {
            try {
                profile.setAge(new Integer(age));
            } catch (Exception e) {
                textInputEditText.setError("Age Invalid");
                e.printStackTrace();
            }
            isAgeValid = true;
        } else {
            textInputEditText.setError("Age Required");
        }

        if(!(radioGroup.getCheckedChipId() == -1)) {
            male.setError(null);
            female.setError(null);
            genderId = (Chip) radioGroup.findViewById(radioGroup.getCheckedChipId());
            //genderState = genderId.getChipText().toString();
            genderState = genderId.getText().toString();
            try{
                profile.setGender(genderState);
            } catch (Exception e) {
                e.printStackTrace();
            }
            isGenderValid = true;
        } else {
            male.setError("");
            female.setError("");
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                male.setError(null);
                female.setError(null);
            });
        }

        if(isAgeValid == true && isGenderValid == true) {
            profile.setProfile(true);
            //Globals.exec.execute(()->{
            MainActivity.surveyDB.surveyDao().addProfile(profile);
            //});
        }
        // Binary End-Gate guarantees that we'll get the right results nomatter the combinations
        return isAgeValid && isGenderValid;
    }

    public void hideSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    view.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            //imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
    /** TODO: Rename method, update argument and hook method into UI event
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
