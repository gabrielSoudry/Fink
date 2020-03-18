package com.esilv.fink;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    private ArrayList<String> ImageUrls = new ArrayList<>();
    private ArrayList<String> ImageNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        initImages();
    }

   private void initImages(){
       ImageUrls.add("http://drive.google.com/uc?export=view&id=1g06OsyVLnpyuPmIBn9ZeTehAgVClZ0Co");
       ImageNames.add("Summer");

        ImageUrls.add("http://drive.google.com/uc?export=view&id=13jB7kigggDsRmsDQlfhAk3Ng-jGtrYZL");
        ImageNames.add("Vintage");

       ImageUrls.add("http://drive.google.com/uc?export=view&id=1CjLi77W6jISnQOTSXdT2Mhp1gGeNNiYN");
       ImageNames.add("Spring");

       ImageUrls.add("http://drive.google.com/uc?export=view&id=1y86o62_kTCwqkrEJxXwmkeZq-6D8ud36");
       ImageNames.add("Trendy");

       ImageUrls.add("http://drive.google.com/uc?export=view&id=1oVl9LQwBmL6RgiQULjHA9kjzvxuH9wYC");
       ImageNames.add("Dutch");

       initRecycler();
   }
   private void initRecycler(){


           RecyclerView recyclerView = findViewById(R.id.recyclerview);

           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
           recyclerView.setLayoutManager(linearLayoutManager);

           RecyclerViewAdapterColor adapter = new RecyclerViewAdapterColor(ImageUrls, ImageNames, this);
           recyclerView.setAdapter(adapter);


   }
}
