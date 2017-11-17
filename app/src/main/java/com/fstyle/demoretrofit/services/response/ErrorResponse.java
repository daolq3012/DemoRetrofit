package com.fstyle.demoretrofit.services.response;

import com.google.gson.annotations.Expose;

/**
 * Created by daolq on 11/17/17.
 */

public class ErrorResponse {
    @Expose
    private String documentationUrl;
    @Expose
    private String message;

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public String getMessage() {
        return message;
    }
}
