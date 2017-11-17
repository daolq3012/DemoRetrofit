package com.fstyle.demoretrofit.services.api;

import android.text.TextUtils;
import android.util.Log;
import com.fstyle.demoretrofit.services.response.ErrorResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.HttpURLConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by daolq on 11/17/17.
 */

public abstract class ApiCallback<T> implements Callback<T> {

    public abstract void failure(BaseException baseException);

    public abstract void success(T response);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        int code = response.code();
        if (code >= HttpURLConnection.HTTP_OK && code < HttpURLConnection.HTTP_MULT_CHOICE) {
            success(response.body());
        } else {
            if (response.errorBody() != null) {
                try {
                    ErrorResponse errorResponse =
                            new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                    if (errorResponse != null && !TextUtils.isEmpty(errorResponse.getMessage())) {
                        failure(BaseException.toServerError(errorResponse));
                    } else {
                        failure(BaseException.toHttpError(response));
                    }
                } catch (IOException e) {
                    failure(BaseException.toHttpError(response));
                }
            } else {
                failure(BaseException.toHttpError(response));
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        failure(convertToBaseException(throwable));
    }

    private BaseException convertToBaseException(Throwable throwable) {
        if (throwable instanceof IOException) {
            return BaseException.toNetworkError(throwable);
        }

        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            Response response = httpException.response();
            return BaseException.toHttpError(response);
        }

        return BaseException.toUnexpectedError(throwable);
    }
}
