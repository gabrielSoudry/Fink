package com.esilv.fink.api;

public class Product {
    private String bank;
    private Integer duration;
    private Integer loanvalue;
    private Double total;
    private Double mensualite;

    public String getBank() {
        return bank;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getDurationText() {
        return duration.toString() + " Years";
    }

    public Integer getLoanvalue() {
        return loanvalue;
    }

    public String getLoanvalueText() {
        return loanvalue.toString().replaceAll("(\\d)(?=(\\d{3})+$)", "$1 ") + " €";
    }

    public Double getTotal() {
        return total;
    }

    public String getTotalText() {
        return total.toString().split("\\.")[0].replaceAll("(\\d)(?=(\\d{3})+$)", "$1 ") + " €";
    }

    public Double getMensualite() {
        return mensualite;
    }

    public String getMensualiteText() {
        return mensualite.toString().replaceAll("(\\d)(?=(\\d{3})+$)", "$1 ") + " €/month";
    }

    @Override
    public String toString(){
        return bank + " " + duration.toString() + " " + total.toString();
    }
}
