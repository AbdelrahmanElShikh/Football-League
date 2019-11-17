package com.abdelrahman.footballleague.api;

import android.util.Log;

import com.abdelrahman.footballleague.MyApplication;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Abdel-Rahman El-Shikh on 15-Nov-19.
 */
public class RetrofitBuilder {
    private static final String BASE_URL = "https://api.football-data.org/v2/";

    // this olHttpClient is for adding our custom Http headers
    private final static OkHttpClient client = buildClient();

    private final static Retrofit retrofit = buildRetrofit();

    private static OkHttpClient buildClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Request.Builder mBuilder = request.newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Accept", "application/json")
                            .addHeader("Connection", "close");
                    request = mBuilder.build();
                    return chain.proceed(request);
                });
        return builder.build();
    }

    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }


    public static <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }

    public static ApiError convertErrors(ResponseBody response) {
        Converter<ResponseBody, ApiError> converter = retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);
        ApiError apiError = null;
        try {
            apiError = converter.convert(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiError;
    }
}
