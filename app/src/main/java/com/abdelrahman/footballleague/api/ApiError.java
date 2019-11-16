package com.abdelrahman.footballleague.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Abdel-Rahman El-Shikh on 16-Nov-19.
 */
public class ApiError {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
