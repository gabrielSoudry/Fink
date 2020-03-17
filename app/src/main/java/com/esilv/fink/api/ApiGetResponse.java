package com.esilv.fink.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class    ApiGetResponse {
    private List<Customer> data;

    public List<Customer> getCustomers() {
        return data;
    }
}
