package com.fstyle.demoretrofit;

import android.app.Application;
import com.fstyle.demoretrofit.services.api.ApiService;
import com.fstyle.demoretrofit.services.api.ServiceHelper;

/**
 * Created by daolq on 11/17/17.
 */

public class MainApplication extends Application {

    private static ApiService mApiService;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mApiService == null){
            mApiService = ServiceHelper.createApiService(this);
        }
    }

    public static ApiService getApiService() {
        return mApiService;
    }
}
