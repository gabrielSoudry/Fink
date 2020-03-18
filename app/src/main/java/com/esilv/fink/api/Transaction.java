package com.esilv.fink.api;

public class Transaction {
    private Integer TRANSACTIONID;
    private Integer CUSTOMERID;
    private String TRANSACTIONTYPE;
    private Double VALUE;
    private Integer MONTH;
    private Integer YEAR;

    public Integer getTRANSACTIONID() {
        return TRANSACTIONID;
    }

    public Integer getCUSTOMERID() {
        return CUSTOMERID;
    }

    public String getTRANSACTIONTYPE() {
        return TRANSACTIONTYPE;
    }

    public Double getVALUE() {
        return VALUE;
    }

    public Integer getMONTH() {
        return MONTH;
    }

    public Integer getYEAR() {
        return YEAR;
    }

    public String ToString(){
        String str = "Type : ";
        str += TRANSACTIONTYPE;
        str += "\nValue : " + VALUE;

        return str;
    }
}
