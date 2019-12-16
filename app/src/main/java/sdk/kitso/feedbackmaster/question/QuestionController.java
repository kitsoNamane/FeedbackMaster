package sdk.kitso.feedbackmaster.question;

import java.util.List;
import java.util.ListIterator;

import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.db.MultipleChoiceOption;
import sdk.kitso.feedbackmaster.db.Question;

// Make it a Singleton: If it's already created re-use the instantiated one
public class QuestionController {
    private int maxQuestions;
    private static QuestionController instance;
    public Question currentQuestion;
    private List<Question> questions;
    private ListIterator<Question> questionsIterator;
    private List<MultipleChoiceOption> options;

    private QuestionController(int surveyId) {
        currentQuestion = new Question();
        this.questions = MainActivity.surveyDB.surveyDao()
                .getQuestions(surveyId)
                .iterator().next().questions;
        this.maxQuestions = this.questions.size();
        this.questionsIterator = this.questions.listIterator();
    }

    public static QuestionController getInstance(int surveyId) {
        if(instance == null) {
            instance = new QuestionController(surveyId);
        }
        return instance;
    }

    /**
    public int nextQuestion() {
        if(this.questionsIterator.hasNext()) {
            this.currentQuestion = this.questionsIterator.next();
            this.options = MainActivity.surveyDB.surveyDao()
                    .getOptions(this.currentQuestion.getId())
                    .iterator().next().options;
            return this.currentQuestion.getType();
        }
        return -1;
    }
     */

    public List<MultipleChoiceOption> nextOption() {
        if(this.options == null || this.options.size() <= 0) {
            return null;
        }
        return this.options;
    }

    public Question nextQuestion(int questionId) {
        return questions.get(questionId);
    }

    public Question nextQuestion() {
        if(this.questionsIterator.hasNext()) {
            this.currentQuestion = this.questionsIterator.next();
            this.options = MainActivity.surveyDB.surveyDao()
                    .getOptions(this.currentQuestion.getId())
                    .iterator().next().options;
            return this.currentQuestion;
        }
        return this.currentQuestion;
    }

    public Question previousQuestion() {
        if(this.questionsIterator.hasPrevious()) {
            this.currentQuestion = this.questionsIterator.previous();
            this.options = MainActivity.surveyDB.surveyDao()
                    .getOptions(this.currentQuestion.getId())
                    .iterator().next().options;
            return this.currentQuestion;
        }
        return this.currentQuestion;
    }

    /**
    public String getQuestion(int questionType) {
        switch (questionType) {
            case Globals.RATING_STARS:
                return "Rating Stars";
            case Globals.MULTIPLE_CHOICE:
                return "Multiple Choice";
            case Globals.MULTIPLE_CHOICES:
                return "Multiple Choices";
            case Globals.TRUE_FALSE:
                return "True or False";
            case Globals.SHORT_ANSWER:
                return "Short Answer";
            case Globals.SCALE:
                return "Scale";
            default:
                // Globals.RATING_STARS
                return "Rating Stars";
        }
    }
     */
}
