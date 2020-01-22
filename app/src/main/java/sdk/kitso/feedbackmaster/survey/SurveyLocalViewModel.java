package sdk.kitso.feedbackmaster.survey;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import sdk.kitso.feedbackmaster.model.Survey;
import sdk.kitso.feedbackmaster.model.SurveyDao;

public class SurveyLocalViewModel extends ViewModel {
    private SurveyDao surveyDao;
    private Executor executor;
    private int pageSize = 10;
    public LiveData<PagedList<Survey>> surveys;

    public void init(SurveyDao surveyDao) {
        executor = Executors.newFixedThreadPool(5);
        this.surveyDao = surveyDao;
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(pageSize*2)
                .setEnablePlaceholders(false)
                .build();
        this.surveys = new LivePagedListBuilder<>(surveyDao.getAllSurveys(), config)
                .setFetchExecutor(executor)
                .build();
    }
}
