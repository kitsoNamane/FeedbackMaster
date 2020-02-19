package sdk.feedbackmaster.model;

import java.io.Serializable;

public class Hint implements Serializable {

    private int hintId;
    private String hintText;
    private int hintImage;

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    public int getHintImage() {
        return hintImage;
    }

    public void setHintImage(int hintImage) {
        this.hintImage = hintImage;
    }

    public int getHintId() {
        return hintId;
    }

    public void setHintId(int hintId) {
        this.hintId = hintId;
    }
}
