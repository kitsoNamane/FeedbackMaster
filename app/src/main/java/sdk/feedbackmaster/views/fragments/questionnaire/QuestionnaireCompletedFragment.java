package sdk.feedbackmaster.views.fragments.questionnaire;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import sdk.feedbackmaster.Globals;
import sdk.feedbackmaster.MainActivity;
import sdk.feedbackmaster.R;
import sdk.feedbackmaster.model.AnswerResponse;
import sdk.feedbackmaster.model.Profile;
import sdk.feedbackmaster.viewmodels.AnswersViewModel;
import sdk.feedbackmaster.viewmodels.ProfileViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link QuestionnaireCompletedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionnaireCompletedFragment extends Fragment implements MaterialButtonToggleGroup.OnButtonCheckedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    MaterialButton goHome;
    MaterialButton sendAnonymous;
    MaterialButtonToggleGroup group;
    MaterialButton notAnonymous;
    MaterialTextView completedSurveys;
    MaterialTextView totalWins;
    MaterialTextView thankYou;
    FlexboxLayout uploadingAnswers;
    int retryAttempts = 3;
    FlexboxLayout sendAsAnonymous;
    Handler handler = new Handler();
    View trophy;
    private ProfileViewModel profileViewModel;
    private AnswersViewModel answersViewModel;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuestionnaireCompletedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionnaireCompletedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionnaireCompletedFragment newInstance(String param1, String param2) {
        QuestionnaireCompletedFragment fragment = new QuestionnaireCompletedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        answersViewModel = new ViewModelProvider(this).get(AnswersViewModel.class);
        answersViewModel.getNetworkState().observe(getViewLifecycleOwner(), networkState -> {
            switch (networkState.getStatus()) {
                case SUCCESS:
                case FAILED:
                    showProgressBar(View.INVISIBLE);
                    break;
                case RUNNING:
                    showProgressBar(View.VISIBLE);
                    break;
                default:
                    // stop rendering loading animation
                    //enableBottomNavigation();
            }
        });

        answersViewModel.getAnswerResponse().observe(getViewLifecycleOwner(), answerResponse -> {
            if(answerResponse == null) {
                // prompt reload button
            } else if(answerResponse.isSuccess() == false) {
                //setVisibility(View.INVISIBLE);
                delayedDialogBox(answerResponse);
            } else {
                Profile profile = profileViewModel.getProfile();
                profile.setNumberOfSurveysCompleted(
                        profileViewModel.getProfile().getNumberOfSurveysCompleted() + 1
                );
                profileViewModel.addProfile(profile);
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
        
         // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                
                AlertDialog alertDialog = new MaterialAlertDialogBuilder(
                   this.getContext(), R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
                   .setMessage("You already completed a questionnaire")
                   .setPositiveButton("Continue", (dialog, which) -> { 
                       dialog.cancel();           
                   }).show();
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);    
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_questionnaire_completed, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Feedback Master");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Upload Answers");
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        profileViewModel.init(getContext());

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
                        profileViewModel.getProfile().getNumberOfSurveysCompleted()
                )
        );

        totalWins.setText(String.format(Locale.getDefault(), "%d",
                profileViewModel.getProfile().getTotalWins()

        ));
        goHome.setOnClickListener(v -> Navigation.findNavController(v).navigate(QuestionnaireCompletedFragmentDirections.actionHome()));

        sendAsAnonymous.setVisibility(View.VISIBLE);
        setVisibility(View.INVISIBLE);
        showProgressBar(View.INVISIBLE);
        trophy.setVisibility(View.INVISIBLE);

        return view;
    }

    public void showProgressBar(int VISIBILITY) {
        uploadingAnswers.setVisibility(VISIBILITY);
    }

    public void delayedDialogBox(AnswerResponse response) {
        if(Globals.RESPONSE_ERROR_CODES.contains(new Integer(response.getErrorCode()))) {
            AlertDialog alertDialog = new MaterialAlertDialogBuilder(
                   this.getContext(), R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
                   .setCancelable(false)
                   .setTitle("Try Again Later...")
                   .setMessage(response.getMessage().get(0).toString())
                   .setNegativeButton("", ((dialog, which) -> {
                       answersViewModel.clearNetworkState();
                       MainActivity.navController.navigate(QuestionnaireCompletedFragmentDirections.actionHome());
                       dialog.cancel();
                   }))
                   .setPositiveButton("Continue", (dialog, which) -> {
                       answersViewModel.clearNetworkState();
                       MainActivity.navController.navigate(QuestionnaireCompletedFragmentDirections.actionHome());
                       dialog.cancel();
                   }).create();
           alertDialog.setCancelable(false);
           alertDialog.setCanceledOnTouchOutside(false);
           alertDialog.show();
        } else if(retryAttempts == 1) {
            AlertDialog alertDialog = new MaterialAlertDialogBuilder(
                   this.getContext(), R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
                   .setCancelable(false)
                   .setTitle("Try Again Later...")
                   .setNegativeButton("", ((dialog, which) -> {
                       goHome.setVisibility(View.VISIBLE);
                       answersViewModel.clearNetworkState();
                       dialog.cancel();
                       MainActivity.navController.navigate(QuestionnaireCompletedFragmentDirections.actionHome());
                   }))
                   .setPositiveButton("Continue", (dialog, which) -> {
                       goHome.setVisibility(View.VISIBLE);
                       answersViewModel.clearNetworkState();
                       dialog.cancel();
                       MainActivity.navController.navigate(QuestionnaireCompletedFragmentDirections.actionHome());
                   }).create();
           alertDialog.setCancelable(false);
           alertDialog.setCanceledOnTouchOutside(false);
           alertDialog.show();
        } else {
            AlertDialog alertDialog = new MaterialAlertDialogBuilder(
                   this.getContext(), R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
                   .setTitle("Re-Submit Answers ?")
                   .setCancelable(false)
                   .setMessage(response.getMessage().toString())
                   .setPositiveButton("Retry " + (retryAttempts), (dialog, which) -> {
                       showProgressBar(View.VISIBLE);
                       retry();
                       dialog.cancel();
                   })
                   .setNegativeButton("No", (dialog, which) -> {
                       goHome.setVisibility(View.VISIBLE);
                       answersViewModel.clearNetworkState();
                       uploadingAnswers.setVisibility(View.INVISIBLE);
                       sendAsAnonymous.setVisibility(View.VISIBLE);
                       dialog.cancel();
                   }).create();
           alertDialog.setCancelable(false);
           alertDialog.setCanceledOnTouchOutside(false);
           alertDialog.show();
        }
   }

   public void retry() {
        retryAttempts -= 1;
        if(retryAttempts == 0) return;
        handler.postDelayed(() -> answersViewModel.retry(), 1000);
   }

    public void setVisibility(int VISIBILITY) {
        this.goHome.setVisibility(VISIBILITY);
        this.thankYou.setVisibility(VISIBILITY);
    }

    @Override
    public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        if(checkedId == R.id.as_anonymous) {
            answersViewModel.sendAnswers(MainActivity.questionnaireAnswer);
        } else {
            // add profile info
            MainActivity.questionnaireAnswer.setMobileNumber(
                    String.format(
                            Locale.getDefault(), "%d", profileViewModel.getProfile().getPhone()
                    )
            );

            answersViewModel.sendAnswers(MainActivity.questionnaireAnswer);
        }
        sendAsAnonymous.setVisibility(View.INVISIBLE);
        showProgressBar(View.VISIBLE);
    }
}
