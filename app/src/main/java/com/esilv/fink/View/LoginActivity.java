package com.esilv.fink.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.esilv.fink.Adapter.MyRecyclerViewAdapter;
import com.esilv.fink.R;
import com.esilv.fink.api.ApiGetResponse;
import com.esilv.fink.api.Customer;
import com.esilv.fink.api.MyAPIService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    private MyAPIService service;
    List<Customer> customers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://130.61.95.117:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(MyAPIService.class);
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Its loading....");
        progressDialog.setTitle("ProgressDialog bar example");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // show it
        progressDialog.show();
        service.search().enqueue(new Callback<ApiGetResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiGetResponse> call, @NonNull Response<ApiGetResponse> response) {
                Log.d(TAG, "onResponse ===========");
                if (response.isSuccessful()) {
                    ApiGetResponse apiResponse = response.body();
                    customers = apiResponse.getCustomers();
                    progressDialog.dismiss();
                    RecyclerView recyclerView = findViewById(R.id.rvCustomer);
                    recyclerView.setLayoutManager(new LinearLayoutManager(LoginActivity.this));
                    adapter = new MyRecyclerViewAdapter(LoginActivity.this, customers);
                    adapter.setClickListener(LoginActivity.this);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<ApiGetResponse> call, Throwable t) {
                Log.e(TAG, "onFailure", t);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        Intent intents = new Intent(LoginActivity.this, Dashboard.class);
        intents.putExtra("customerLogin", adapter.getItem(position)); //Put your id to your next Intent
        startActivity(intents);
    }



}
