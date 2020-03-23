
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
import com.esilv.fink.api.Transaction;
import com.esilv.fink.api.TransactionResponse;
import com.esilv.fink.api.TransactionService;
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
    private TransactionService service3;
    Statistic statistic;
    List<Transaction> transaction;
    ListView lv;
    ArrayList<ChartItem> list = new ArrayList<>();
    Customer customerSelected ;


    @Override
    protected void onResume() {
        Log.v("Example", "onResume");
        customerSelected = (Customer)getIntent().getSerializableExtra("customerLogin");
        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if(action == null || !action.equals("Already created")) {
            Log.v("Example", "Force restart");
            Intent intent = new Intent(this, Dashboard.class);
            intent.putExtra("customerLogin", customerSelected); //Put your id to your next Intent

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
        boolean value = EasySettings.retrieveSettingsSharedPrefs(this).getBoolean("darkmode", false);
        System.out.println("====================");
        System.out.println(value);
        System.out.println("====================");


        super.onCreate(savedInstanceState);
        customerSelected = (Customer)getIntent().getSerializableExtra("customerLogin");
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
        service3 = retrofit.create(TransactionService.class);

        String id = "CUSTOMERID=" + customerSelected.getCUSTOMERID();

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

                    PieChartItem chart =  new PieChartItem(generateDataPie(), getApplicationContext());
                    list.add(new PieChartItem(generateDataPie(), getApplicationContext()));

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

        customerSelected = (Customer)getIntent().getSerializableExtra("customerLogin");

        service3.search(id).enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(@NonNull Call<TransactionResponse> call, @NonNull Response<TransactionResponse> response) {
                Log.d(TAG, "onResponse");
                if (response.isSuccessful()) {
                    TransactionResponse transac = response.body();
                    transaction = transac.getTransactions();

                    String test = statistic.toString();
                    for (Transaction a : transaction) {
                        System.out.println("transaction");
                        System.out.println(a.getVALUE());
                    }
                    // 30 items
                    //list.add(new LineChartItem(generateDataLine(2 + 1), getApplicationContext()));
                    list.add(new BarChartItem(generateDataBar(2+ 1,transaction), getApplicationContext()));
                    list.add(new LineChartItem(generateDataLine(3+ 1,transaction), getApplicationContext()));
                    ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
                    lv.setAdapter(cda);
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
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
    private LineData generateDataLine(int cnt,List<Transaction> transaction) {

        ArrayList<Entry> values1 = new ArrayList<>();

        Double [] amountByMonth = new  Double[13];
        for (int i=0; i<amountByMonth.length;i++) amountByMonth[i] = new Double(0);
        for (Transaction a : transaction) {
            System.out.println(a.getMONTH() +" value:" + a.getVALUE());
            amountByMonth[a.getMONTH()] += a.getVALUE();
            System.out.println(a.getVALUE());
        }


        for (int i = 0; i < 12; i++) {
            values1.add(new Entry(i, (int)Math.round(amountByMonth[i])));
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
    private BarData generateDataBar(int cnt, List<Transaction> transaction) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        Double [] amountByMonth = new  Double[13];
        for (int i=0; i<amountByMonth.length;i++) amountByMonth[i] = new Double(0);
        for (Transaction a : transaction) {
            System.out.println(a.getMONTH() +" value:" + a.getVALUE());
                amountByMonth[a.getMONTH()] += a.getVALUE();
                System.out.println(a.getVALUE());
        }

        for (int i = 1; i < 13; i++) {
            System.out.println("amount============");
            System.out.println(amountByMonth[i]);
            entries.add(new BarEntry(i, (int)Math.round(amountByMonth[i]),"J"));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);

        int value = EasySettings.retrieveSettingsSharedPrefs(this).getInt("colors", 0);

        final int[] LIBERTY_COLORS = {
                Color.rgb(207, 248, 246), Color.rgb(148, 212, 212), Color.rgb(136, 180, 187),
                Color.rgb(118, 174, 175), Color.rgb(42, 109, 130)
        };

        final int[] p0 = {
            Color.parseColor("#f6d186"),Color.parseColor("#fcf7bb"),Color.parseColor("#cbe2b0"),Color.parseColor("#f19292")
        };

        final int[] p1 = {
                Color.parseColor("#084177"),Color.parseColor("#687466"),Color.parseColor("#cd8d7b"),Color.parseColor("#fbc490")
        };
        final int[] p2 = {
                Color.parseColor("#f76a8c"),Color.parseColor("#f8dc88"),Color.parseColor("#f8fab8"),Color.parseColor("#ccf0e1")
        };

        final int[] p3 = {
                Color.parseColor("#ffd1bd"),Color.parseColor("#ffb0cd"),Color.parseColor("#ffffff"),Color.parseColor("#c2f0fc")
        };
        final int[] p4 = {
                Color.parseColor("#000000"),Color.parseColor("#323232"),Color.parseColor("#ff1e56"),Color.parseColor("#ffac41")
        };



        if (value==0) d.setColors(p0);
        else if (value==1) d.setColors(p1);
        else if (value==2) d.setColors(p2);
        else if (value==3) d.setColors(p3);
        else if (value==4) d.setColors(p4);

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
                i.putExtra("customerLogin", customerSelected); //Put your id to your next Intent

                i.setData(Uri.parse("https://www.facebook.com/"));
                startActivity(i);
                break;
            }
            case R.id.setting: {
                Intent i = new Intent(this, SettingsAcctivity.class);
                i.putExtra("customerLogin", customerSelected); //Put your id to your next Intent

                startActivity(i);
            }
        }
        return true;
    }
    @Override
    public void saveToGallery() { /* Intentionally left empty */ }
}
