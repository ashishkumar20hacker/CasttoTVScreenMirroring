package com.ide.codekit.casttotv.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface PexelsApiService {
    @GET("search")
    Call<PexelsResponse> searchImages(
            @Header("Authorization") String apiKey,
            @Query("query") String query,
            @Query("per_page") int perPage,
            @Query("page") int page // Add this parameter
    );
}
