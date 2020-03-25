package com.esilv.fink.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {
    @GET("/bestProduct/")
    Call<ApiGetProductResponse> search(@Query("id") Integer id);
}
