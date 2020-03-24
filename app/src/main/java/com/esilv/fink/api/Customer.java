package com.esilv.fink.api;

import java.io.Serializable;
import java.util.Random;

public class Customer implements Serializable {
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
    private final String imageURL;

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

    public String getImageUrl() {
        return imageURL;
    }

    public  Customer() {
        Random random = new Random();
        int nombreAleatoire = 0 + (int)(Math.random() * ((500 - 0) + 1));
        this.imageURL= "https://i.picsum.photos/id/"+nombreAleatoire+ "/200/200.jpg";
    }

    @Override
    public String toString(){
        String str = "Id : " + CUSTOMERID + ", Surname : " + SURNAME;
        return str;
    }
}