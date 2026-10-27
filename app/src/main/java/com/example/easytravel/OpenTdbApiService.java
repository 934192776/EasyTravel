package com.example.easytravel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OpenTdbApiService {
    @GET("api.php?amount=10")
    Call<TriviaResponse> getTriviaQuestions();
}