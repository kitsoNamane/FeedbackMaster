package sdk.kitso.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Answer implements Serializable {
    @SerializedName("question")
    private String question;

    @SerializedName("answer")
    @Expose
    private AnswerData answerData;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public AnswerData getAnswer() {
        return answerData;
    }

    public void setAnswerData(AnswerData answerData) {
        this.answerData = answerData;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "Answer{" +
                        "question = '" + question + '\'' +
                        ",answer = '" + answerData + '\'' +
                        "}";
    }
}

