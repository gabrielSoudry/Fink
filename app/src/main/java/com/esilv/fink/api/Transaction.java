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

    public String getTRANSACTIONTYPEString() {
        System.out.println("TRANSACTION");
        System.out.println(TRANSACTIONTYPE.split("\\.")[0]);
        System.out.println(TRANSACTIONTYPE.split("\\.")[1]);
        return TRANSACTIONTYPE.split("\\.")[1];
    }

    public Double getVALUE() {
        return VALUE;
    }

    public String getVALUEString() {
        if (VALUE.toString().split("\\.")[1].length() == 1) {
            return VALUE.toString() + "0 €";
        } else {
            return VALUE.toString() + " €";
        }
    }

    public Integer getMONTH() {
        return MONTH;
    }

    public String getMONTHString() {
        if (MONTH < 10){
            return "0" + MONTH.toString();
        } else {
            return MONTH.toString();
        }
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
