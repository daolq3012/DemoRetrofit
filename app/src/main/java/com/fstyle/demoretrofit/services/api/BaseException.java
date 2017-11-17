package com.fstyle.demoretrofit.services.api;

import android.support.annotation.Nullable;
import com.fstyle.demoretrofit.services.response.ErrorResponse;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import retrofit2.Response;

/**
 * Created by daolq on 11/17/17.
 */

public class BaseException extends RuntimeException {
    private final String type;
    @Nullable
    private Response response;
    @Nullable
    private ErrorResponse errorResponse;

    private BaseException(String type, Throwable cause) {
        super(cause.getMessage(), cause);
        this.type = type;
    }

    private BaseException(String type, @Nullable Response response) {
        this.type = type;
        this.response = response;
    }

    public BaseException(String type, @Nullable ErrorResponse response) {
        this.type = type;
        this.errorResponse = response;
    }

    public static BaseException toNetworkError(Throwable cause) {
        return new BaseException(Type.NETWORK, cause);
    }

    public static BaseException toHttpError(Response response) {
        return new BaseException(Type.HTTP, response);
    }

    public static BaseException toUnexpectedError(Throwable cause) {
        return new BaseException(Type.UNEXPECTED, cause);
    }

    public static BaseException toServerError(ErrorResponse response) {
        return new BaseException(Type.SERVER, response);
    }

    @Type
    public String getErrorType() {
        return type;
    }

    public String getMessage() {
        switch (type) {
            case Type.SERVER:
                if (errorResponse != null) {
                    return errorResponse.getMessage();
                }
                return "";
            case Type.NETWORK:
                return getNetworkErrorMessage(getCause());
            case Type.HTTP:
                if (response != null) {
                    return getHttpErrorMessage(response.code());
                }
                return "Error";
            default:
                return "Error";
        }
    }

    private String getNetworkErrorMessage(Throwable throwable) {
        if (throwable instanceof SocketTimeoutException) {
            return throwable.getMessage();
        }

        if (throwable instanceof UnknownHostException) {
            return throwable.getMessage();
        }

        if (throwable instanceof IOException) {
            return throwable.getMessage();
        }

        return throwable.getMessage();
    }

    private String getHttpErrorMessage(int httpCode) {
        if (httpCode >= 300 && httpCode <= 308) {
            // Redirection
            return "It was transferred to a different URL. I'm sorry for causing you trouble";
        }
        if (httpCode >= 400 && httpCode <= 451) {
            // Client error
            return "An error occurred on the application side. Please try again later!";
        }
        if (httpCode >= 500 && httpCode <= 511) {
            // Server error
            return "A server error occurred. Please try again later!";
        }

        // Unofficial error
        return "An error occurred. Please try again later!";
    }
}
