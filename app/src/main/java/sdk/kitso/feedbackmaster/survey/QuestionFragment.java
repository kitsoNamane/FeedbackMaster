package sdk.kitso.feedbackmaster.survey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textview.MaterialTextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import sdk.kitso.feedbackmaster.Globals;
import sdk.kitso.feedbackmaster.MyListAdapter;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.question.QuestionController;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionFragment.OnFragmentInteractionListener} interface
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
    MaterialTextView questionTitle;
    ListView group;
    View questionContent;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

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
        //questionController.setQuestions(questionFragmentArgs.getSurveyId());
        //questionController.setQuestions();
        questionTitle = view.findViewById(R.id.question_title);
        return view;
    }
        /**questionTitle.setText(questionController.nextQuestion().getQuestion());
        Toast.makeText(this.getContext(), "OPTIONS : "+questionController.options.size(), Toast.LENGTH_LONG).show();
        renderQuestion();
        questionNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                // Do nothing for now
            }
        });

        questionNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.question_next:
                        questionController.nextQuestion();
                        if(questionController.currentQuestion == null) {
                            Navigation.findNavController(view).navigate(QuestionFragmentDirections.actionCompleted());
                            break;
                        }
                        questionTitle.setText(questionController.currentQuestion.getQuestion());
                        renderQuestion();
                        Toast.makeText(view.getContext(), "Question ID : "+questionController.currentQuestion.getId(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.previous_question:
                        questionTitle.setText(questionController.previousQuestion().getQuestion());
                        renderQuestion();
                        Toast.makeText(view.getContext(), "Question ID : "+questionController.currentQuestion.getId(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.dummy:
                        break;
                    default:
                        // Do something
                }
                return false;
            }
        });

        //MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        //BottomNavigationView navView = view.findViewById(R.id.nav_view);
        //View decorView = this.getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        //int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        //        | View.SYSTEM_UI_FLAG_FULLSCREEN;
        //decorView.setSystemUiVisibility(uiOptions);
        //toolbar.setVisibility(View.GONE);
        // navView.setVisibility(View.GONE);
        return view;
    }

    public View setQuestionContent(@LayoutRes int i) {
       return getLayoutInflater().inflate(i, questionView, false);
    }

    public void renderQuestion() {
        View view = questionNav.findViewById(R.id.dummy);
        switch(questionController.currentQuestion.getType()) {
            case Globals.RATING_STARS:
                questionContent = setQuestionContent(R.layout.rating_stars);
                questionView.removeAllViews();
                questionView.addView(questionContent);
                break;
            case Globals.MULTIPLE_CHOICE:
                questionContent = setQuestionContent(R.layout.multiple_choice);
                group = questionContent.findViewById(R.id.multiply_options);
                addOptions(group, ListView.CHOICE_MODE_SINGLE);
                break;
            case Globals.MULTIPLE_CHOICES:
                questionContent = setQuestionContent(R.layout.multiple_choice);
                group = questionContent.findViewById(R.id.multiply_options);
                addOptions(group, ListView.CHOICE_MODE_MULTIPLE);
                break;
            case Globals.TRUE_FALSE:
                questionContent = setQuestionContent(R.layout.true_or_false);
                questionView.removeAllViews();
                questionView.addView(questionContent);
                break;
            case Globals.SHORT_ANSWER:
                questionContent = setQuestionContent(R.layout.short_answer);
                questionView.removeAllViews();
                questionView.addView(questionContent);
                break;
            case Globals.SCALE:
                questionContent = getLayoutInflater().inflate(R.layout.rating_stars, questionView, false);
                questionView.removeAllViews();
                questionView.addView(questionContent);
                break;
            default:
                // multiple choice
                questionContent = getLayoutInflater().inflate(R.layout.rating_stars, questionView, false);
                questionView.addView(questionContent);
                questionView.removeAllViews();
                questionTitle.setText(questionController.nextQuestion().getQuestion());
        }
    }

    public void addOptions(ListView myGroup, int CHOICE_MODE) {
        myGroup.setAdapter(new MyListAdapter(this.getContext(), questionController.options.get(0).getOptions()));
        myGroup.setDivider(null);
        myGroup.setChoiceMode(CHOICE_MODE);
        Toast.makeText(this.getContext(), "COWS : "+questionController.options.get(0).getOptions().size(), Toast.LENGTH_LONG).show();
        questionView.removeAllViews();
        questionView.addView(questionContent);
    }


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
