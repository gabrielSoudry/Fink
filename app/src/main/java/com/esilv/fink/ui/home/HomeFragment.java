package com.esilv.fink.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.esilv.fink.Model.DemoBase;
import com.esilv.fink.R;
import com.esilv.fink.api.ApiGetResponse;
import com.esilv.fink.api.Customer;
import com.esilv.fink.api.MyAPIService;
import com.esilv.fink.api.Stat;
import com.esilv.fink.api.StatisticResponse;
import com.esilv.fink.api.StatisticsService;

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;


import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private StatisticsService service2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://130.61.95.117:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service2 = retrofit.create(StatisticsService.class);

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        textView.setText("gros bisous");

        service2.search().enqueue(new Callback<StatisticResponse>() {
            @Override
            public void onResponse(@NonNull Call<StatisticResponse> call, @NonNull Response<StatisticResponse> response) {
                Log.d(TAG, "onResponse");
                if (response.isSuccessful()) {
                    StatisticResponse statResponse = response.body();
                    Stat stat = statResponse.getStatistics();
                    String test = stat.toString();
                    textView.setText(test);
                }
            }

            @Override
            public void onFailure(Call<StatisticResponse> call, Throwable t) {
                System.out.println("FAILED______________________________________________");
                Log.e(TAG, "onFailure", t);

                textView.setText("fail updated");
            }
        });

        return root;
    }
}
