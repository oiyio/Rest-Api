package com.test.tutorial1_get_request_query;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.test.tutorial1_get_request_query.adapter.AnswersAdapter;
import com.test.tutorial1_get_request_query.api.ApiUtils;
import com.test.tutorial1_get_request_query.api.SOAnswersResponse;
import com.test.tutorial1_get_request_query.api.SOService;
import com.test.tutorial1_get_request_query.calladapter.ApiResponse;
import com.test.tutorial1_get_request_query.model.Item;

import java.util.ArrayList;

/**
 * This example demonstrates how to retrieve data in {@link SOAnswersResponse}
 * format
 * from Stackoverflow using Retrofit. Answer owners are returned from server and listed with a {@link RecyclerView}
 *
 * <p>
 * Endpoint: https://api.stackexchange.com/2.2/answers?order=desc&sort=activity&site=stackoverflow&tagged=tags
 * </p>
 **/
public class MainActivity extends AppCompatActivity {

    private SOService mService;
    private AnswersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO choose Call or LiveData returning Service api
        // mService = ApiUtils.getSOService();
        mService = ApiUtils.getLiveDataSOService();
        bindViews();

    }

    private void bindViews() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_answers);
        mAdapter = new AnswersAdapter(this, new ArrayList<Item>(0), new AnswersAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                Toast.makeText(MainActivity.this, "Post id is" + id, Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        //
        loadAnswers("android");
    }

    public void loadAnswers(String tag) {


//        mService.getAnswers(tag).enqueue(new Callback<SOAnswersResponse>() {
//
//            @Override
//            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {
//
//                if (response.isSuccessful()) {
//                    mAdapter.updateAnswers(response.body().getItems());
//                    Log.d("MainActivity", "posts loaded from API");
//                } else {
//                    int statusCode = response.code();
//                    // handle request errors depending on status code
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {
//                Log.d("MainActivity", "error loading from API");
//            }
//        });


        LiveData<ApiResponse<SOAnswersResponse>> soAnswersResponseLiveData = mService.getAnswersLive(tag);

        System.out.println("MainActivity() loadAnswers() soAnswersResponseLiveData: " + soAnswersResponseLiveData.getValue());

        soAnswersResponseLiveData.observe(this, new Observer<ApiResponse<SOAnswersResponse>>() {
            @Override
            public void onChanged(@Nullable ApiResponse<SOAnswersResponse> soAnswersResponseApiResponse) {
                if (soAnswersResponseApiResponse != null && soAnswersResponseApiResponse.body != null) {
                    System.out.println("MainActivity() loadAnswers() observe() THREAD:  " + Thread.currentThread().getName() );

                    mAdapter.updateAnswers(soAnswersResponseApiResponse.body.getItems());
                }
            }
        });
    }

}
