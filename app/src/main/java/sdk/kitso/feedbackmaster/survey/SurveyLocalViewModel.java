package sdk.kitso.feedbackmaster.survey;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import sdk.kitso.feedbackmaster.db.Survey;
import sdk.kitso.feedbackmaster.db.SurveyDao;

public class SurveyLocalViewModel {
    private SurveyDao surveyDao;
    public final LiveData<PagedList<Survey>> surveys;

    public SurveyLocalViewModel(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;
        surveys = new LivePagedListBuilder<>(surveyDao.getAllSurveys(), 10).build();
    }

}
