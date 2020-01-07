package sdk.kitso.feedbackmaster.repository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import sdk.kitso.feedbackmaster.Globals;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.db.Survey;
import sdk.kitso.feedbackmaster.db.SurveyDB;
import sdk.kitso.feedbackmaster.survey.SurveysFragment;

public class SurveyBoundaryCallback extends PagedList.BoundaryCallback<Survey> {
    @Override
    public void onItemAtEndLoaded(@NonNull Survey itemAtEnd) {
        Globals.executor.execute(() ->{
            List<Survey> data = SurveysFragment.dataSourceImplementation.fetchSurveys(itemAtEnd.getId());
            for(Survey survey: data) {
                MainActivity.surveyDB.surveyDao().addSurvey(survey);
            }
        });
    }
}
