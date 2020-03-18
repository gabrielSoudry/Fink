package com.esilv.fink.api;

import java.util.List;

public class StatisticResponse {
    private List<Statistic> data;

    public Statistic getStatistics() {
        return data.get(0);
    }
}
