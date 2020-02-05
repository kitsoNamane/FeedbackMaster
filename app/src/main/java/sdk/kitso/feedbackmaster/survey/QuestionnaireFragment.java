package sdk.kitso.feedbackmaster.survey;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.RatingBar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import sdk.kitso.feedbackmaster.Globals;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.model.Answer;
import sdk.kitso.feedbackmaster.model.AnswerData;
import sdk.kitso.feedbackmaster.model.AnswersItem;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link QuestionnaireFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionnaireFragment extends Fragment implements MaterialButtonToggleGroup.OnButtonCheckedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    QuestionController questionController;
    QuestionnaireFragmentArgs questionFragmentArgs;
    FrameLayout questionView;
    MaterialTextView questionTitle;
    View questionContent;
    RatingBar ratingBar;
    MaterialButtonToggleGroup multipleChoice;
    LayoutInflater layoutInflater;
    MaterialButton nextQuestion;
    List<Answer> answers;
    Answer answer;
    AnswerData answerData;
    MaterialButton option;
    TextInputEditText shortAnswer;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    MaterialTextView questionId;
    String start_date;
    String end_date;
    Chronometer stopWatch;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuestionnaireFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionnaireFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionnaireFragment newInstance(String param1, String param2) {
        QuestionnaireFragment fragment = new QuestionnaireFragment();
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
        questionFragmentArgs = QuestionnaireFragmentArgs.fromBundle(getArguments());
        questionController = QuestionController.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_question, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Feedback Master");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Questionnaire");

        questionView = view.findViewById(R.id.question_showcase);
        questionTitle = view.findViewById(R.id.question_title);
        nextQuestion = view.findViewById(R.id.next_question);
        questionId = view.findViewById(R.id.question_id);
        stopWatch = view.findViewById(R.id.stop_watch);

        nextQuestion.setVisibility(View.GONE);
        layoutInflater = this.getLayoutInflater();
        answers = new ArrayList<>();
        answerData = new AnswerData();

        stopWatch.setOnChronometerTickListener(chronometer -> stopWatch = chronometer);

        questionController.setQuestions(
                questionFragmentArgs.getSurveyQuestions().getQuestions()
        );

        questionTitle.setText(questionController.nextQuestion().getCaption());
        renderQuestion();
        start_date = dateFormat.format(c.getTime());
        stopWatch.start();

        nextQuestion.setOnClickListener(v -> {
            if(questionController.currentQuestion.getSurveyQuestiontype().getRef().equals(Globals.OPEN_ENDED)) {
                answer = new Answer();
                answer.setQuestion(questionController.currentQuestion.getRef());
                answerData = new AnswerData();
                //answerData.setRef("");
                answerData.setText(shortAnswer.getText().toString());
                //answerData.setListItem("");
                answer.setAnswerData(answerData);
                answers.add(answer);
            }

            questionController.nextQuestion();
            if(questionController.listIterator == (questionController.maxQuestions - 1)) {
                nextQuestion.setText("Finish");
            }

            if(questionController.currentQuestion == null && answers != null && answers.size() > 0) {
                stopWatch.stop();
                end_date = dateFormat.format(c.getTime());
                MainActivity.questionnaireAnswer.setAnswers(answers);
                MainActivity.questionnaireAnswer.setTimer(getTimer(stopWatch.getText().toString()));
                MainActivity.questionnaireAnswer.setMobileNumber(
                       Integer.toString(MainActivity.feedbackMasterDB.surveyDao().getProfile(Globals.CURRENT_USER_ID).getPhone())
                );
                MainActivity.questionnaireAnswer.setStartDate(start_date);
                MainActivity.questionnaireAnswer.setEndDate(end_date);
                MainActivity.questionnaireAnswer.removeNullAnswers();
                MainActivity.questionnaireAnswer.showMe();

                MainActivity.profile.setNumberOfSurveysCompleted(
                        MainActivity.profile.getNumberOfSurveysCompleted() + 1
                );

                MainActivity.feedbackMasterDB.surveyDao().addProfile(MainActivity.profile);
                MainActivity.navController.navigate(
                        QuestionnaireFragmentDirections.actionCompleted(MainActivity.questionnaireAnswer)
                );
            } else if (questionController.currentQuestion != null) {
                renderQuestion();
            }
        });
        return view;
    }

    public int getTimer(String timer) {
        String[] units = timer.split(":");
        int minutes = Integer.parseInt(units[0]);
        int seconds = Integer.parseInt(units[1]);
        return (minutes * 60) + seconds;
    }


    public View setQuestionContent(@LayoutRes int i) {
       return getLayoutInflater().inflate(i, questionView, false);
    }

    public void renderQuestion() {
        //View view = questionNav.findViewById(R.id.dummy);
        displayContinueButton(false);
        questionTitle.setText(questionController.currentQuestion.getCaption());
        questionId.setText(String.format(Locale.getDefault()," %d / %d", questionController.listIterator+1, questionController.maxQuestions));
        switch(questionController.currentQuestion.getSurveyQuestiontype().getRef()){
            case Globals.SINGLE_SELECT:
            case Globals.TRUE_OR_FALSE:
                // True or False is categorized as single-select
                questionContent = setQuestionContent(R.layout.group_multiple_choice);
                multipleChoice = (MaterialButtonToggleGroup) questionContent;
                questionView.removeAllViews();
                addOptions(questionContent, Globals.SINGLE_SELECT);
                break;
            case Globals.MULTI_SELECT:
                questionContent = setQuestionContent(R.layout.group_multiple_choice);
                questionView.removeAllViews();
                addOptions(questionContent, Globals.MULTI_SELECT);
                break;
            case Globals.OPEN_ENDED:
                questionContent = setQuestionContent(R.layout.short_answer);
                shortAnswer = questionContent.findViewById(R.id.short_answer);
                shortAnswer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        displayContinueButton(true);
                    }
                });

                questionView.removeAllViews();
                questionView.addView(questionContent);
                break;
            case Globals.RATING:
            default:
                String questionType = questionController.currentQuestion.getAnswertype().getRef();
                if(questionType.equals("one-to-five")) {
                    questionContent = setQuestionContent(R.layout.five_rating_stars);
                    ratingBar = questionContent.findViewById(R.id.five_rating_bar);
                } else if(questionType.equals("one-to-ten")){
                    questionContent = setQuestionContent(R.layout.ten_rating_stars);
                    ratingBar = questionContent.findViewById(R.id.ten_rating_bar);
                }
                ratingBar.setOnRatingBarChangeListener((_ratingBar, rating, fromUser) -> {
                    int index = (int)(rating - 1);
                    if(index < 0) {
                        nextQuestion.setVisibility(View.GONE);
                        return;
                    }
                    answer = new Answer();
                    answer.setQuestion(questionController.currentQuestion.getRef());
                    answerData = new AnswerData();
                    answerData.setRef(questionController.availableAnswers.get(index).getRef());
                    answerData.setText(questionController.availableAnswers.get(index).getCaption());
                    answerData.setListItem(questionController.availableAnswers.get(index).getRef());
                    answer.setAnswerData(answerData);
                    answers.add(answer);
                    displayContinueButton(true);
                });
                questionView.removeAllViews();
                questionView.addView(questionContent);
        }
    }

    public void addOptions(View view, String QestionType) {
        multipleChoice = (MaterialButtonToggleGroup) view;
        multipleChoice.setClickable(true);
        List<AnswersItem> options = questionController.currentQuestion.getAnswers();

        multipleChoice.removeAllViews();
        for(int i=0; i < options.size(); i++) {
            option = (MaterialButton) layoutInflater.inflate(R.layout.option_item, multipleChoice, false);
            option.setText(options.get(i).getCaption());
            option.setId(i);
            option.setOnClickListener(v -> onButtonChecked(multipleChoice, v.getId(), ((MaterialButton)v).isChecked()));
            multipleChoice.addView(option);
        }
        questionView.addView(multipleChoice);

        switch (QestionType) {
            case Globals.MULTI_SELECT:
                multipleChoice.setSingleSelection(false);
            case Globals.SINGLE_SELECT:
            default:
                multipleChoice.setSingleSelection(true);
        }
    }

    public void displayContinueButton(boolean isQuestionAnswered) {
        if(isQuestionAnswered) {
            nextQuestion.setVisibility(View.VISIBLE);
        } else {
            nextQuestion.setVisibility(View.GONE);
        }


    }

    @SuppressLint("ResourceType")
    @Override
    public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        List<Integer> selectedButtons = group.getCheckedButtonIds();
        if(isChecked) {
            answer = new Answer();

            answer.setQuestion(questionController.currentQuestion.getRef());
            answerData.setRef(questionController.availableAnswers.get(checkedId).getRef());
            answerData.setText(questionController.availableAnswers.get(checkedId).getCaption());
            answerData.setListItem(questionController.availableAnswers.get(checkedId).getRef());
            answer.setAnswerData(answerData);
            // hack for now
            while(answers.size() < checkedId) {
                answers.add(new Answer());
            }
            answers.add(checkedId, answer);
        } else {
            answers.remove(checkedId);
        }

        if(selectedButtons.size() > 0) {
            displayContinueButton(true);
        } else {
            displayContinueButton(false);
        }
    }
}
