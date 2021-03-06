package com.esilv.fink.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esilv.fink.Adapter.MyRecyclerViewAdapter;
import com.esilv.fink.R;
import com.esilv.fink.api.ApiGetResponse;
import com.esilv.fink.api.Customer;
import com.esilv.fink.api.MyAPIService;
import com.squareup.haha.perflib.Main;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    private MyRecyclerViewAdapter adapter;
    private List<Customer> customers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://130.61.95.117:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyAPIService service = retrofit.create(MyAPIService.class);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("database loading no jutsu....");
        progressDialog.setTitle("Trying to fetch from database");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // show it
        //progressDialog.show();
        service.search().enqueue(new Callback<ApiGetResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiGetResponse> call, @NonNull Response<ApiGetResponse> response) {
                Log.d(TAG, "onResponse GetCustomers");
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
        Intent intents = new Intent(LoginActivity.this, MainActivity.class);
        intents.putExtra("customerLogin", adapter.getItem(position));
        startActivity(intents);
    }
}
