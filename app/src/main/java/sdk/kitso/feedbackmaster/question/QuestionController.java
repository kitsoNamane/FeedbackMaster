package sdk.kitso.feedbackmaster.question;

import android.util.Log;

import java.util.List;

import sdk.kitso.feedbackmaster.model.Question;
import sdk.kitso.feedbackmaster.model.QuestionDataItem;
import sdk.kitso.feedbackmaster.model.Questions;
import sdk.kitso.feedbackmaster.survey.SurveysFragment;

// Make it a Singleton: If it's already created re-use the instantiated one
public class QuestionController {
    private int maxQuestions;
    private static QuestionController instance;
    public QuestionDataItem currentQuestion;
    private List<QuestionDataItem> questions;
    public int listIterator;

    private QuestionController() {
        currentQuestion = new QuestionDataItem();
    }

    public void setQuestions(List<QuestionDataItem> questions) {
        this.questions = questions;
        this.maxQuestions = this.questions.size();
        Log.d("FMDIGILAB", "MAX Questions : "+this.maxQuestions);
        this.listIterator = -1;
    }

    public static QuestionController getInstance() {
        if(instance == null) {
            instance = new QuestionController();
        }
        return instance;
    }


    public QuestionDataItem nextQuestion() {
        this.listIterator += 1;
        if(this.listIterator < this.maxQuestions) {
            this.currentQuestion = this.questions.get(this.listIterator);
            return this.currentQuestion;
        }
        this.listIterator -= 1;
        this.currentQuestion = null;
        return this.currentQuestion;
    }

    public QuestionDataItem previousQuestion() {
        this.listIterator -= 1;
        if(this.listIterator >= 0) {
            this.currentQuestion = this.questions.get(this.listIterator);
            return this.currentQuestion;
        }
        this.listIterator += 1;
        return this.currentQuestion;
    }
}
