package sdk.feedbackmaster.ui.survey_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import sdk.feedbackmaster.MainActivity;
import sdk.feedbackmaster.R;
import sdk.feedbackmaster.model.BranchDataItem;
import sdk.feedbackmaster.model.ChildrenDataItem;
import sdk.feedbackmaster.model.DataItem;
import sdk.feedbackmaster.viewmodels.QuestionnaireViewModel;

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
    private MaterialTextView company;
    private MaterialTextView survey;
    private MaterialTextView numberOfQuestions;
    private MaterialTextView numberOfRespondents;
    private MaterialTextView expiryDate;
    private MaterialTextView categoryHelpText;
    private LinearLayout selectedCategories;
    private FlexboxLayout getSelectedCategories;
    private Chip selectedChipItem;
    private DataItem item;
    private LayoutInflater layoutInflater;
    private MaterialButton start;
    private MaterialButton toggleButton;

    private MaterialTextView businessIntro;
    private BranchesDepartmentsFragmentArgs branchesDepartmentsFragmentArgs;
    static QuestionnaireViewModel questionnaireViewModel;
    private String surveyReference;
    private String businessReference;


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
        questionnaireViewModel = new ViewModelProvider(requireActivity()).get(QuestionnaireViewModel.class);
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
                questionnaireViewModel.clearNetworkState();
                MainActivity.navController.navigate(actionBeginSurvey);
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

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Feedback Master");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Organisation Categories");
        company = view.findViewById(R.id.company_title);
        survey = view.findViewById(R.id.survey_title);
        categoryHelpText = view.findViewById(R.id.category_help_text);
        numberOfQuestions = view.findViewById(R.id.number_of_questions);
        numberOfRespondents = view.findViewById(R.id.number_of_respondents);
        expiryDate = view.findViewById(R.id.expiry_date);
        layoutInflater = this.getLayoutInflater();
        selectedCategories = view.findViewById(R.id.selected_chip_group);
        getSelectedCategories = view.findViewById(R.id.chip_group);
        businessIntro = view.findViewById(R.id.intro);
        start = view.findViewById(R.id.start_survey);
        item = branchesDepartmentsFragmentArgs.getCurrentSurvey();

        if(getChildFragmentManager().getFragments().size() > 0) {
            Toast.makeText(this.getContext(), "More Children", Toast.LENGTH_SHORT).show();
        }

        survey.setText(this.item.getName());
        company.setText(this.item.getBusiness().getBusinessData().getName());
        if(item.getIntro() != null) {
            businessIntro.setText(item.getIntro());
        } else {
            businessIntro.setVisibility(View.GONE);
        }

        //expiryDate.setText(item.getEnds());
        //numberOfRespondents.setText(Integer.toString(item.getEntries().getTotal()));
        //numberOfQuestions.setText(Integer.toString(item.getQuestions().getNumberOfQuestion()));

        if(surveyReference == null && businessReference == null) {
            this.start.setVisibility(View.GONE);
        }
        surveyReference = item.getAlias();
        renderBranches();
        return view;
    }

    public void renderBranches() {
        List<ChildrenDataItem> children = item.getBusiness().getBusinessData().getChildren().getData();
        if(children.size() > 0) {
            for(ChildrenDataItem child : children) {
                toggleButton = (MaterialButton) layoutInflater.inflate(R.layout.option_item, getSelectedCategories, false);
                toggleButton.setText(child.getName());

                toggleButton.setOnClickListener(v -> {
                    MaterialButton selectedChip = (MaterialButton) v;
                    setSelectedCategories(selectedChip);
                    if(child.getChildren().getData().size() <= 0) {
                        businessReference = child.getAlias();
                        categoryHelpText.setText("Press Start Survey");
                        start.setVisibility(View.VISIBLE);
                    }
                    BranchesDepartmentsFragment.this.renderDepartments(child);
                });
                getSelectedCategories.addView(toggleButton);
            }
        } else {
            businessReference = item.getBusiness().getBusinessData().getAlias();
            start.setVisibility(View.VISIBLE);
            categoryHelpText.setText("Press Start Survey");
        }
    }

    public void setSelectedCategories(MaterialButton btn) {
        selectedChipItem = (Chip) layoutInflater.inflate(R.layout.selected_category_chip, selectedCategories, false);
        selectedChipItem.setCloseIcon(getResources().getDrawable(R.drawable.ic_close_black_18dp));
        selectedChipItem.setCloseIconVisible(true);
        selectedChipItem.setText(btn.getText());
        selectedChipItem.setOnCloseIconClickListener(v -> {
            selectedCategories.removeAllViews();
            start.setVisibility(View.GONE);
            getSelectedCategories.removeAllViews();
            categoryHelpText.setText("Do you want to give feedback to the following:");
            renderBranches();
        });
        selectedCategories.addView(selectedChipItem);
    }

    public void renderDepartments(ChildrenDataItem childObj) {
        List<BranchDataItem> children = childObj.getChildren().getData();
        getSelectedCategories.removeAllViews();
        if(children.size() > 0) {
            for(BranchDataItem child : children) {
                toggleButton = (MaterialButton) layoutInflater.inflate(R.layout.option_item, getSelectedCategories, false);
                toggleButton.setText(child.getName());
                toggleButton.setOnClickListener(v -> {
                    MaterialButton selectedChip = (MaterialButton) v;
                    businessReference = child.getAlias();
                    categoryHelpText.setText("Press Start Survey");
                    start.setVisibility(View.VISIBLE);

                    selectedChipItem = (Chip) layoutInflater.inflate(R.layout.selected_category_chip, selectedCategories, false);
                    selectedChipItem.setCloseIcon(getResources().getDrawable(R.drawable.ic_close_black_18dp));
                    selectedChipItem.setCloseIconVisible(true);
                    selectedChipItem.setText(selectedChip.getText());
                    selectedChipItem.setLayoutParams(
                            new ChipGroup.LayoutParams(
                                    ChipGroup.LayoutParams.WRAP_CONTENT, ChipGroup.LayoutParams.WRAP_CONTENT)
                    );
                    selectedChipItem.setOnCloseIconClickListener(v1 -> {
                        selectedCategories.removeView(v1);
                        categoryHelpText.setText("Do you want to give feedback to the following:");
                        start.setVisibility(View.GONE);
                        renderDepartments(childObj);
                    });
                    selectedCategories.addView(selectedChipItem);
                    getSelectedCategories.removeAllViews();
                });

                getSelectedCategories.addView(toggleButton);
            }
        } else {
            getSelectedCategories.removeAllViews();
        }
    }

    public void delayedDialogBox(String message) {
        AlertDialog alertDialog = new MaterialAlertDialogBuilder( this.getContext(), R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        .setTitle(message)
        .setCancelable(true)
                .setPositiveButton("Back", (dialog, which)->{
                    questionnaireViewModel.clearNetworkState();
                    MainActivity.navController.popBackStack();
                    dialog.dismiss();
                }).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}
