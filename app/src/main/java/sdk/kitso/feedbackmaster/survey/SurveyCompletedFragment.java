package sdk.kitso.feedbackmaster.survey;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import java.util.Locale;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import sdk.kitso.feedbackmaster.Globals;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.model.QuestionnaireAnswer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SurveyCompletedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveyCompletedFragment extends Fragment implements MaterialButtonToggleGroup.OnButtonCheckedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    MaterialButton goHome;
    MaterialButton sendAnonymous;
    MaterialButtonToggleGroup group;
    MaterialButton notAnonymous;
    MaterialTextView completedSurveys;
    MaterialTextView totalWins;
    MaterialTextView thankYou;
    MaterialAlertDialogBuilder materialAlertDialogBuilder;
    FlexboxLayout uploadingAnswers;
    int retryAttempts = 3;
    FlexboxLayout sendAsAnonymous;
    Handler handler = new Handler();
    View trophy;
    private QuestionnaireViewModel questionnaireViewModel;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SurveyCompletedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SurveyCompletedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SurveyCompletedFragment newInstance(String param1, String param2) {
        SurveyCompletedFragment fragment = new SurveyCompletedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SurveyCompletedFragmentArgs surveyCompletedFragmentArgs = SurveyCompletedFragmentArgs.fromBundle(getArguments());
        QuestionnaireAnswer answer = surveyCompletedFragmentArgs.getQuestionnaireAnswers();
        questionnaireViewModel = ViewModelProviders.of(this).get(QuestionnaireViewModel.class);
        questionnaireViewModel.init();

        questionnaireViewModel.getNetworkState().observe(getViewLifecycleOwner(), networkState -> {
            switch (networkState.getStatus()) {
                case FAILED:
                    //disableBottomNavigation();
                    showProgressBar(View.INVISIBLE);
                    break;
                case RUNNING:
                    // render loading screen
                    //disableBottomNavigation();
                    showProgressBar(View.VISIBLE);
                    break;
                case SUCCESS:
                    showProgressBar(View.INVISIBLE);
                default:
                    // stop rendering loading animation
                    //enableBottomNavigation();
            }
        });

        questionnaireViewModel.getAnswerResponse().observe(getViewLifecycleOwner(), answerResponse -> {
            if(answerResponse == null) {
                // prompt reload button
            } else if(answerResponse.isSuccess() == false) {
                setVisibility(View.INVISIBLE);
                delayedDialogBox(answerResponse.getMessage().get(0).toString());
            } else {
                sendAsAnonymous.setVisibility(View.INVISIBLE);
                setVisibility(View.VISIBLE);
                showProgressBar(View.INVISIBLE);
                trophy.setVisibility(View.VISIBLE);
            }
        });

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
        View view  = inflater.inflate(R.layout.fragment_survey_completed, container, false);
        goHome = view.findViewById(R.id.go_home_text);
        uploadingAnswers = view.findViewById(R.id.uploading_answers);
        completedSurveys = view.findViewById(R.id.surveys_completed);
        trophy = view.findViewById(R.id.trophy);
        thankYou = view.findViewById(R.id.thank_you_text);
        totalWins = view.findViewById(R.id.total_wins);
        sendAsAnonymous = view.findViewById(R.id.send_as_anonymous);
        sendAnonymous = view.findViewById(R.id.as_anonymous);
        notAnonymous = view.findViewById(R.id.not_anonymous);
        group = view.findViewById(R.id.anonymous_group);

        notAnonymous.setOnClickListener(v -> onButtonChecked(group, v.getId(), ((MaterialButton)v).isChecked()));
        sendAnonymous.setOnClickListener(v -> onButtonChecked(group, v.getId(), ((MaterialButton)v).isChecked()));

        completedSurveys.setText(
                String.format(Locale.getDefault(), "%d",
                        MainActivity.feedbackMasterDB.surveyDao().getProfile(Globals.CURRENT_USER_ID).getNumberOfSurveysCompleted()
                )
        );
        totalWins.setText("0");
        goHome.setOnClickListener(v -> Navigation.findNavController(v).navigate(SurveyCompletedFragmentDirections.actionHome()));

        setVisibility(View.INVISIBLE);
        showProgressBar(View.INVISIBLE);
        trophy.setVisibility(View.INVISIBLE);

        return view;
    }

    public void showProgressBar(int VISIBILITY) {
        //if(VISIBILITY == View.INVISIBLE) {
        //    handler.postDelayed(() -> uploadingAnswers.setVisibility(VISIBILITY), 3000);
        //} else {
        uploadingAnswers.setVisibility(VISIBILITY);
        //}
    }

   public void delayedDialogBox(String message) {
       if(retryAttempts == 1) {
           new MaterialAlertDialogBuilder(this.getContext(), R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
                   .setTitle("Feedback Master")
                   .setCancelable(true)
                   .setMessage("Try Again Later")
                   .setPositiveButton("", ((dialog, which) -> {
                       goHome.setVisibility(View.VISIBLE);
                       dialog.cancel();
                       MainActivity.navController.navigate(SurveyCompletedFragmentDirections.actionHome());
                   }))
                   .setPositiveButton("Ok ", (dialog, which)->{
                       goHome.setVisibility(View.VISIBLE);
                       dialog.cancel();
                       MainActivity.navController.navigate(SurveyCompletedFragmentDirections.actionHome());
                   }).show();
       } else {
           new MaterialAlertDialogBuilder(this.getContext(), R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
                   .setTitle("Feedback Master")
                   .setCancelable(true)
                   .setMessage(message)
                   .setPositiveButton("Retry " + (retryAttempts), (dialog, which) -> {
                       showProgressBar(View.VISIBLE);
                       retry();
                       dialog.cancel();
                   })
                   .setNegativeButton("Cancel", (dialog, which) -> {
                       goHome.setVisibility(View.VISIBLE);
                       uploadingAnswers.setVisibility(View.INVISIBLE);
                       sendAsAnonymous.setVisibility(View.VISIBLE);
                       dialog.cancel();
                   }).show();
       }
   }

   public void retry() {
       retryAttempts -= 1;
       if(retryAttempts == 0) return;
       handler.postDelayed(() -> questionnaireViewModel.retry(), 1000);
   }

    public void setVisibility(int VISIBILITY) {
        this.goHome.setVisibility(VISIBILITY);
        this.thankYou.setVisibility(VISIBILITY);
    }

    @Override
    public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        if(checkedId == R.id.as_anonymous) {
            questionnaireViewModel.sendAnswerToServer(MainActivity.questionnaireAnswer);
        } else {
            // add profile info
            questionnaireViewModel.sendAnswerToServer(MainActivity.questionnaireAnswer);
        }
        sendAsAnonymous.setVisibility(View.INVISIBLE);
        showProgressBar(View.VISIBLE);
    }
}
