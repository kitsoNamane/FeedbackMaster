package sdk.kitso.feedbackmaster.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Survey.class, Branch.class, Department.class,
        Profile.class, Question.class, MultipleChoiceOption.class}, version = 4)
public abstract class SurveyDB extends RoomDatabase {
    public abstract SurveyDao surveyDao();
}
