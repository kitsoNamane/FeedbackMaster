package sdk.kitso.feedbackmaster.survey;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;


import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NetworkConnectivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NetworkConnectivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkConnectivityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressBar progressBar;
    Handler handler = new Handler();
    Group group;
    MaterialButton retry;

    //private OnFragmentInteractionListener mListener;

    public NetworkConnectivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NetworkConnectivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NetworkConnectivityFragment newInstance(String param1, String param2) {
        NetworkConnectivityFragment fragment = new NetworkConnectivityFragment();
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
        View view = inflater.inflate(R.layout.fragment_network_connectivity, container, false);
        retry = view.findViewById(R.id.try_again);
        progressBar = view.findViewById(R.id.progressBar);
        group = view.findViewById(R.id.network_error_group);
        group.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        // check network below
        onNetworkState(this.getContext());

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                onNetworkState(v.getContext());
            }
        });
        return view;
    }

    private void onNetworkState(Context context) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Utils.isOnline(context)) {
                    // to to surveys
                    MainActivity.navController.navigate(NetworkConnectivityFragmentDirections.actionNetworkConnectivityFragmentPop());
                } else {
                    progressBar.setVisibility(View.GONE);
                    group.setVisibility(View.VISIBLE);
                }
            }
        }, 1000);

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
