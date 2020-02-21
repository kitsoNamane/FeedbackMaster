package sdk.feedbackmaster.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Hint implements Serializable {

    private int hintId;
    private String hintText;
    private Drawable hintImage;

    public Hint(int hintId, String hintText, Drawable hintImage) {
        this.hintId = hintId;
        this.hintText = hintText;
        this.hintImage = hintImage;
    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    public Drawable getHintImage() {
        return hintImage;
    }

    public void setHintImage(Drawable hintImage) {
        this.hintImage = hintImage;
    }

    public int getHintId() {
        return hintId;
    }

    public void setHintId(int hintId) {
        this.hintId = hintId;
    }
}
