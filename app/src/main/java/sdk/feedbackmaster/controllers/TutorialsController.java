package sdk.feedbackmaster.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textview.MaterialTextView;

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
    private FlexboxLayout tutorialPages;

    Tutorial tutorial;

    public static TutorialsController getInstance() {
        if(ourInstance == null) {
             ourInstance = new TutorialsController();
        }
        return ourInstance;
    }

    public void initTutorial(View view) {
        tutorial = tutorial != null ? tutorial : new Tutorial();
        parent = parent != null ? parent : view.findViewById(R.id.tutorials);
        tutorialView = tutorialView != null ? tutorialView : LayoutInflater.from(view.getContext()).inflate(R.layout.tutorial, parent);
        skip = skip != null ? skip : tutorialView.findViewById(R.id.skip_tutorial);
        hintText = hintText != null ? hintText : tutorialView.findViewById(R.id.hint_text);
        hintImage = hintImage != null ? hintImage : tutorialView.findViewById(R.id.hint_image);
        tutorialPages = tutorialPages != null ? tutorialPages : tutorialView.findViewById(R.id.tutorial_pages);

        skip.setOnClickListener(v -> parent.removeAllViews());
    }

    public void setTutorial(int viewId) {
        tutorial.setTutorialId(viewId);

        // now set the tutorial text
        switch (viewId) {
            case R.id.tutorials:
            break;
            case R.id.search:
                break;
            case R.id.questionnaire:
                break;
            case R.id.branches_departments:
                break;
        }

        // set tutorial hints
        for(Hint hint : tutorial.getTutorialContent()) {
            Chip chip = new Chip(tutorialView.getContext());
            chip.setId(hint.getHintId());
            chip.setText(hint.getHintId());
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeHint(v.getId());
                }
            });
            tutorialPages.addView(chip);
        }
    }

    public void changeHint(int hintId) {
        Hint newHint = tutorial.getTutorialContent().get(hintId);
        hintText.setText(newHint.getHintText());
        hintImage.setImageDrawable(tutorialView.getResources().getDrawable(newHint.getHintImage()));
    }
}
