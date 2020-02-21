package sdk.feedbackmaster.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tutorial implements Serializable {
    private int tutorialId;

    private List<Hint> tutorialContent;

    public int getTutorialId() {
        return tutorialId;
    }

    public void setTutorialId(int tutorialId) {
        this.tutorialId = tutorialId;
    }

    public List<Hint> getTutorialContent() {
        return tutorialContent;
    }

    public void setTutorialContent(List<Hint> tutorialContent) {
        this.tutorialContent = tutorialContent;
    }

    public void removeAllTutorials() {
        this.tutorialContent = null;
        this.tutorialContent = new ArrayList<>();
    }

    public void setTutorialHint(Hint tutorialHint) {
        tutorialContent = tutorialContent != null ? tutorialContent : new ArrayList<>();
        tutorialContent.add(tutorialHint);
    }
}
