package com.esilv.fink.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MyAPIService {
    @GET("get/select%20*%20from%20user_table")
    Call<ApiGetResponse> search();
}
