package com.esilv.fink.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.esilv.fink.Adapter.MyRecyclerViewAdapter;
import com.esilv.fink.Adapter.TransactionAdapter;
import com.esilv.fink.R;
import com.esilv.fink.api.ApiGetResponse;
import com.esilv.fink.api.Customer;
import com.esilv.fink.api.Transaction;
import com.esilv.fink.api.TransactionResponse;
import com.esilv.fink.api.TransactionService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class TransactionActivity extends AppCompatActivity {
    Integer id;
    RecyclerView recyclerView;
    List<String> TypeDepense, Value, Month, Year;
    Customer customer;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://130.61.95.117:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private TransactionService service;
    List<Transaction> transactions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        customer = (Customer)getIntent().getSerializableExtra("customerLogin");

        TypeDepense = new ArrayList<String>();
        Value = new ArrayList<String>();
        Month = new ArrayList<String>();
        Year = new ArrayList<String>();

        recyclerView = findViewById(R.id.recycler_view_transactions);

        final TransactionAdapter adapter = new TransactionAdapter(this, TypeDepense, Value, Month, Year);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        id = customer.getCUSTOMERID();

        service = retrofit.create(TransactionService.class);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(TransactionActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("database loading no jutsu....");
        progressDialog.setTitle("Trying to fetch from database");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        progressDialog.incrementProgressBy(10);
        service.search("customerid = " + id.toString()).enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(@NonNull Call<TransactionResponse> call, @NonNull Response<TransactionResponse> response) {
                progressDialog.incrementProgressBy(50);
                if (response.isSuccessful()) {
                    TransactionResponse transactionResponse = response.body();
                    transactions = transactionResponse.getTransactions();

                    String temp = "";
                    for (int i = 0; i < transactions.size(); i++){
                        TypeDepense.add(transactions.get(i).getTRANSACTIONTYPEString());
                        Value.add(transactions.get(i).getVALUEString());
                        Month.add(transactions.get(i).getMONTHString());
                        Year.add(transactions.get(i).getYEAR().toString());
                    }
                    adapter.notifyItemRangeChanged(0, TypeDepense.size());
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                Log.e(TAG, "onFailure", t);
                progressDialog.dismiss();
            }
        });
    }
}
