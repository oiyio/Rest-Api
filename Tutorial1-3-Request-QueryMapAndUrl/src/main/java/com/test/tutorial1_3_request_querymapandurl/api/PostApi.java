package com.test.tutorial1_3_request_querymapandurl.api;

import com.test.tutorial1_3_request_querymapandurl.model.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface PostApi {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("posts")
    Call<List<Post>> getPosts(@Query("userId") Integer[] userId,
                              @Query("_sort") String sort,
                              @Query("_order") String order);

    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> params);

}
