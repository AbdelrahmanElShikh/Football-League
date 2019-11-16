package com.abdelrahman.footballleague.api;

import android.util.Log;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Abdel-Rahman El-Shikh on 16-Nov-19.
 */
public abstract class ApiCallback<T> implements Callback<T> {
    private static final String TAG = "ApiCallback";

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (response.isSuccessful()) {
            handlerResponseData(response.body());
        } else {
            handleError(response);
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        if (t instanceof Exception) {
            handleException((Exception) t);
        } else {
            Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
        }
    }

    abstract void handleException(Exception t);

    abstract void handleError(Response<T> response);

    abstract void handlerResponseData(T body);
}
