package com.test.tutorial1_3_request_querymapandurl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.test.tutorial1_3_request_querymapandurl.api.PostApi;
import com.test.tutorial1_3_request_querymapandurl.api.RetrofitClient;
import com.test.tutorial1_3_request_querymapandurl.model.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private PostApi mPostApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = RetrofitClient.getClient(PostApi.BASE_URL);

        mPostApi = retrofit.create(PostApi.class);

        getPosts();

    }


    private void getPosts() {

        final TextView tv = findViewById(R.id.tvResult);

        // This one uses @Query
        //  Call<List<Post>> call = mPostApi.getPosts(new Integer[]{1}, "id", "desc");

        // This one uses @QueryMap
        Map<String, String> params = new HashMap<>();
        params.put("userId", "1");
        params.put("_sort", "id");
        params.put("_order", "desc");
        Call<List<Post>> call = mPostApi.getPosts(params);


        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> posts = response.body();

                    StringBuilder sb = new StringBuilder();

                    for (Post post : posts) {
                        sb.append("id: " + post.getId() + "\n")
                                .append("userId: " + post.getUserId() + "\n")
                                .append("title: " + post.getTitle() + "\n")
                                .append("text: " + post.getBody() + "\n");
                    }


                    tv.setText(sb.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });


    }
}
