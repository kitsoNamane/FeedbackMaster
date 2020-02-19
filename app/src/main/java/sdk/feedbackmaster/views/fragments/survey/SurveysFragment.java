package sdk.feedbackmaster.views.fragments.survey;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sdk.feedbackmaster.MainActivity;
import sdk.feedbackmaster.R;
import sdk.feedbackmaster.controllers.TutorialsController;
import sdk.feedbackmaster.utils.Utils;
import sdk.feedbackmaster.viewmodels.SurveyViewModel;
import sdk.feedbackmaster.views.adapters.SurveyPagedAdapter;

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
    public static RecyclerView recyclerView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView.LayoutManager layoutManager;
    private SurveyViewModel surveyViewModel;
    private SurveyPagedAdapter pagedAdapter;
    private static MaterialCardView reloadCard;
    private Chip reloadChip;

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        //Toolbar toolbar = (Toolbar) this.getActivity().findViewById(R.id.toolbar);
        //toolbar.inflateMenu(R.menu.topbar_with_search);
        super.onCreateOptionsMenu(menu, menuInflater);
        getActivity().getMenuInflater().inflate(R.menu.topbar_with_search, menu);
        MenuItem search = menu.getItem(menu.size() - 1);
        search.setTitle("search");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                MainActivity.navController.navigate(SurveysFragmentDirections.actionSearch());
            default:
                // Do nothing for now
        }
        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        surveyViewModel = new ViewModelProvider(getActivity()).get(SurveyViewModel.class);

        pagedAdapter = new SurveyPagedAdapter();

        surveyViewModel.getNetworkState().observe(getViewLifecycleOwner(), networkState->{
            pagedAdapter.setNetworkState(networkState);
            pagedAdapter.setRetry(surveyViewModel.getRetry());
        });

        surveyViewModel.getSurveyLiveData().observe(getViewLifecycleOwner(), pagedList->{
            if(pagedList != null) {
                pagedAdapter.submitList(pagedList);
                pagedAdapter.notifyDataSetChanged();
            }
        });


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(pagedAdapter);

        /**
        CustomAlertDialog.showAlert(
                this.getContext(), 1, "custom message, try to position it"
        );
         */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_surveys, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Feedback Master");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Surveys List");

        if(MainActivity.profile.getPhone() == 0) {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(
                    SurveysFragmentDirections.actionSignup()
            );
        }

        recyclerView = view.findViewById(R.id.survey_list);
        layoutManager = new LinearLayoutManager(view.getContext());
        TutorialsController tutorialsController = TutorialsController.getInstance();
        tutorialsController.initTutorial(view);
        return view;
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void onNetworkState(Context context) {
        if(!Utils.isOnline(context)) {
            MainActivity.navController.navigate(SurveysFragmentDirections.actionNetworkError());
        }
    }
}
