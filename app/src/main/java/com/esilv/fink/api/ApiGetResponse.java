package com.esilv.fink.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ApiGetResponse {
    private List<SingleCustomerList> data;

    public ArrayList<Customer> getCustomers() throws ParseException {
        ArrayList<Customer> users = new ArrayList<Customer>();
        for (int i = 0; i < data.size(); i++){
            users.add(data.get(i).getCustomer());
        }
        return users;
    }
}
