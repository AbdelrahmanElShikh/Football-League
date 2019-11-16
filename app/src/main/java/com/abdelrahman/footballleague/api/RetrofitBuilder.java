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
    private static final String TAG = "RetrofitBuilder";
    private static final String BASE_URL = "https://api.football-data.org/v2/";
    private static final long cacheSize = 5 * 1024 * 1024; // 5 MB
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";
    private static final String HEADER_PRAGMA = "Pragma";

    // this olHttpClient is for adding our custom Http headers
    private final static OkHttpClient client = buildClient();

    private final static Retrofit retrofit = buildRetrofit();

    private static OkHttpClient buildClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache())
                //this interceptor called if the network is ON or OFF.
                .addInterceptor(httpLoggingInterceptor())
                //this interceptor called if the network is ON (ONLY)
                .addNetworkInterceptor(networkInterceptor())
                .addInterceptor(offlineInterceptor());
        return builder.build();
    }

    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    private static Cache cache() {
        return new Cache(new File(MyApplication.getInstance().getCacheDir(), "cacheDir"), cacheSize);
    }

    private static HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(message -> Log.d(TAG, "log: http log: " + message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }


    private static Interceptor networkInterceptor() {
        return chain -> {
            String apiKey = "ae449e21e9524d928dadbdc85df4022c=";
            Log.d(TAG, "network interceptor: called.");

            Response response = chain.proceed(chain.request());

            //when we are able to use the cache if the network is available
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(5, TimeUnit.SECONDS)
                    .build();

            return response.newBuilder()
                    //removing the pragma to be able to use cache
                    .removeHeader(HEADER_PRAGMA)
                    //removing the server cache control
                    .removeHeader(HEADER_CACHE_CONTROL)
                    //applying our own cache control
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    //adding our api key
                    .addHeader("X-Auth-Token", apiKey)
                    .build();
        };
    }

    private static Interceptor offlineInterceptor() {
        return chain -> {
            Log.d(TAG, "offline interceptor: called.");
            Request request = chain.request();

            // prevent caching when network is on. For that we use the "networkInterceptor"
            if (!MyApplication.hasNetwork()) {
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();

                request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build();
            }

            return chain.proceed(request);
        };
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
