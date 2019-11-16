package com.abdelrahman.footballleague.api;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Response;

import static com.abdelrahman.footballleague.api.Status.ERROR;
import static com.abdelrahman.footballleague.api.Status.Failure;
import static com.abdelrahman.footballleague.api.Status.SUCCESS;

/**
 * @author Abdel-Rahman El-Shikh on 16-Nov-19.
 */
public abstract class RequestHanlder<T> {
    private MutableLiveData<ApiResponse<T>> apiResponseMutableLiveData;

    protected RequestHanlder() {
        apiResponseMutableLiveData = new MutableLiveData<>();
    }

    public abstract Call<T> makeRequest();

    public void doRequest() {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        makeRequest().enqueue(new ApiCallback<T>() {
            @Override
            void handlerResponseData(T body) {
                //response is successful
                apiResponse.setData(body);
                apiResponse.setStatus(SUCCESS);
                apiResponseMutableLiveData.setValue(apiResponse);
            }

            @Override
            void handleException(Exception t) {
                //Failure happened
                apiResponse.setApiException(t);
                apiResponse.setStatus(Failure);
                apiResponseMutableLiveData.setValue(apiResponse);
            }

            @Override
            void handleError(Response<T> response) {
                //response isn't successful
                apiResponse.setApiError(RetrofitBuilder.convertErrors(response.errorBody()));
                apiResponse.setStatus(ERROR);
                apiResponseMutableLiveData.setValue(apiResponse);
            }
        });
    }

    public MutableLiveData<ApiResponse<T>> getApiResponseLiveData() {
        return apiResponseMutableLiveData;
    }

}
