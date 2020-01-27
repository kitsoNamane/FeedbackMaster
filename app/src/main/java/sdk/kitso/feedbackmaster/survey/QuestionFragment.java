package sdk.kitso.feedbackmaster.survey;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.textview.MaterialTextView;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import sdk.kitso.feedbackmaster.Globals;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.model.AnswersItem;
import sdk.kitso.feedbackmaster.model.Result;
import sdk.kitso.feedbackmaster.question.QuestionController;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    QuestionController questionController;
    QuestionFragmentArgs questionFragmentArgs;
    BottomNavigationView questionNav;
    FrameLayout questionView;
    private Result getQuestions;
    MaterialTextView questionTitle;
    ListView group;
    View questionContent;
    FlexboxLayout multipleChoice;
    LayoutInflater layoutInflater;
    Chip chipItem;
    Chip option;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
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
        // Get value passed to this fragment using safeargs
        questionFragmentArgs = QuestionFragmentArgs.fromBundle(getArguments());
        questionController = QuestionController.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_question, container, false);
        questionNav = view.findViewById(R.id.question_nav_view);
        questionView = view.findViewById(R.id.question_showcase);
        questionNav.setSelectedItemId(R.id.dummy);
        questionTitle = view.findViewById(R.id.question_title);
        layoutInflater = this.getLayoutInflater();

        getQuestions();

        MainActivity.surveyViewModel.getNetworkState().observe(this, networkState -> {
            switch (networkState.getStatus()) {
                case FAILED:
                    disableBottomNavigation();
                    break;
                case RUNNING:
                    // render loading screen
                    disableBottomNavigation();
                    break;
                case SUCCESS:
                default:
                    // stop rendering loading animation
                    enableBottomNavigation();
            }
        });

        MainActivity.surveyViewModel.getQuestionnaire().observe(this, questionnaire->{
            if(questionnaire != null) {
                questionController.setQuestions(questionnaire.getQuestions());
                questionTitle.setText(questionController.nextQuestion().getCaption());
                renderQuestion();
            }
        });

        return view;
    }

    public void getQuestions() {
        MainActivity.surveyViewModel.getQuestionsFromServer(
                questionFragmentArgs.getSurveyReference(),
                questionFragmentArgs.getBusinessReference()
        );
    }


    public View setQuestionContent(@LayoutRes int i) {
       return getLayoutInflater().inflate(i, questionView, false);
    }

    public void disableBottomNavigation() {
        questionNav.setOnNavigationItemReselectedListener(item -> {
            // Do nothing for now
        });

        questionNav.setOnNavigationItemSelectedListener(item-> {
            // Do nothing
            return false;
        });
    }

    public void enableBottomNavigation() {
        questionNav.setOnNavigationItemReselectedListener(item -> {
            // Do nothing for now
        });

        //renderQuestion();
        questionNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.question_next:
                    questionController.nextQuestion();
                    if(questionController.currentQuestion == null) {
                        MainActivity.profile.setNumberOfSurveysCompleted(
                                MainActivity.profile.getNumberOfSurveysCompleted() + 1

                        );
                        MainActivity.surveyDB.surveyDao().addProfile(MainActivity.profile);

                        MainActivity.navController.navigate(QuestionFragmentDirections.actionCompleted());
                        break;
                    }
                    questionTitle.setText(questionController.currentQuestion.getCaption());
                    renderQuestion();
                    break;
                case R.id.previous_question:
                    questionController.previousQuestion();
                    questionTitle.setText(questionController.currentQuestion.getCaption());
                    renderQuestion();
                    break;
                case R.id.dummy:
                    break;
                default:
                    // Do something
            }
            return false;
        });
    }

    public void renderQuestion() {
        View view = questionNav.findViewById(R.id.dummy);
        Log.d("FMDIGILAB 24", questionController.currentQuestion.getSurveyQuestiontype().getRef());
        switch(questionController.currentQuestion.getSurveyQuestiontype().getRef()){
            case Globals.SINGLE_SELECT:
            case Globals.TRUE_OR_FALSE:
                // True or False is categorized as single-select
                questionContent = setQuestionContent(R.layout.multiple_choice);
                multipleChoice = (FlexboxLayout) questionContent;
                questionView.removeAllViews();
                addOptions(questionContent, Globals.SINGLE_SELECT);
                break;
            case Globals.MULTI_SELECT:
                questionContent = setQuestionContent(R.layout.multiple_choice);
                questionView.removeAllViews();
                addOptions(questionContent, Globals.MULTI_SELECT);
                break;
            case Globals.OPEN_ENDED:
                questionContent = setQuestionContent(R.layout.short_answer);
                questionView.removeAllViews();
                questionView.addView(questionContent);
                break;
            case Globals.RATING:
            default:
                questionContent = setQuestionContent(R.layout.ten_rating_stars);
                questionView.removeAllViews();
                questionView.addView(questionContent);
        }
    }

    public void addOptions(View view, String QestionType) {
        multipleChoice = (FlexboxLayout) view;

        switch(QestionType) {
           case Globals.MULTI_SELECT:
               multipleChoice.removeAllViews();
               for(AnswersItem item: questionController.currentQuestion.getAnswers()) {
                   option = (Chip) layoutInflater.inflate(R.layout.category_chip, multipleChoice, false);
                   option.setText(item.getCaption());
                   multipleChoice.addView(option);
               }
               break;
           case Globals.SINGLE_SELECT:
           default:
               multipleChoice.removeAllViews();
               for(AnswersItem item: questionController.currentQuestion.getAnswers()) {
                   option = (Chip) layoutInflater.inflate(R.layout.category_chip, multipleChoice, false);
                   option.setText(item.getCaption());
                   option.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                          setSingleSelect(multipleChoice, (Chip)v);
                          displayContinueButton(isSelected(multipleChoice));
                       }
                   });
                   multipleChoice.addView(option);
               }
        }
        questionView.addView(multipleChoice);
    }

    public void displayContinueButton(boolean isQuestionAnswered) {
        if(isQuestionAnswered) {
            //display next question
            Toast.makeText(this.getContext(), "Answer Selected", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getContext(), "Answer Not Selected", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isSelected(FlexboxLayout options) {
        if(!(options instanceof ViewGroup)) {
            return false;
        }

        for(int i = 0; i < options.getFlexItemCount(); i++) {
            option = (Chip) options.getFlexItemAt(i);
            if(option.isChecked()) {
                return true;
            }
        }
        return false;
    }

    public void setSingleSelect(FlexboxLayout options, Chip chip) {
        if(!(options instanceof ViewGroup)) {
            return;
        }

        for(int i = 0; i < options.getFlexItemCount(); i++) {
           option = (Chip) options.getFlexItemAt(i);
           if(option.getText() != chip.getText()) {
               option.setChecked(false);
           }
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
