package com.fstyle.demoretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.fstyle.demoretrofit.services.api.ApiCallback;
import com.fstyle.demoretrofit.services.api.BaseException;
import com.fstyle.demoretrofit.services.response.SearchGitHubUsersResponse;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainApplication.getApiService()
                .searchGithubUsers(12, "abc").enqueue(new ApiCallback<SearchGitHubUsersResponse>() {
            @Override
            public void failure(BaseException baseException) {
                Log.e(TAG, "failure: ",baseException );
            }

            @Override
            public void success(SearchGitHubUsersResponse response) {
                Log.d(TAG, "success: "+response.getItems().size());
            }
        });
    }
}
