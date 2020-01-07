package sdk.kitso.feedbackmaster.survey;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.db.Survey;
import sdk.kitso.feedbackmaster.db.SurveyDao;
import sdk.kitso.feedbackmaster.repository.SurveyBoundaryCallback;

public class SurveyViewModel extends ViewModel {
    public LiveData<PagedList<Survey>> surveys;
    public DataSource.Factory factory;

    public void init() {
        factory = MainActivity.surveyDB.surveyDao().getAllSurveys();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(10)
                .setInitialLoadSizeHint(20)
                .setEnablePlaceholders(true)
                .build();
        surveys = new LivePagedListBuilder(factory, config)
                .setBoundaryCallback(new SurveyBoundaryCallback())
                .build();
    }
}
