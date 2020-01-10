package sdk.kitso.feedbackmaster.survey;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import sdk.kitso.feedbackmaster.db.Survey;
import sdk.kitso.feedbackmaster.db.SurveyDao;

public class SurveyLocalViewModel extends ViewModel {
    private SurveyDao surveyDao;
    public LiveData<PagedList<Survey>> surveys;

    public void init(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;
        this.surveys = new LivePagedListBuilder<>(surveyDao.getAllSurveys(), 5).build();
    }

}
