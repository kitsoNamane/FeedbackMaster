package sdk.kitso.feedbackmaster.repository;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FeedbackMasterSurveyApi {
    public static FeedbackMasterSurveyApiService getService(String device_uuid, Context context) {
        DeviceUUIDInterceptor deviceUUIDInterceptor = new DeviceUUIDInterceptor(device_uuid);
        ConnectivityInterceptor connectivityInterceptor = new ConnectivityInterceptor(context);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(deviceUUIDInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl("http://localhost:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(FeedbackMasterSurveyApiService.class);
    }
}
