package com.esilv.fink.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class SingleCustomerList {
    private List<String> attributes;

    public Customer getCustomer() throws ParseException {
        Customer customer = new Customer(attributes);

        return customer;
    }
}

public class Customer{
    public Integer id;
    public String Surname;
    public Integer CreditScore;
    public String Country;
    public String Gender;
    public Date Birthdate;
    public Integer Tenure;
    public Float Balance;
    public Integer NbOfProducts;
    public Boolean HasCreditCard;
    public Boolean IsActiveMember;
    public Float EstimatedSalary;
    public Boolean Exited;

    public Customer(List<String> attributes) throws ParseException {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-dd-MM");

        this.id = Integer.parseInt(attributes.get(0));
        this.Surname = attributes.get(1);
        this.CreditScore = Integer.parseInt(attributes.get(2));
        this.Country = attributes.get(3);
        this.Gender = attributes.get(4);
        this.Birthdate = formater.parse(attributes.get(0).split("T")[5]);
        this.Tenure = Integer.parseInt(attributes.get(6));
        this.Balance = Float.parseFloat(attributes.get(7));
        this.NbOfProducts = Integer.parseInt(attributes.get(8));
        this.HasCreditCard = attributes.get(9) == "Y" ? Boolean.TRUE : Boolean.FALSE;
        this.IsActiveMember = attributes.get(10) == "Y" ? Boolean.TRUE : Boolean.FALSE;;
        this.EstimatedSalary = Float.parseFloat(attributes.get(11));
        this.Exited = attributes.get(12) == "Y" ? Boolean.TRUE : Boolean.FALSE;;
    }

    @Override
    public String toString(){
        String str = "Id : " + id + ", Surname : " + Surname;
        return str;
    }
}