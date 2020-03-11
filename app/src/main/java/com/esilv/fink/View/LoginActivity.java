package com.esilv.fink.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.esilv.fink.Adapter.MyRecyclerViewAdapter;
import com.esilv.fink.Model.User;
import com.esilv.fink.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // data to populate the RecyclerView with
        ArrayList<User> users = new ArrayList<>();
        User gabriel = new User();
        User Michel = new User();
        User Michel2 = new User();
        User Michel3 = new User();
        gabriel.setName("Gabriel");
        Michel.setName("Bill");
        users.add(gabriel);
        users.add(Michel);
        users.add(Michel2);
        users.add(Michel3);


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, users);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, MainActivity.class));
    }
}
