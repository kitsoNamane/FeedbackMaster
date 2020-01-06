package sdk.kitso.feedbackmaster;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Globals {
    public static final int CURRENT_USER_ID = 0;
    public static Executor executor = Executors.newSingleThreadExecutor();
    public static final int RATING_STARS = 0;
    public static final int SHORT_ANSWER = 1;
    public static final int TRUE_FALSE = 2;
    public static final int MULTIPLE_CHOICE = 3;
    public static final int MULTIPLE_CHOICES = 4;
    public static final int SCALE = 5;
}
