package sdk.kitso.feedbackmaster.question;

import java.util.List;

import sdk.kitso.feedbackmaster.db.MultipleChoiceOption;
import sdk.kitso.feedbackmaster.db.Question;
import sdk.kitso.feedbackmaster.db.QuestionsAndAllOptions;
import sdk.kitso.feedbackmaster.survey.SurveysFragment;

// Make it a Singleton: If it's already created re-use the instantiated one
public class QuestionController {
    private int maxQuestions;
    private static QuestionController instance;
    public Question currentQuestion;
    private List<Question> questions;
    public int listIterator;
    public List<QuestionsAndAllOptions> options;

    private QuestionController() {
        currentQuestion = new Question();
    }

    public void setQuestions(int surveyId) {
        this.questions = SurveysFragment.questionDB.questionDao()
                .getQuestions(surveyId);
        //Log.i("Size : "+this.questions.size()+" SurveyId : "+surveyId, "Help");
        this.maxQuestions = this.questions.size();
        this.listIterator = -1;
    }

    public static QuestionController getInstance() {
        if(instance == null) {
            instance = new QuestionController();
        }
        return instance;
    }

    public List<MultipleChoiceOption> nextOption() {
        if(this.options == null || this.options.size() <= 0) {
            return null;
        }
        return null;
    }

    public Question nextQuestion() {
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
}
