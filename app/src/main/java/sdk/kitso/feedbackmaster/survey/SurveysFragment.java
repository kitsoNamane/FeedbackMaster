package sdk.kitso.feedbackmaster.survey;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.Globals;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.Utils;
import sdk.kitso.feedbackmaster.model.DataItem;
import sdk.kitso.feedbackmaster.model.Profile;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SurveysFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveysFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static SurveyPagedAdapter pagedAdapter;
    private MaterialCardView reloadCard;
    private Chip reloadChip;
    private static SurveyViewModel surveyViewModel;
    private SurveyViewHolder surveyViewHolder;
    Profile profile;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SurveysFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SurveysFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SurveysFragment newInstance(String param1, String param2) {
        SurveysFragment fragment = new SurveysFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNetworkState(this.getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static void retry() {
        surveyViewModel.retry();
        Log.d("FMDIGILAB 16", "RETYRING");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_surveys, container, false);

        reloadCard = view.findViewById(R.id.reloadCard);
        reloadChip = view.findViewById(R.id.load_more);
        reloadChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry();
            }
        });

        String androidId = Settings.Secure.getString(this.getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        surveyViewModel = ViewModelProviders.of(this).get(SurveyViewModel.class);
        surveyViewModel.init(androidId, this.getContext());
        pagedAdapter = new SurveyPagedAdapter();

        surveyViewModel.getSurveyLiveData().observe(this, pagedList->{
            pagedAdapter.submitList(pagedList);
        });

        surveyViewModel.getNetworkState().observe(this, networkState->{
            pagedAdapter.setNetworkState(networkState);
            toggleReload(networkState.getStatus());
        });



        profile = MainActivity.surveyDB.surveyDao().getProfile(Globals.CURRENT_USER_ID);
        if(profile == null) {
            MainActivity.navController.navigate(SurveysFragmentDirections.actionSignup());
        }

        recyclerView = view.findViewById(R.id.survey_list);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(pagedAdapter);

        return view;
    }

    public void toggleReload(NetworkState.Status status) {
        if(status == NetworkState.Status.FAILED) {
            reloadCard.setVisibility(View.VISIBLE);
            Log.d("FMDIGILAB 14", "LiveUpdate");
        } else {
            Log.d("FMDIGILAB 15", "LiveUpdate");
            reloadCard.setVisibility(View.GONE);
        }
    }


    private void onNetworkState(Context context) {
        if(!Utils.isOnline(context)) {
            MainActivity.navController.navigate(SurveysFragmentDirections.actionNetworkError());
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
