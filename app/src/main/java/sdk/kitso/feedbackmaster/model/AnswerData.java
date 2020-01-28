package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class AnswerData {
    @SerializedName("ref")
    private String ref;
    @SerializedName("list")
    private List<String> list;
    @SerializedName("text")
    private String text;

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setListItem(String item) {
        if(this.list == null) {
            this.list = new ArrayList<>();
        }
        this.list.add(item);
    }

    public List<String> getList() {
        return list;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "AnswerData{" +
                        "ref = '" + ref + '\'' +
                        ",list = '" + list + '\'' +
                        ",text = '" + text + '\'' +
                        "}";
    }
}
