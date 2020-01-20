package sdk.kitso.feedbackmaster.question;

import java.util.List;

import sdk.kitso.feedbackmaster.model.Question;
import sdk.kitso.feedbackmaster.model.Questions;

// Make it a Singleton: If it's already created re-use the instantiated one
public class QuestionController {
    private int maxQuestions;
    private static QuestionController instance;
    public Question currentQuestion;
    private List<Question> questions;
    public int listIterator;

    private QuestionController() {
        currentQuestion = new Question();
    }

    public void setQuestions(Questions questions) {
        //this.maxQuestions = this.questions.size();
        //this.listIterator = -1;
    }

    public static QuestionController getInstance() {
        if(instance == null) {
            instance = new QuestionController();
        }
        return instance;
    }


   /** public Question nextQuestion() {
        this.listIterator += 1;
        if(this.listIterator < this.maxQuestions) {
            this.currentQuestion = this.questions.get(this.listIterator);
            this.options = SurveysFragment.questionDB.questionDao()
                    .getOptions(this.currentQuestion.getId());
            return this.currentQuestion;
        }
        this.listIterator -= 1;
        this.currentQuestion = null;
        return this.currentQuestion;
    }

    public Question previousQuestion() {
        this.listIterator -= 1;
        if(this.listIterator >= 0) {
            this.currentQuestion = this.questions.get(this.listIterator);
            this.options = SurveysFragment.questionDB.questionDao()
                    .getOptions(this.currentQuestion.getId());
            return this.currentQuestion;
        }
        this.listIterator += 1;
        return this.currentQuestion;
    }
    */
}
