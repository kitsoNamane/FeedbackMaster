package sdk.kitso.feedbackmaster.survey;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import sdk.kitso.feedbackmaster.MainActivity;
import sdk.kitso.feedbackmaster.NetworkState;
import sdk.kitso.feedbackmaster.model.SearchResponse;
import sdk.kitso.feedbackmaster.repository.FeedbackMasterSearchApi;

public class SearchViewModel extends ViewModel {
    public Runnable reload;
    private FeedbackMasterSearchApi feedbackMasterSearchApi = FeedbackMasterSearchApi.getInstance(MainActivity.feedbackMasterSurveyApiService);
    private MutableLiveData<String> searchString = new MutableLiveData<>();
    private MutableLiveData<Integer> getNetworkArgs = new MutableLiveData<>(0);
    public LiveData<SearchResponse> searchResults = Transformations.switchMap(
        searchString, (searchKeyword)-> feedbackMasterSearchApi.getSearchResults(searchKeyword)
    );

    public LiveData<NetworkState> networkState = Transformations.switchMap(getNetworkArgs,
            args -> feedbackMasterSearchApi.getNetworkState()
    );

    public void retry() {
        Thread thread = new Thread(
                reload
        );
        thread.run();
    }

    public void search(String keyword) {
        searchString.setValue(keyword);
        getNetworkArgs.setValue(getNetworkArgs.getValue()+1);
        reload = ()-> search(keyword);
    }

    public LiveData<NetworkState> getNetworkState() {
        if(networkState == null) {
            networkState = new MutableLiveData<>();
        }
        return networkState;
    }

    public LiveData<SearchResponse> getSearchResults() {
        if(networkState == null) {
            networkState = new MutableLiveData<>();
        }
        return searchResults;
    }
}
