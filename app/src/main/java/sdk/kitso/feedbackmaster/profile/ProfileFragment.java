package sdk.kitso.feedbackmaster.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.fragment.app.Fragment;
import sdk.kitso.feedbackmaster.Globals;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.db.Profile;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextInputEditText viewPhone;
    TextInputEditText viewAge;
    RadioGroup viewGender;
    RadioButton male;
    RadioButton female;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Profile profile;

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
        profile = MainActivity.surveyDB.surveyDao().getProfile(Globals.CURRENT_USER_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        viewPhone = view.findViewById(R.id.phone_view);
        viewAge = view.findViewById(R.id.age_view);
        viewGender = view.findViewById(R.id.gender_view);
        male = viewGender.findViewById(R.id.male_view);
        female = viewGender.findViewById(R.id.female_view);

        viewPhone.setText(Integer.toString(profile.getPhone()));
        viewAge.setText(Integer.toString(profile.getAge()));
        FloatingActionButton editProfile = view.findViewById(R.id.edit_profile);


        Toast.makeText(this.getContext(), profile.getGender(), Toast.LENGTH_LONG).show();
        if(profile.getGender() == "male") {
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }

        //! Testing for now,
        if(profile.getProfile() == false) {
            viewPhone.setError(null,
                    view.getResources()
                        .getDrawable(R.drawable.ic_warning_orange_700_36dp));
        } else {
            viewPhone.setError(null,
                    view.getResources()
                            .getDrawable(R.drawable.ic_verified_user_black_24dp));
        }
        //Disable All Input until edit requested
        toggleInput(false);
        return view;
    }

    public void toggleInput(boolean enabledState) {
        viewPhone.setEnabled(enabledState);
        viewAge.setEnabled(enabledState);
        male.setEnabled(enabledState);
        female.setEnabled(enabledState);
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
