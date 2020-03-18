package com.esilv.fink.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TransactionService {
    @GET("get/?query=select%20*%20from%20transactions%20where")
    Call<TransactionResponse> search(@Query("additionnal") String id);
}
