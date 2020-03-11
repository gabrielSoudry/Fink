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

import com.esilv.fink.R;
import com.esilv.fink.api.ApiGetResponse;
import com.esilv.fink.api.Customer;
import com.esilv.fink.api.MyAPIService;

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private MyAPIService service;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://130.61.95.117:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(MyAPIService.class);

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);


        service.search().enqueue(new Callback<ApiGetResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiGetResponse> call, @NonNull Response<ApiGetResponse> response) {
                Log.d(TAG, "onResponse");
                if (response.isSuccessful()) {
                    ApiGetResponse apiSearchResponse = response.body();
                    try {
                        System.out.println(response.body().toString());
                        final List<Customer> itemList = apiSearchResponse.getCustomers();
                        System.out.println("______________________________");
                        System.out.println(itemList.get(0).toString());
                        System.out.println("______________________________");
                        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                            @Override
                            public void onChanged(@Nullable String s) {
                                textView.setText(itemList.get(0).toString());
                            }
                        });
                    } catch (ParseException e) {
                        e.printStackTrace();
                        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                            @Override
                            public void onChanged(@Nullable String s) {
                                textView.setText("bugged");
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiGetResponse> call, Throwable t) {
                Log.e(TAG, "onFailure", t);
            }
        });

        return root;
    }
}
