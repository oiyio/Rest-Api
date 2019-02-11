package com.example.tutorial1_1get_request;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tutorial1_1get_request.adapter.GitHubRepoAdapter;
import com.example.tutorial1_1get_request.api.GitHubClient;
import com.example.tutorial1_1get_request.api.GitHubRepo;
import com.example.tutorial1_2basics.R;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This example demonstrates how to retrieve data(repos for instance) in {@link GitHubRepo}
 * or raw({@link ResponseBody} -> String) format
 * from GitHub using Retrofit and link provided for a particular owner
 *
 * <p>
 * Endpoint: https://api.github.com/users/{query}/repos
 * </p>
 */
public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https:/api.github.com/";
    private String query = "SmartToolFactory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.pagination_list);

        // Create Retrofit instance with builder pattern
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Request request;

        // Create GitHubClient
        GitHubClient gitHubClient = retrofit.create(GitHubClient.class);

        // Send a request to retrieve GithubRepo object and assign this objects to listView
        requestRepos(listView, gitHubClient);


        // OPTIONAL query that returns ResponseBody
        // requestReposRaw(gitHubClient);

    }

    private void requestReposRaw(GitHubClient gitHubClient) {
        Call<ResponseBody> callRaw = gitHubClient.responseUsers(query);

        callRaw.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    // Get response message in String format
                    String rawResponse = response.body().string();
                    System.out.println("Response RAW: " + rawResponse);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.body().close();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void requestRepos(ListView listView, GitHubClient gitHubClient) {
        // Create call to execute network process
        Call<List<GitHubRepo>> call = gitHubClient.repoForUser(query);

        // Send request asynchronously and get response or error
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {

                // This is the Request we send to server
                Request request = response.raw().request();

                // Headers section of the Request
                System.out.println("REQUEST HEADERS: " + request.headers());

                System.out.println("************************");

                // Headers section of the Response
                System.out.println("RESPONSE HEADERS: " + response.headers());

                // Http status message
                System.out.println("RESPONSE MESSAGE: " + response.message());

                // Response Body returns list of GitHubRepo objects
                System.out.println("RESPONSE BODY: " + response.body());

                List<GitHubRepo> repos = response.body();
                listView.setAdapter(new GitHubRepoAdapter(MainActivity.this, repos));


            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
