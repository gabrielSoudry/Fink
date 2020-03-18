package com.esilv.fink.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.esilv.fink.R;
import com.esilv.fink.api.Statistic;
import com.esilv.fink.api.StatisticResponse;
import com.esilv.fink.api.StatisticsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        service2.search("1234").enqueue(new Callback<StatisticResponse>() {
            @Override
            public void onResponse(@NonNull Call<StatisticResponse> call, @NonNull Response<StatisticResponse> response) {
                Log.d(TAG, "onResponse");
                if (response.isSuccessful()) {
                    StatisticResponse statResponse = response.body();
                    Statistic statistic = statResponse.getStatistics();
                    String test = statistic.toString();
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
