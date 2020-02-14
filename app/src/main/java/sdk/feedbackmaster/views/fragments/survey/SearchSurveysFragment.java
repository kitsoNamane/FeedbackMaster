package sdk.feedbackmaster.views.fragments.survey;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sdk.feedbackmaster.MainActivity;
import sdk.feedbackmaster.R;
import sdk.feedbackmaster.Utils;
import sdk.feedbackmaster.model.Survey;
import sdk.feedbackmaster.viewmodels.SearchViewModel;
import sdk.feedbackmaster.views.adapters.SearchAdapter;
import sdk.feedbackmaster.views.adapters.SurveyPagedAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SearchSurveysFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SearchSurveysFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    SearchAdapter searchAdapter;
    SurveyPagedAdapter pagedAdapter;
    RecyclerView.LayoutManager layoutManager;
    SearchViewModel searchViewModel;
    TextInputEditText searchKeyword;
    ProgressBar searchProgress;
    TextInputLayout textInputLayout;
    CountDownTimer countDownTimer;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchSurveysFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchSurveysFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchSurveysFragment newInstance(String param1, String param2) {
        SearchSurveysFragment fragment = new SearchSurveysFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Feedback Master");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Search Surveys");

        textInputLayout = view.findViewById(R.id.textInputLayout);
        searchKeyword = view.findViewById(R.id.search_keyword);
        searchProgress = view.findViewById(R.id.search_progress_bar);
        recyclerView = view.findViewById(R.id.search_result_list);
        layoutManager = new LinearLayoutManager(view.getContext());
        searchAdapter = new SearchAdapter();
        pagedAdapter = new SurveyPagedAdapter();
        searchAdapter.setSearchResult(new ArrayList<Survey>());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchAdapter);
        searchProgress.setVisibility(View.GONE);

        textInputLayout.setStartIconOnClickListener(v -> {
            SurveysFragment.hideKeyboard(getActivity());
            MainActivity.navController.navigateUp();
        });

        searchKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count >= 3) {
                    if(!Utils.isOnline(getContext())) {
                        searchViewModel.clearNetworkState();
                        return;
                    }
                    countDownTimer = new CountDownTimer(500, 1) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if(s.length() < 3) return;
                        }

                        @Override
                        public void onFinish() {
                            if(s.length() < 3) return;
                            searchViewModel.search(s.toString());
                            searchProgress.setVisibility(View.VISIBLE);
                        }
                    }.start();
                } else if(count < 3){
                    searchAdapter.clearSearchResult();
                    searchProgress.setVisibility(View.GONE);
                } else {
                    searchAdapter.clearSearchResult();
                    searchProgress.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.getNetworkState().observe(getViewLifecycleOwner(), networkState -> {
            searchAdapter.setNetworkState(networkState);
            switch (networkState.getStatus()) {
                case FAILED:
                    Log.d("FMDIGILAB", networkState.getMsg());
                    searchAdapter.setRetry(searchViewModel.getRetry());
                    searchProgress.setVisibility(View.GONE);
                    break;
                case RUNNING:
                    // run loading progressbar
                    break;
                case SUCCESS:
                default:
                    // stop rendering loading animation
                    //enableBottomNavigation();
            }
        });


        searchViewModel.getSearchResults().observe(getViewLifecycleOwner(), result -> {
            if(result != null && result.getData().getSurveyList() != null) {
                searchAdapter.setSearchResult(result.getData().getSurveyList());
                searchProgress.setVisibility(View.GONE);
            } else {
                searchAdapter.clearSearchResult();
            }
        });
    }

}
