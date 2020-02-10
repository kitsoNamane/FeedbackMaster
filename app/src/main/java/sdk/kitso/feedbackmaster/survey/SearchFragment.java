package sdk.kitso.feedbackmaster.survey;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.model.DataItem;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    SearchAdapter searchAdapter;
    RecyclerView.LayoutManager layoutManager;
    SearchViewModel searchViewModel;
    TextInputEditText searchKeyword;
    ProgressBar searchProgress;
    TextInputLayout textInputLayout;
    CountDownTimer countDownTimer;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        textInputLayout = view.findViewById(R.id.textInputLayout);
        searchKeyword = view.findViewById(R.id.search_keyword);
        searchProgress = view.findViewById(R.id.search_progress_bar);
        recyclerView = view.findViewById(R.id.search_result_list);
        layoutManager = new LinearLayoutManager(view.getContext());
        searchAdapter = new SearchAdapter();
        searchAdapter.setSearchResult(new ArrayList<DataItem>());
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
                textInputLayout.setHelperTextEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count >= 3) {
                    textInputLayout.setHelperTextEnabled(false);
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
                    textInputLayout.setHelperTextEnabled(true);
                    searchProgress.setVisibility(View.GONE);
                } else {
                    searchAdapter.clearSearchResult();
                    textInputLayout.setHelperTextEnabled(true);
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
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.getNetworkState().observe(getViewLifecycleOwner(), networkState -> {
            switch (networkState.getStatus()) {
                case FAILED:
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
            if(result != null && result.getData().getDataItemList() != null) {
                searchAdapter.setSearchResult(result.getData().getDataItemList());
                searchProgress.setVisibility(View.GONE);
            } else {
                searchAdapter.clearSearchResult();
            }
        });
    }

}
