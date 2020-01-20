package sdk.kitso.feedbackmaster.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface QuestionDao {
    @Insert
    void addQuestion(Question question);

    @Insert
    void addOption(MultipleChoiceOption option);

    @Insert
    void addAnswer(QuestionAnswer answer);

    @Query("SELECT * from questions where surveyId = :surveyId")
    public List<Question> getQuestions(int surveyId);

    @Query("SELECT * from questions")
    public List<Question> getAllQuestions();

    @Query("SELECT * from questions where id = :questionId")
    public List<QuestionsAndAllOptions> getOptions(int questionId);
}
