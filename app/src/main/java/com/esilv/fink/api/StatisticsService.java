package com.esilv.fink.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StatisticsService {
    @GET("get/select%20*%20from%20statistics%20where%20CUSTOMERID%20=%2015634602")
    Call<StatisticResponse> search(@Query("CUSTOMERID") String id);
}
