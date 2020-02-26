package sdk.feedbackmaster.controllers;

import java.util.List;

import sdk.feedbackmaster.model.AnswersItem;
import sdk.feedbackmaster.model.QuestionsItem;

// Make it a Singleton: If it's already created re-use the instantiated one
public class QuestionController {
    private int maxQuestions;
    private static QuestionController instance;
    private QuestionsItem currentQuestion;

    private List<AnswersItem> availableAnswers;
    private List<QuestionsItem> questions;
    private int index;

    private QuestionController() {
        currentQuestion = new QuestionsItem();
    }

    public void setQuestions(List<QuestionsItem> questions) {
        this.questions = questions;
        this.maxQuestions = this.questions.size();
        this.index = -1;
    }

    public static QuestionController getInstance() {
        if(instance == null) {
            instance = new QuestionController();
        }
        return instance;
    }

    public QuestionsItem getCurrentQuestion() {
        return currentQuestion;
    }

    public int getIndex() {
        return index;
    }

    public int getMaxQuestions() {
        return maxQuestions;
    }

    public List<AnswersItem> getAvailableAnswers() {
        return availableAnswers;
    }

    public AnswersItem getAnswerItem(int index) {
        return availableAnswers.get(index);
    }

    public List<QuestionsItem> getQuestions() {
        return questions;
    }

    public QuestionsItem getQuestion(int index) {
        return this.questions.get(index);
    }

    public QuestionsItem nextQuestion() {
        this.index += 1;
        if(this.index < this.maxQuestions) {
            this.currentQuestion = this.questions.get(this.index);
            availableAnswers = this.currentQuestion.getAnswers();
            return this.currentQuestion;
        }
        this.index -= 1;
        this.currentQuestion = null;
        return this.currentQuestion;
    }
}
