package com.esilv.fink.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.esilv.fink.R;
import com.esilv.fink.api.ApiGetProductResponse;
import com.esilv.fink.api.Customer;
import com.esilv.fink.api.Product;
import com.esilv.fink.api.ProductService;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GetProductActivity extends AppCompatActivity {
    Product product;
    private Customer customerSelected;
    TextView tv;
    GifImageView gif;
    ConstraintLayout layout;
    CardView cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_product);

        layout = findViewById(R.id.productLayout);
        cd = findViewById(R.id.cardViewProduct);
        gif = findViewById(R.id.loadingGif);

        cd.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://130.61.95.117:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductService service = retrofit.create(ProductService.class);
        customerSelected = (Customer)getIntent().getSerializableExtra("customerLogin");
        service.search(customerSelected.getCUSTOMERID()).enqueue(new Callback<ApiGetProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiGetProductResponse> call, @NonNull Response<ApiGetProductResponse> response) {
                Log.d(TAG, "onResponse GetCustomers");
                if (response.isSuccessful()) {
                    ApiGetProductResponse apiResponse = response.body();
                    product = apiResponse.getProduct();

                    tv = findViewById(R.id.ProductTitle);
                    tv.setText("Fink's best product\nfor you !");

                    gif.animate().alpha(0.0f).setDuration(1000);

                    cd.setVisibility(View.VISIBLE);
                    cd.animate().alpha(0.0f);
                    cd.animate().alpha(1.0f).setDuration(1000);

                    tv = findViewById(R.id.BankNameText);
                    tv.setText(product.getBank());

                    tv = findViewById(R.id.DurationText);
                    tv.setText(product.getDurationText());

                    tv = findViewById(R.id.LoanValueText);
                    tv.setText(product.getLoanvalueText());

                    tv = findViewById(R.id.ProductTotalText);
                    tv.setText(product.getTotalText());

                    tv = findViewById(R.id.MensualiteText);
                    tv.setText(product.getMensualiteText());
                }
            }
            @Override
            public void onFailure(Call<ApiGetProductResponse> call, Throwable t) {
                Log.e(TAG, "onFailure", t);
            }
        });
    }
}
