package com.example.externaltutorial_codinginflow_1_simple_get_request;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
public interface JsonPlaceHolderApi {
    @GET("posts")
    Call<List<Post>> getPosts();
}