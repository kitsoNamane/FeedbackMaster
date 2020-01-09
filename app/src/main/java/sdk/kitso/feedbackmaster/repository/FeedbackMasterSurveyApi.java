package sdk.kitso.feedbackmaster.repository;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sdk.kitso.feedbackmaster.db.DataItem;

public class FeedbackMasterSurveyApi {
    public static GsonConverterFactory customConverterFactory() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DataItem.class, new SurveyDeserializer());
        return GsonConverterFactory.create(gsonBuilder.create());
    }

    public static FeedbackMasterSurveyApiService getService(String device_uuid, Context context) {
        DeviceUUIDInterceptor deviceUUIDInterceptor = new DeviceUUIDInterceptor(device_uuid);
        ConnectivityInterceptor connectivityInterceptor = new ConnectivityInterceptor(context);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(deviceUUIDInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl("http://localhost:8000/api/v1/")
                .addConverterFactory(customConverterFactory())
                .build();
        return retrofit.create(FeedbackMasterSurveyApiService.class);
    }
}

