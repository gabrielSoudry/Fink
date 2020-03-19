
package com.esilv.fink.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.esilv.fink.Chart.BarChartItem;
import com.esilv.fink.Chart.ChartItem;
import com.esilv.fink.Chart.DemoBase;
import com.esilv.fink.Chart.LineChartItem;
import com.esilv.fink.Chart.PieChartItem;
import com.esilv.fink.R;
import com.esilv.fink.SettingsAcctivity;
import com.esilv.fink.api.Customer;
import com.esilv.fink.api.Statistic;
import com.esilv.fink.api.StatisticResponse;
import com.esilv.fink.api.StatisticsService;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hotmail.or_dvir.easysettings.pojos.EasySettings;
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


public class Dashboard extends DemoBase {

    private StatisticsService service2;
    Statistic statistic;
    ListView lv;


    @Override
    protected void onResume() {
        Log.v("Example", "onResume");

        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if(action == null || !action.equals("Already created")) {
            Log.v("Example", "Force restart");
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
            finish();
        }
        // Remove the unique action so the next time onResume is called it will restart
        else
            getIntent().setAction(null);

        LinearLayout li= findViewById(R.id.linear);
        boolean value = EasySettings.retrieveSettingsSharedPrefs(this).getBoolean("darkmode", false);

        if(!value)
            li.setBackgroundColor(Color.WHITE);
        else
            li.setBackgroundColor(Color.BLACK);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("Example", "onCreate");
        getIntent().setAction("Already created");
        Customer customerSelected = (Customer)getIntent().getSerializableExtra("customerLogin");
        boolean value = EasySettings.retrieveSettingsSharedPrefs(this).getBoolean("darkmode", false);
        System.out.println("====================");
        System.out.println(value);
        System.out.println("====================");


        super.onCreate(savedInstanceState);
        Log.v("Example", "onCreate");
        getIntent().setAction("Already created");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_listview_chart);
        lv = findViewById(R.id.listView1);
        new DrawerBuilder().withActivity(this).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://130.61.95.117:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service2 = retrofit.create(StatisticsService.class);
        String id = "CUSTOMERID=16000000";

        LinearLayout li= findViewById(R.id.linear);

        if(!value)
            li.setBackgroundColor(Color.WHITE);
        else
            li.setBackgroundColor(Color.BLACK);

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Our product");
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(2).withName("Setting");

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
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
                        return  true;

                        // do something with the clicked item :D
                    }
                })
                .build();
        setTitle("Dashboard");



        service2.search(id).enqueue(new Callback<StatisticResponse>() {
            @Override
            public void onResponse(@NonNull Call<StatisticResponse> call, @NonNull Response<StatisticResponse> response) {
                Log.d(TAG, "onResponse");
                if (response.isSuccessful()) {
                    StatisticResponse statResponse = response.body();
                    statistic = statResponse.getStatistics();
                    String test = statistic.toString();
                    System.out.println(test);
                    ArrayList<ChartItem> list = new ArrayList<>();

                    // 30 items
                    for (int i = 0; i < 30; i++) {

                        if(i % 3 == 0) {
                            list.add(new LineChartItem(generateDataLine(i + 1), getApplicationContext()));
                        } else if(i % 3 == 1) {
                            list.add(new BarChartItem(generateDataBar(i + 1), getApplicationContext()));
                        } else if(i % 3 == 2) {
                            PieChartItem chart =  new PieChartItem(generateDataPie(), getApplicationContext());
                            list.add(new PieChartItem(generateDataPie(), getApplicationContext()));
                        }
                    }

                    ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
                    lv.setAdapter(cda);
                }
            }

            @Override
            public void onFailure(Call<StatisticResponse> call, Throwable t) {
                System.out.println("FAILED______________________________________________");
                Log.e(TAG, "onFailure", t);
            }
        });



    }

    /** adapter that supports 3 different item types */
    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {

        ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            //noinspection ConstantConditions
            return getItem(position).getView(position, convertView, getContext());
        }

        @Override
        public int getItemViewType(int position) {
            // return the views type
            ChartItem ci = getItem(position);
            return ci != null ? ci.getItemType() : 0;
        }

        @Override
        public int getViewTypeCount() {
            return 3; // we have 3 different item-types
        }
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return Line data
     */
    private LineData generateDataLine(int cnt) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            values1.add(new Entry(i, (int) (Math.random() * 65) + 40));
        }

        LineDataSet d1 = new LineDataSet(values1, "New DataSet " + cnt + ", (1)");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);
        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            values2.add(new Entry(i, values1.get(i).getY() - 30));
        }

        LineDataSet d2 = new LineDataSet(values2, "New DataSet " + cnt + ", (2)");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(false);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        sets.add(d2);

        return new LineData(sets);
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return Bar data
     */
    private BarData generateDataBar(int cnt) {
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i, (int) (Math.random() * 70) + 30));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);
        return cd;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return Pie data
     */
    private PieData generateDataPie() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add((new PieEntry(Float.valueOf(String.valueOf(statistic.getSHOPPING())),"Shopping")));
        entries.add((new PieEntry(Float.valueOf(String.valueOf(statistic.getELECTRICITE())),"Electricite")));
        entries.add((new PieEntry(Float.valueOf(String.valueOf(statistic.getALIMENTATION())),"Alimentation")));
        entries.add((new PieEntry(Float.valueOf(String.valueOf(statistic.getINTERNET())),"Internet")));
        entries.add((new PieEntry(Float.valueOf(String.valueOf(statistic.getAUTRES())),"Autre")));
        entries.add((new PieEntry(Float.valueOf(String.valueOf(statistic.getLOISIRS())),"Loisirs")));
        entries.add((new PieEntry(Float.valueOf(String.valueOf(statistic.getMULTIMEDIA())),"Multimedia")));
        entries.add((new PieEntry(Float.valueOf(String.valueOf(statistic.getSANTE())),"Sante")));
        entries.add((new PieEntry(Float.valueOf(String.valueOf(statistic.getRESTAURANTS())),"Restaurant")));
        entries.add((new PieEntry(Float.valueOf(String.valueOf(statistic.getRETRAITS())),"Retrait")));

        PieDataSet d = new PieDataSet(entries, "");

        final int[] JOYFUL_COLORS = {
                Color.rgb(217, 80, 138), Color.rgb(254, 149, 7), Color.rgb(0, 0, 0),
                Color.rgb(106, 167, 134), Color.rgb(53, 194, 209),  Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
        };

        // space between slices
        d.setSliceSpace(2f);
        d.setColors(JOYFUL_COLORS);


        return new PieData(d);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.only_github, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.viewGithub: {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.facebook.com/"));
                startActivity(i);
                break;
            }
            case R.id.setting: {
                Intent i = new Intent(this, SettingsAcctivity.class);
                startActivity(i);
            }
        }
        return true;
    }
    @Override
    public void saveToGallery() { /* Intentionally left empty */ }
}
