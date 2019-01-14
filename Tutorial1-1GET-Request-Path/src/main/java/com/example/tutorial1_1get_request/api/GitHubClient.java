package com.example.tutorial1_1get_request.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubClient {

    /**
     * Retrieves the user repo from GitHubServer
     * <p>
     * Endpoint: https://api.github.com/users/{user}/repos
     * </p>
     *
     * @param user name of the repo owner
     * @return list of {@link GitHubRepo} objects
     */
    @GET("users/{user}/repos")
    Call<List<GitHubRepo>> repoForUser(@Path("user") String user);

    // Raw Response in JSON format
    @GET("users/{user}/repos")
    Call<ResponseBody> responseUsers(@Path("user") String user);
}
