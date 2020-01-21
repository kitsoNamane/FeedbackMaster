package sdk.kitso.feedbackmaster.survey;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

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
    Group branch;
    Group department;
    ChipGroup branches;
    ChipGroup departments;
    Chip chipItem;
    MaterialButton start;

    BranchesDepartmentsFragmentArgs branchesDepartmentsFragmentArgs;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        branchesDepartmentsFragmentArgs = BranchesDepartmentsFragmentArgs.fromBundle(getArguments());
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_branches_departments, container, false);
        this.company = view.findViewById(R.id.company_name);
        this.survey = view.findViewById(R.id.survey_title);
        this.branches = view.findViewById(R.id.branchOld);
        this.departments = view.findViewById(R.id.department);
        this.department = view.findViewById(R.id.department_list);
        this.branch = view.findViewById(R.id.branch_list);
        this.start = view.findViewById(R.id.start_survey);

        renderBranches(branchesDepartmentsFragmentArgs.getCurrentSurvey());

        this.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }

    public void renderBranches(DataItem item) {
        List<ChildrenDataItem> children = item.getBusiness().getBusinessData().getChildren().getData();
        if(children.size() > 0) {
            if(branches.getChildCount() > 0) {
                branch.setVisibility(View.VISIBLE);
                return;
            }

            for(ChildrenDataItem child : children) {
                chipItem = new Chip(this.getContext());
                Log.d("FMDIGITAL", child.getName());
                chipItem.setText(child.getName());
                chipItem.setCheckable(true);
                chipItem.setCheckedIcon(
                        this.getContext().getResources().getDrawable(
                                R.drawable.ic_check_black_24dp
                        )
                );

                chipItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        renderDepartments(child);
                    }
                });


                branches.addView(chipItem);
            }
            branch.setVisibility(View.VISIBLE);
        }
    }

    public void renderDepartments(ChildrenDataItem childObj) {
        List<BranchDataItem> children = childObj.getChildren().getData();
        if(children.size() > 0) {
            if(departments.getChildCount() > 0) {
                departments.removeAllViews();
            }

            for(BranchDataItem child : children) {
                chipItem = new Chip(this.getContext());
                Log.d("FMDIGITAL Dep", child.getName());
                chipItem.setText(child.getName());
                chipItem.setCheckable(true);

                chipItem.setCheckedIcon(
                        this.getContext().getResources().getDrawable(
                                R.drawable.ic_check_black_24dp
                        )
                );
                departments.addView(chipItem);
            }
            department.setVisibility(View.VISIBLE);
        } else {
            departments.removeAllViews();
            department.setVisibility(View.GONE);
        }
    }

    public void setVisibility(int VISIBILITY) {
        this.branch.setVisibility(VISIBILITY);
        this.department.setVisibility(VISIBILITY);
        this.start.setVisibility(VISIBILITY);
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
