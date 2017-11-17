package com.fstyle.demoretrofit.services.api;

import com.fstyle.demoretrofit.services.response.SearchGitHubUsersResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by daolq on 11/17/17.
 */

public interface ApiService {

    @GET("/search/users")
    Call<SearchGitHubUsersResponse> searchGithubUsers(@Query("per_page") int limit, @Query("q") String searchTerm);
}
