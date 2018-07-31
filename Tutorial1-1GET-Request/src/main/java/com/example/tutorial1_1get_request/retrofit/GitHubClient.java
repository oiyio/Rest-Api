package com.example.tutorial1_1get_request.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubClient {

    @GET("users/{user}/repos")
    Call<List<GitHubRepo>> repoForUser(@Path("user") String user);

}
