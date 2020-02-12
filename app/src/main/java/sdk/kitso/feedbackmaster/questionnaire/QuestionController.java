package sdk.kitso.feedbackmaster.questionnaire;

import java.util.List;

import sdk.kitso.feedbackmaster.model.AnswersItem;
import sdk.kitso.feedbackmaster.model.QuestionsItem;

// Make it a Singleton: If it's already created re-use the instantiated one
public class QuestionController {
    public int maxQuestions;
    private static QuestionController instance;
    public QuestionsItem currentQuestion;

    public List<AnswersItem> availableAnswers;
    private List<QuestionsItem> questions;
    public int listIterator;

    private QuestionController() {
        currentQuestion = new QuestionsItem();
    }

    public void setQuestions(List<QuestionsItem> questions) {
        this.questions = questions;
        this.maxQuestions = this.questions.size();
        this.listIterator = -1;
    }

    public static QuestionController getInstance() {
        if(instance == null) {
            instance = new QuestionController();
        }
        return instance;
    }

    public QuestionsItem nextQuestion() {
        this.listIterator += 1;
        if(this.listIterator < this.maxQuestions) {
            this.currentQuestion = this.questions.get(this.listIterator);
            availableAnswers = this.currentQuestion.getAnswers();
            return this.currentQuestion;
        }
        this.listIterator -= 1;
        this.currentQuestion = null;
        return this.currentQuestion;
    }

    public QuestionsItem previousQuestion() {
        this.listIterator -= 1;
        if(this.listIterator >= 0) {
            this.currentQuestion = this.questions.get(this.listIterator);
            availableAnswers = this.currentQuestion.getAnswers();
            return this.currentQuestion;
        }
        this.listIterator += 1;
        return this.currentQuestion;
    }
}
