package com.esilv.fink.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.esilv.fink.R;
import com.esilv.fink.api.Customer;

public class MainActivity extends AppCompatActivity {
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customer = (Customer)getIntent().getSerializableExtra("customerLogin");

        final Button mainButton = findViewById(R.id.finkMainButton);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(MainActivity.this, GetProductActivity.class);
                intents.putExtra("customerLogin", customer);
                startActivity(intents);
            }
        });

        final Button dashboardButton = findViewById(R.id.dashboardButton);
        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(MainActivity.this, Dashboard.class);
                intents.putExtra("customerLogin", customer);
                startActivity(intents);
            }
        });

        final Button transactionsButton = findViewById(R.id.transactionsButton);
        transactionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(MainActivity.this, TransactionActivity.class);
                intents.putExtra("customerLogin", customer);
                startActivity(intents);
            }
        });
    }
}
