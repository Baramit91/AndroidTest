package com.baramit.com.androidtest.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCalls {

    @GET("raw/8wJzytQX")
    Call<List<Integer>> getNumbers();

}
