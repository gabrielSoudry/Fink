package com.esilv.fink;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.esilv.fink.View.Dashboard;
import com.hotmail.or_dvir.easysettings.pojos.BasicSettingsObject;
import com.hotmail.or_dvir.easysettings.pojos.CheckBoxSettingsObject;
import com.hotmail.or_dvir.easysettings.pojos.EasySettings;
import com.hotmail.or_dvir.easysettings.pojos.SettingsObject;
import com.hotmail.or_dvir.easysettings.pojos.SwitchSettingsObject;

import java.util.ArrayList;

public class SettingsAcctivity extends AppCompatActivity {

    private ArrayList<String> ImageUrls = new ArrayList<>();
    private ArrayList<String> ImageNames = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("Example", "onCreate");
        getIntent().setAction("Already created");
        super.onCreate(savedInstanceState);
        SettingsAcctivity.this.setTitle("Setting");
        setContentView(R.layout.easysetting);
        LinearLayout container = findViewById(R.id.settingsContainer);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ArrayList<SettingsObject> mySettingsList = EasySettings.createSettingsArray(
                new BasicSettingsObject.Builder("basicSettingsKey1", "fancy title 1")
                        .setSummary("fancy summary")
                        .build(),
                new BasicSettingsObject.Builder("basicSettingsKey2","fancy title 2")
                        .setSummary("not so fancy summary")
                        .build(),
                new CheckBoxSettingsObject.Builder("cehckBoxKey", "checkbox title", false)
                        .setSummary("checkbox summary")
                        .build(),
                new SwitchSettingsObject.Builder("darkmode", "switch title", true)
                        .setUseValueAsSummary()
                        .addDivider()
                        .build());
        boolean value = EasySettings.retrieveSettingsSharedPrefs(this).getBoolean("darkmode", false);
        EasySettings.initializeSettings(this, mySettingsList);

        EasySettings.inflateSettingsLayout(this, container, mySettingsList);


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