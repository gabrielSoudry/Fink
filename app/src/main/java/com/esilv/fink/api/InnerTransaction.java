package com.esilv.fink.api;

public class InnerTransaction {

    private String typeDepense;
    private String value;
    private String month;
    private String year;


    public InnerTransaction(String typeDepense, String value, String month, String year) {
        this.typeDepense = typeDepense;
        this.value = value;
        this.month = month;
        this.year = year;
    }

    public InnerTransaction() {

    }

    public String getTypeDepense() {
        return typeDepense;
    }

    public void setTypeDepense(String typeDepense) {
        this.typeDepense = typeDepense;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
