package com.example.lbd.retrofit.services;

import com.example.lbd.retrofit.models.ChuckNorrisQuotes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by LBD on 16/11/2017.
 */

public interface ChuckService {

    @GET("jokes/random")
    Call<ChuckNorrisQuotes> getQuote();

    @GET("jokes/random")
    Call<ChuckNorrisQuotes> getRandomCategory(
        @Query("categories") String kategori
    );
}
