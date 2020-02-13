package sdk.kitso.feedbackmaster.ui.network_fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
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
    LottieAnimationView lottieProgressBar;
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

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Feedback Master");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Internet Connection");
        retry = view.findViewById(R.id.try_again);
        lottieProgressBar = view.findViewById(R.id.progressBar);
        group = view.findViewById(R.id.network_error_group);
        group.setVisibility(View.GONE);
        lottieProgressBar.setVisibility(View.VISIBLE);

        // check network below
        onNetworkState(this.getContext());

        retry.setOnClickListener(v -> {
            group.setVisibility(View.GONE);
            lottieProgressBar.setVisibility(View.VISIBLE);
            onNetworkState(v.getContext());
        });
        return view;
    }

    private void onNetworkState(Context context) {
        handler.postDelayed(() -> {
            if(Utils.isOnline(context)) {
                // to to surveys
                MainActivity.navController.navigate(NetworkConnectivityFragmentDirections.actionNetworkConnectivityFragmentPop());
            } else {
                lottieProgressBar.setVisibility(View.GONE);
                group.setVisibility(View.VISIBLE);
            }
        }, 1500);

    }
}
