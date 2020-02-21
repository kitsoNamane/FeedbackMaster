package sdk.feedbackmaster.controllers;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import sdk.feedbackmaster.R;
import sdk.feedbackmaster.model.Hint;
import sdk.feedbackmaster.model.Tutorial;

public class TutorialsController {
    private static TutorialsController ourInstance;
    private View tutorialView;
    private FrameLayout parent;

    private MaterialButton skip;
    private MaterialTextView hintText;
    private ImageView hintImage;
    private ChipGroup tutorialPages;
    private List<String> hintTexts;
    //private List<Drawable> hintIcons;
    TypedArray hintMessages;
    TypedArray hintIcons;

    private TutorialsController() {}

    Tutorial tutorial;

    public static TutorialsController getInstance() {
        if(ourInstance == null) {
             ourInstance = new TutorialsController();
        }
        return ourInstance;
    }

    public void initTutorial(View view) {

        /**
        tutorial = tutorial != null ? tutorial : new Tutorial();
        parent = parent != null ? parent : view.findViewById(R.id.tutorials);
        tutorialView = tutorialView != null ? tutorialView : LayoutInflater.from(view.getContext()).inflate(R.layout.tutorial, parent);
        skip = skip != null ? skip : tutorialView.findViewById(R.id.skip_tutorial);
        hintText = hintText != null ? hintText : tutorialView.findViewById(R.id.hint_text);
        hintImage = hintImage != null ? hintImage : tutorialView.findViewById(R.id.hint_image);
        tutorialPages = tutorialPages != null ? tutorialPages : tutorialView.findViewById(R.id.tutorial_pages);
         */
        tutorial = new Tutorial();
        parent = view.findViewById(R.id.tutorials);
        tutorialView = LayoutInflater.from(view.getContext()).inflate(R.layout.tutorial, parent);
        skip = tutorialView.findViewById(R.id.skip_tutorial);
        hintText =  tutorialView.findViewById(R.id.hint_text);
        hintImage = tutorialView.findViewById(R.id.hint_image);
        tutorialPages = tutorialView.findViewById(R.id.tutorial_pages);

        hintMessages = view.getContext().getResources().obtainTypedArray(R.array.hint_text);
        hintTexts = Arrays.asList(view.getContext().getResources().getStringArray(R.array.hint_text));
        hintIcons = view.getContext().getResources().obtainTypedArray(R.array.hint_icons);
        skip.setOnClickListener(v -> parent.removeAllViews());
        setTutorial(view.getId());
    }

    @SuppressLint("ResourceType")
    public void setTutorial(int viewId) {
        tutorial.setTutorialId(viewId);
        tutorial.removeAllTutorials();
        List<Hint> tuts = new ArrayList<>();
        // now set the tutorial text
        switch (viewId) {
            case R.id.fragment_surveys:
                tuts.add(0, new Hint(1, hintMessages.getString(0).trim(), hintIcons.getDrawable(0)));
                tuts.add(1, new Hint(2, hintMessages.getString(1).trim(), hintIcons.getDrawable(1)));
                tutorial.setTutorialContent(tuts);
                changeHint(1);
                break;
            case R.id.branches_departments:
                tuts.add(0, new Hint(1, hintMessages.getString(2).trim(), hintIcons.getDrawable(2)));
                tuts.add(1, new Hint(2, hintMessages.getString(3).trim(), hintIcons.getDrawable(3)));
                tutorial.setTutorialContent(tuts);
                changeHint(1);
                break;
            case R.id.questionnaire:
                tuts.add(0, new Hint(1, hintMessages.getString(4).trim(), hintIcons.getDrawable(4)));
                tutorial.setTutorialContent(tuts);
                changeHint(1);
                break;
        }

        // set tutorial hints
        tutorialPages.removeAllViews();
        for(Hint hint : tutorial.getTutorialContent()) {
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(tutorialView.getContext(), null, 0, R.style.ChipStyle);
            Chip chip = new Chip(tutorialView.getContext());
            chip.setChipDrawable(chipDrawable);
            chip.setId(hint.getHintId());
            chip.setClickable(true);
            chip.setText(String.format(Locale.getDefault(), "%d", hint.getHintId()));
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TutorialsController.this.changeHint(v.getId());
                }
            });

            if(hint.getHintId() == 1) {
                chip.setChecked(true);
            }
            tutorialPages.addView(chip);
        }
    }

    public void changeHint(int hintId) {
        Hint newHint = tutorial.getTutorialContent().get(hintId - 1);
        hintText.setText(newHint.getHintText());
        hintImage.setImageDrawable(newHint.getHintImage());
    }
}
