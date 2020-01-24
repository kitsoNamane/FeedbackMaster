package sdk.kitso.feedbackmaster;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Globals {
    public static final int CURRENT_USER_ID = 0;
    public static Executor executor = Executors.newSingleThreadExecutor();
    public static Executor exec = Executors.newFixedThreadPool(1);
    public static final String RATING = "rating";
    public static final String OPEN_ENDED = "open-ended";
    public static final String TRUE_OR_FALSE = "true-or-false";
    public static final String MULTI_SELECT = "multiple-select";
    public static final String SINGLE_SELECT = "single-select";
    public static final int SCALE = 5;
}
