package com.assignment.tataaig.app.network;

import androidx.annotation.NonNull;

import com.assignment.tataaig.BuildConfig;
import com.assignment.tataaig.constant.Constant;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private static final int TIMEOUT = 45;

    public static <T> T buildMainApiService(final Class<T> service) {
        return buildGeneric(service, Constant.BASE_URL);
    }

    private static <T> T buildGeneric(Class<T> service, String hostUrl) {
        return new Retrofit.Builder()
                .baseUrl(hostUrl)
                .client(getOkHttpClientInstance())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(service);
    }

    @NonNull
    private static OkHttpClient getOkHttpClientInstance() {
        return new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(getOkHttpLoggingInterceptor())
                .addInterceptor(getOkHttpInterceptor())
                .build();
    }

    private static Interceptor getOkHttpInterceptor() {
        return new Interceptor() {
            @NotNull
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl httpUrl = original.url();
                HttpUrl newHttpUrl = httpUrl.newBuilder().addQueryParameter("api_key", Constant.API_KEY).build();
                Request.Builder requestBuilder = original.newBuilder().url(newHttpUrl);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

    private static long getTimeOutFor(String key) {
        return TIMEOUT;
    }

    private static HttpLoggingInterceptor getOkHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return httpLoggingInterceptor;
    }
}
