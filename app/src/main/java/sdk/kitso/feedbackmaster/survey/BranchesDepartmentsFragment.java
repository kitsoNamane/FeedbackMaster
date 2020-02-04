package sdk.kitso.feedbackmaster.survey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.R;
import sdk.kitso.feedbackmaster.model.BranchDataItem;
import sdk.kitso.feedbackmaster.model.ChildrenDataItem;
import sdk.kitso.feedbackmaster.model.DataItem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link BranchesDepartmentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BranchesDepartmentsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MaterialTextView company;
    MaterialTextView survey;
    MaterialTextView numberOfQuestions;
    MaterialTextView numberOfRespondents;
    MaterialTextView expiryDate;
    MaterialTextView categoryHelpText;
    FlexboxLayout selectedCategories;
    FlexboxLayout getSelectedCategories;
    Chip chipItem;
    Chip selectedChipItem;
    DataItem item;
    LayoutInflater layoutInflater;
    MaterialButton start;

    MaterialAlertDialogBuilder materialAlertDialogBuilder;
    BranchesDepartmentsFragmentArgs branchesDepartmentsFragmentArgs;
    QuestionnaireViewModel questionnaireViewModel;
    String surveyReference;
    String businessReference;


    public BranchesDepartmentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BranchesDepartmentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BranchesDepartmentsFragment newInstance(String param1, String param2) {
        BranchesDepartmentsFragment fragment = new BranchesDepartmentsFragment();
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        questionnaireViewModel = ViewModelProviders.of(this).get(QuestionnaireViewModel.class);
        questionnaireViewModel.init();
        questionnaireViewModel.getNetworkState().observe(getViewLifecycleOwner(), networkState -> {
            switch (networkState.getStatus()) {
                case FAILED:
                    if (networkState.getMsg().equalsIgnoreCase("Access Denied")) {
                        delayedDialogBox(networkState.getMsg());
                    }
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

        questionnaireViewModel.getQuestionnaire().observe(getViewLifecycleOwner(), questionnaire->{
            if(questionnaire != null && questionnaire.getQuestionBusiness().getRef().equals(businessReference)) {
                BranchesDepartmentsFragmentDirections.ActionBeginSurvey actionBeginSurvey = BranchesDepartmentsFragmentDirections.actionBeginSurvey(
                        branchesDepartmentsFragmentArgs.getCurrentSurvey(), surveyReference, businessReference, questionnaire
                );
                MainActivity.navController.navigate(actionBeginSurvey);
            } else {
            }
        });
        this.start.setOnClickListener(v -> {
            MainActivity.questionnaireAnswer.setBusiness(businessReference);
            MainActivity.questionnaireAnswer.setCampaign(surveyReference);
            MainActivity.questionnaireAnswer.setCountry(item.getBusiness().getBusinessData().getCountry().getCountryData().getKey());
            questionnaireViewModel.getQuestionsFromServer(surveyReference, businessReference);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        branchesDepartmentsFragmentArgs = BranchesDepartmentsFragmentArgs.fromBundle(getArguments());
        View view =  inflater.inflate(R.layout.fragment_branches_departments, container, false);
        company = view.findViewById(R.id.company_name);
        survey = view.findViewById(R.id.survey_title);
        categoryHelpText = view.findViewById(R.id.category_help_text);
        numberOfQuestions = view.findViewById(R.id.number_of_questions);
        numberOfRespondents = view.findViewById(R.id.number_of_respondents);
        expiryDate = view.findViewById(R.id.expiry_date);
        layoutInflater = this.getLayoutInflater();
        selectedCategories = view.findViewById(R.id.selected_chip_group);
        getSelectedCategories = view.findViewById(R.id.chip_group);
        start = view.findViewById(R.id.start_survey);
        item = branchesDepartmentsFragmentArgs.getCurrentSurvey();

        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(
                this.getContext(),
                R.style.ThemeOverlay_MaterialComponents_Dialog_Alert
        );
        materialAlertDialogBuilder.setTitle("Feedback Master");
        materialAlertDialogBuilder.setCancelable(true);

        survey.setText(this.item.getName());
        company.setText(this.item.getBusiness().getBusinessData().getName());

        expiryDate.setText(item.getEnds());
        numberOfRespondents.setText(Integer.toString(item.getEntries().getTotal()));
        numberOfQuestions.setText(Integer.toString(item.getQuestions().getNumberOfQuestion()));

        if(surveyReference == null && businessReference == null) {
            this.start.setVisibility(View.GONE);
        }
        surveyReference = item.getReference();
        renderBranches();
        return view;
    }

    public void renderBranches() {
        List<ChildrenDataItem> children = item.getBusiness().getBusinessData().getChildren().getData();
        if(children.size() > 0) {
            for(ChildrenDataItem child : children) {
                chipItem = (Chip) layoutInflater.inflate(R.layout.category_chip, getSelectedCategories, false);
                chipItem.setText(child.getName());

                chipItem.setOnClickListener(v -> {
                    Chip selectedChip = (Chip) v;
                    setSelectedCategories(selectedChip);
                    if(child.getChildren().getData().size() <= 0) {
                        businessReference = child.getRef();
                        categoryHelpText.setText("Press Start Survey");
                        start.setVisibility(View.VISIBLE);
                    }
                    BranchesDepartmentsFragment.this.renderDepartments(child);
                });
                getSelectedCategories.addView(chipItem);
            }
        } else {
            businessReference = item.getBusiness().getBusinessData().getRef();
            start.setVisibility(View.VISIBLE);
            categoryHelpText.setText("Press Start Survey");
        }
    }

    public void setSelectedCategories(Chip chip) {
        selectedChipItem = (Chip) layoutInflater.inflate(R.layout.selected_category_chip, selectedCategories, false);
        selectedChipItem.setText(chip.getText());
        selectedChipItem.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCategories.removeAllViews();
                start.setVisibility(View.GONE);
                getSelectedCategories.removeAllViews();
                categoryHelpText.setText("Do you want to give feedback to the following:");
                renderBranches();
            }
        });
        selectedCategories.addView(selectedChipItem);
    }

    public void renderDepartments(ChildrenDataItem childObj) {
        List<BranchDataItem> children = childObj.getChildren().getData();
        getSelectedCategories.removeAllViews();
        if(children.size() > 0) {
            for(BranchDataItem child : children) {
                chipItem = (Chip) layoutInflater.inflate(R.layout.category_chip, getSelectedCategories, false);
                chipItem.setText(child.getName());
                chipItem.setOnClickListener(v -> {
                    Chip selectedChip = (Chip) v;
                    businessReference = child.getRef();
                    categoryHelpText.setText("Press Start Survey");
                    start.setVisibility(View.VISIBLE);

                    selectedChipItem = (Chip) layoutInflater.inflate(R.layout.selected_category_chip, selectedCategories, false);
                    selectedChipItem.setText(selectedChip.getText());
                    selectedChipItem.setOnCloseIconClickListener(v1 -> {
                        selectedCategories.removeView(v1);
                        categoryHelpText.setText("Do you want to give feedback to the following:");
                        start.setVisibility(View.GONE);
                        renderDepartments(childObj);
                    });
                    selectedCategories.addView(selectedChipItem);
                    getSelectedCategories.removeAllViews();
                });

                getSelectedCategories.addView(chipItem);
            }
        } else {
            getSelectedCategories.removeAllViews();
        }
    }

    public void delayedDialogBox(String message) {
        materialAlertDialogBuilder.setMessage(message)
                .setPositiveButton("Back", (dialog, which)->{
                    dialog.dismiss();
                    MainActivity.navController.popBackStack();
                }).show();
    }
}
