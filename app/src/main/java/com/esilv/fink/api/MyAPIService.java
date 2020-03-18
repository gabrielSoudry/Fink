package com.esilv.fink.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MyAPIService {
    @GET("/get/query=select%20*%20from%20user_table%20where%20CUSTOMERID%20>=%2016000000")
    Call<ApiGetResponse> search();
}
