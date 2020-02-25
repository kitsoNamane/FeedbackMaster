package sdk.feedbackmaster.repository.remote_datasource.http_interceptors;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DeviceUUIDInterceptor implements Interceptor {
    private String DEVICE_UUID;
    public DeviceUUIDInterceptor(String device_uuid) {
        this.DEVICE_UUID = device_uuid;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("device", DEVICE_UUID)
                .build();

        Request request = chain.request()
                .newBuilder()
                .url(url)
                .build();
        return chain.proceed(request);
    }
}
