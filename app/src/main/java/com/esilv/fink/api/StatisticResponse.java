package com.esilv.fink.api;

import java.util.List;

public class StatisticResponse {
    private List<Stat> data;

    public Stat getStatistics() {
        return data.get(0);
    }
}
