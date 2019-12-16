package sdk.kitso.feedbackmaster.survey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.MockData;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.db.Survey;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SurveysFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SurveysFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveysFragment extends Fragment implements SurveyAdapter.OnSurveyItemClickedListener  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SurveyAdapter adapter;
    private MockData mock;
    SurveyAdapter.SurveyViewHolder holder;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mock = new MockData();
        if(MainActivity.surveyDB.surveyDao().getSurveys().size() <= 0) {
            mock.generateSurveys(100);
        }

        if(MainActivity.surveyDB.surveyDao().getQuestions(0).size() <= 0) {
            mock.generateQuestions(100);
        }
        List<Survey> surveys = MainActivity.surveyDB.surveyDao().getSurveys();
        adapter = new SurveyAdapter(surveys, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_surveys, container, false);
        recyclerView = view.findViewById(R.id.survey_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemClicked(View view, Survey survey) {
        switch (view.getId()) {
        case(R.id.survey_card):
            holder = (SurveyAdapter.SurveyViewHolder) recyclerView.findContainingViewHolder(view);
            /**survey.setChecked(!survey.getChecked());
            holder.cardView.setChecked(survey.getChecked());
            if (holder.cardView.isChecked() == true && holder.departments.getChildCount() <= 2) {
                branches = MainActivity.surveyDB.surveyDao().getBranches(survey.getId());
                depts = MainActivity.surveyDB.surveyDao().getDepartments(survey.getId());
                // Currently O(X^2) complexity
                // find way to speed it up to O(X) complexity
                for (int i = 0; i < branches.size(); i++) {
                    for (int j = 0; j < branches.get(i).getBranches().size(); j++) {
                        checkBox = new RadioButton(view.getContext());
                        checkBox.setText(branches.get(i).getBranches().get(j).getBranch());
                        checkBox.setTextSize(Float.parseFloat("16"));
                        checkBox.setPadding(10, 10, 10, 10);
                        holder.branches.addView(checkBox);
                    }
                }
                for (int i = 0; i < depts.size(); i++) {
                    for (int j = 0; j < depts.get(i).getDepartments().size(); j++) {
                        checkBox = new RadioButton(view.getContext());
                        checkBox.setText(depts.get(i).getDepartments().get(j).getDept());
                        checkBox.setTextSize(Float.parseFloat("16"));
                        checkBox.setPadding(10, 10, 10, 10);
                        holder.departments.addView(checkBox);
                    }
                }
            }
            if (holder.cardView.isChecked()) {
                setVisibility(holder, View.VISIBLE);
            } else {
                setVisibility(holder, View.GONE);
            }
             */
            adapter.bindDynamicContent(holder, survey);
            break;
        case(R.id.start_survey):
            SurveysFragmentDirections.ActionSurvey actionSurvey = SurveysFragmentDirections.actionSurvey(survey.getId());
            Navigation.findNavController(view).navigate(actionSurvey);
            Toast.makeText(this.getContext(), "Go to Question", Toast.LENGTH_LONG).show();
            break;
        default:
            Toast.makeText(this.getContext(), "ButtonId :"+Integer.toString(R.id.start_survey)
                    +" CardId :"+Integer.toString(R.id.survey_card)+" Got :"+Integer.toString(view.getId()),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    public void setVisibility(SurveyAdapter.SurveyViewHolder myholder, int VISIBILITY) {
        myholder.branches.setVisibility(VISIBILITY);
        myholder.departments.setVisibility(VISIBILITY);
        myholder.start.setVisibility(VISIBILITY);
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
