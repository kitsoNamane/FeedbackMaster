package sdk.kitso.feedbackmaster.repository;

import java.util.List;

import sdk.kitso.feedbackmaster.db.Survey;

public interface FeedbackMasterNetworkDataSource {
    public List<Survey> fetchSurveys(
            int pageEnd
    );
}
