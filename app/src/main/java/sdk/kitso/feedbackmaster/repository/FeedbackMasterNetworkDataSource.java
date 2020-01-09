package sdk.kitso.feedbackmaster.repository;


import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import sdk.kitso.feedbackmaster.db.DataItem;

public class FeedbackMasterNetworkDataSource extends PageKeyedDataSource<Integer, DataItem> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> loadInitialParams, @NonNull LoadInitialCallback<Integer, DataItem> loadInitialCallback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, DataItem> loadCallback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, DataItem> loadCallback) {

    }
}
