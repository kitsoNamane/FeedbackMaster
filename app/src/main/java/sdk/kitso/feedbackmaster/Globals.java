package sdk.kitso.feedbackmaster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Globals {
    public static final int CURRENT_USER_ID = 0;
    public static final int FIRST_PAGE = 1;
    public static Executor executor = Executors.newSingleThreadExecutor();
    public static Executor exec = Executors.newFixedThreadPool(1);
    public static final String RATING = "rating";
    public static final String OPEN_ENDED = "open-ended";
    public static final String TRUE_OR_FALSE = "true-or-false";
    public static final String MULTI_SELECT = "multiple-select";
    public static final String SINGLE_SELECT = "single-select";
    public static final int SCALE = 5;

    /** Response Error Codes Key:
     *  2208 -> invalid package
     *  2209 -> unable to save your feedback
     *  2212 -> your feedback recently receive, try again in {12}{days}
     *  1202 -> access denied
     *  1201 -> access denied
     */
    public static final List<Integer> RESPONSE_ERROR_CODES = new ArrayList<Integer>(Arrays.asList(
           2208, 2209, 2212, 1202
    ));
}
