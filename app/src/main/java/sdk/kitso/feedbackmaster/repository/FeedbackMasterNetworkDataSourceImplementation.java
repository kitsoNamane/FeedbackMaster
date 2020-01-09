package sdk.kitso.feedbackmaster.repository;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdk.kitso.feedbackmaster.db.Survey;

public class FeedbackMasterNetworkDataSourceImplementation implements FeedbackMasterNetworkDataSource {
    FeedbackMasterSurveyApiService apiService;
    Context context;

    public FeedbackMasterNetworkDataSourceImplementation(FeedbackMasterSurveyApiService api, Context context) {
       this.apiService = api;
       this.context = context;
    }

    @Override
    public List<Survey> fetchSurveys(int pageEnd) {
        final List<Survey> surveylist = new ArrayList<Survey>();
        Call<List<Survey>> call = apiService.getNextSurveys(pageEnd);
        call.enqueue(new Callback<List<Survey>>() {
            @Override
            public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                if(response.isSuccessful()) {
                    surveylist.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Survey>> call, Throwable throwable) {
                Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show();
            }
        });
        return surveylist;
    }
}
