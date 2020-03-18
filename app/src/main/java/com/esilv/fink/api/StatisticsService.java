package com.esilv.fink.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StatisticsService {
    @GET("get/?query=select%20*%20from%20statistics%20where")
    Call<StatisticResponse> search(@Query("additionnal") String additionnal);
}