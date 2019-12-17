package sdk.kitso.feedbackmaster.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Question.class, MultipleChoiceOption.class, QuestionAnswer.class}, version = 2)
public abstract class QuestionDB extends RoomDatabase {
    public abstract QuestionDao questionDao();
}
