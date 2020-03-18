package com.esilv.fink.api;

public class Statistic {
    private Integer CUSTOMERID;
    private Double ALIMENTATION;
    private Double LOISIRS;
    private Double MULTIMEDIA;
    private Double RESTAURANTS;
    private Double RETRAITS;
    private Double SANTE;
    private Double SHOPPING;
    private Double AUTRES;
    private Double LOYER;
    private Double ELECTRICITE;
    private Double INTERNET;

    public Integer getCUSTOMERID() {
        return CUSTOMERID;
    }

    public Double getALIMENTATION() {
        return ALIMENTATION;
    }

    public Double getLOISIRS() {
        return LOISIRS;
    }

    public Double getMULTIMEDIA() {
        return MULTIMEDIA;
    }

    public Double getRESTAURANTS() {
        return RESTAURANTS;
    }

    public Double getRETRAITS() {
        return RETRAITS;
    }

    public Double getSANTE() {
        return SANTE;
    }

    public Double getSHOPPING() {
        return SHOPPING;
    }

    public Double getAUTRES() {
        return AUTRES;
    }

    public Double getLOYER() {
        return LOYER;
    }

    public Double getELECTRICITE() {
        return ELECTRICITE;
    }

    public Double getINTERNET() {
        return INTERNET;
    }

    @Override
    public String toString(){
        return "Id : " +
                CUSTOMERID + "\n" +
                ALIMENTATION+ "\n" +
                LOISIRS+ "\n" +
                MULTIMEDIA+ "\n" +
                RESTAURANTS+ "\n" +
                RETRAITS+ "\n" +
                SANTE+ "\n" +
                SHOPPING+ "\n" +
                AUTRES+ "\n" +
                LOYER+ "\n" +
                ELECTRICITE+ "\n" +
                INTERNET;
    }
}
