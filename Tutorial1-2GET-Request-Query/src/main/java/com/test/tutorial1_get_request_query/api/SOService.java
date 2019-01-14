package com.test.tutorial1_get_request_query.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SOService {

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<SOAnswersResponse> getAnswers();

    /**
     * Get answer with specific tag
     *
     * @param tags of the questions
     * @return questions that only tagged with tag
     *
     * <p> Endpoint: https://api.stackexchange.com/2.2/answers?order=desc&sort=activity&site=stackoverflow
     * &tagged=tags</p>
     */
    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<SOAnswersResponse> getAnswers(@Query("tagged") String tags);
}