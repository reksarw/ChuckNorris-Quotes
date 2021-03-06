package com.example.lbd.retrofit.services;

import com.example.lbd.retrofit.models.ChuckNorrisCategoryQuote;
import com.example.lbd.retrofit.models.ChuckNorrisQuotes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by LBD on 16/11/2017.
 */

public interface ChuckService {

    @GET("jokes/random")
    Call<ChuckNorrisQuotes> getQuote();

    @GET("jokes/random")
    Call<ChuckNorrisCategoryQuote> getCategoryQuote(
        @Query("category") String kategori
    );

    @GET("jokes/categories")
    Call<List<String>> getCategories();
}
