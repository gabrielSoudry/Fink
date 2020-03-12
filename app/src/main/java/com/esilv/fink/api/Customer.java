package com.esilv.fink.api;

public class Customer{
    private Integer CUSTOMERID;
    private String SURNAME;
    private Integer CREDITSCORE;
    private String COUNTRY;
    private String GENDER;
    private String BIRTHDATE;
    private Integer TENURE;
    private Float BALANCE;
    private Integer NBOFPRODUCTS;
    private Boolean HASCREDITCARD;
    private Boolean ISACTIVEMEMBER;
    private Float ESTIMATEDSALARY;
    private Boolean EXITED;

    public Integer getCUSTOMERID() {
        return CUSTOMERID;
    }

    public String getSURNAME() {
        return SURNAME;
    }

    public Integer getCREDITSCORE() {
        return CREDITSCORE;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }

    public String getGENDER() {
        return GENDER;
    }

    public String getBIRTHDATE() {
        return BIRTHDATE;
    }

    public Integer getTENURE() {
        return TENURE;
    }

    public Float getBALANCE() {
        return BALANCE;
    }

    public Integer getNBOFPRODUCTS() {
        return NBOFPRODUCTS;
    }

    public Boolean getHASCREDITCARD() {
        return HASCREDITCARD;
    }

    public Boolean getISACTIVEMEMBER() {
        return ISACTIVEMEMBER;
    }

    public Float getESTIMATEDSALARY() {
        return ESTIMATEDSALARY;
    }

    public Boolean getEXITED() {
        return EXITED;
    }

    @Override
    public String toString(){
        String str = "Id : " + CUSTOMERID + ", Surname : " + SURNAME;
        return str;
    }
}