package sdk.kitso.feedbackmaster.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.fragment.app.Fragment;
import sdk.kitso.feedbackmaster.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SetPhoneFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SetPhoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetPhoneFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public SetPhoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetPhoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetPhoneFragment newInstance(String param1, String param2) {
        SetPhoneFragment fragment = new SetPhoneFragment();
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
        View view = inflater.inflate(R.layout.fragment_set_phone, container, false);
        final TextInputEditText phoneInput = view.findViewById(R.id.phone_input);
        MaterialButton gotoAgeAndGender = view.findViewById(R.id.goto_age_and_gender);
        gotoAgeAndGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate input
                if(signup(phoneInput)) {
                    ProfileSetup.navController.navigate(R.id.ageAndGenderFragment);
                }
            }
        });
        return view;
    }

    public boolean signup(TextInputEditText textInputEditText) {
        String phone = textInputEditText.getText().toString().trim();
        if(!phone.isEmpty()==true && phone.length()==8) {
            try {
                ProfileSetup.profile.setPhone(new Integer(phone));
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

    public void hideSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    view.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            //imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
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
