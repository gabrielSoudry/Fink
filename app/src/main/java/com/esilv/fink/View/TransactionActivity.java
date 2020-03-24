package com.esilv.fink.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esilv.fink.Adapter.TransactionAdapter;
import com.esilv.fink.R;
import com.esilv.fink.api.Customer;
import com.esilv.fink.api.InnerTransaction;
import com.esilv.fink.api.Transaction;
import com.esilv.fink.api.TransactionResponse;
import com.esilv.fink.api.TransactionService;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class TransactionActivity extends AppCompatActivity {
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://130.61.95.117:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private Integer id;

    private List<InnerTransaction> transactionModel;
    private RecyclerView recyclerView;
    private Customer customer;
    private List<Transaction> transactions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);


        customer = (Customer)getIntent().getSerializableExtra("customerLogin");
        transactionModel = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_transactions);

        final TransactionAdapter adapter = new TransactionAdapter(this, transactionModel);
        SearchView searchView = findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return true;
            }
        });

        new DrawerBuilder().withActivity(this).build();
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("All my transactions");
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(3).withName("");

        Toolbar myToolbar = findViewById(R.id.tool);
        setSupportActionBar(myToolbar);
        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(myToolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        item3
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (position == 0) {
                            Intent intent = new Intent(TransactionActivity.this, Dashboard.class);
                            Customer customerSelected = (Customer) getIntent().getSerializableExtra("customerLogin");
                            intent.putExtra("customerLogin", customerSelected); //Put your id to your next Intent
                            startActivity(intent);
                        }
                        if (position == 3) {
                        }
                        return true;
                    }
                })
                .build();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        id = customer.getCUSTOMERID();

        TransactionService service = retrofit.create(TransactionService.class);

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
                        InnerTransaction temp1 = new InnerTransaction();
                        temp1.setMonth(transactions.get(i).getVALUEString());
                        temp1.setValue(transactions.get(i).getVALUEString());
                        temp1.setYear(transactions.get(i).getYEAR().toString());
                        temp1.setTypeDepense(transactions.get(i).getTRANSACTIONTYPEString());
                        transactionModel.add(temp1);
                    }
                    adapter.notifyItemRangeChanged(0, transactionModel.size());
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
