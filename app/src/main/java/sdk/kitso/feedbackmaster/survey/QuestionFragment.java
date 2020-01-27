package sdk.kitso.feedbackmaster.survey;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import sdk.kitso.feedbackmaster.Globals;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.model.Answer;
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
    MaterialButton nextQuestion;
    List<Answer> answers;
    Chip chipItem;
    Answer answer;
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

        questionView = view.findViewById(R.id.question_showcase);
        questionTitle = view.findViewById(R.id.question_title);
        nextQuestion = view.findViewById(R.id.next_question);
        nextQuestion.setVisibility(View.GONE);
        layoutInflater = this.getLayoutInflater();
        answers = new ArrayList<>();

        nextQuestion.setOnClickListener(v -> {
            questionController.nextQuestion();
            if(questionController.currentQuestion == null && answers != null && answers.size() > 0) {
                MainActivity.questionnaireAnswer.setAnswers(answers);
                MainActivity.questionnaireAnswer.setTimer("124");
                MainActivity.questionnaireAnswer.showMe();
                MainActivity.surveyViewModel.sendAnswerToServer(MainActivity.questionnaireAnswer);

                MainActivity.profile.setNumberOfSurveysCompleted(
                        MainActivity.profile.getNumberOfSurveysCompleted() + 1
                );

                MainActivity.surveyDB.surveyDao().addProfile(MainActivity.profile);
                MainActivity.navController.navigate(QuestionFragmentDirections.actionCompleted());
            } else if (questionController.currentQuestion != null) {
                renderQuestion();
            }
        });

        getQuestions();

        MainActivity.surveyViewModel.getNetworkState().observe(this, networkState -> {
            switch (networkState.getStatus()) {
                case FAILED:
                    //disableBottomNavigation();
                    break;
                case RUNNING:
                    // render loading screen
                    //disableBottomNavigation();
                    break;
                case SUCCESS:
                default:
                    // stop rendering loading animation
                    //enableBottomNavigation();
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

    public void renderQuestion() {
        //View view = questionNav.findViewById(R.id.dummy);
        Log.d("FMDIGILAB 24", questionController.currentQuestion.getSurveyQuestiontype().getRef());
        questionTitle.setText(questionController.currentQuestion.getCaption());
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
                if(questionController.currentQuestion.getAnswertype().getRef() == "one-to-five") {
                    questionContent = setQuestionContent(R.layout.five_rating_stars);
                } else {
                    questionContent = setQuestionContent(R.layout.ten_rating_stars);
                }
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
                   option.setOnClickListener(v -> displayContinueButton(isOptionSelected(multipleChoice)));
                   option.setOnCheckedChangeListener((buttonView, isChecked) -> getSingleSelected(multipleChoice));
                   multipleChoice.addView(option);
                   multipleChoice.addView(option);
               }
               break;
           case Globals.SINGLE_SELECT:
           default:
               multipleChoice.removeAllViews();
               for(AnswersItem item: questionController.currentQuestion.getAnswers()) {
                   option = (Chip) layoutInflater.inflate(R.layout.category_chip, multipleChoice, false);
                   option.setText(item.getCaption());
                   option.setOnClickListener(v -> {
                      setSingleSelect(multipleChoice, (Chip)v);
                      Log.d("FMDIGILAB", "Option ID : "+v.getId());
                      displayContinueButton(isOptionSelected(multipleChoice));
                   });
                   option.setOnCheckedChangeListener((buttonView, isChecked) -> getSingleSelected(multipleChoice));
                   multipleChoice.addView(option);
               }
        }
        questionView.addView(multipleChoice);
    }

    public void displayContinueButton(boolean isQuestionAnswered) {
        if(isQuestionAnswered) {
            nextQuestion.setVisibility(View.VISIBLE);
        } else {
            nextQuestion.setVisibility(View.GONE);
        }
    }

    public boolean isOptionSelected(FlexboxLayout options) {
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

    public void getSingleSelected(FlexboxLayout options) {
        List<AnswersItem> availableAnswers = questionController.currentQuestion.getAnswers();
        for(int i = 0; i < options.getFlexItemCount(); i++) {
            option = (Chip) options.getFlexItemAt(i);
            if(option.isChecked()) {
                answer = new Answer();
                answer.setQuestion(questionController.currentQuestion.getRef());
                answer.setAnswer(availableAnswers.get(i).getRef());
                answers.add(answer);
            } else if(!option.isChecked()) {
                if(answers.size() > 0) {
                    answers.remove(i);
                }
            }
        }
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
}
