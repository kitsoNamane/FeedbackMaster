package sdk.feedbackmaster.repository.remote_datasource.factories;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sdk.feedbackmaster.repository.remote_datasource.FeedbackMasterSurveyApiService;
import sdk.feedbackmaster.repository.remote_datasource.http_interceptors.ConnectivityInterceptor;
import sdk.feedbackmaster.repository.remote_datasource.http_interceptors.DeviceUUIDInterceptor;

public class FeedbackMasterSurveyServiceFactory {
    public static FeedbackMasterSurveyApiService getService(String device_uuid, Context context) {
        DeviceUUIDInterceptor deviceUUIDInterceptor = new DeviceUUIDInterceptor(device_uuid);
        ConnectivityInterceptor connectivityInterceptor = new ConnectivityInterceptor(context);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(deviceUUIDInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl("http://192.168.1.78:8000/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(FeedbackMasterSurveyApiService.class);
    }
}

