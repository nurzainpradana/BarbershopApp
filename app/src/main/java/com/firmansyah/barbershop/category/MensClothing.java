package com.firmansyah.barbershop.category;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.adapter.ListProductAdapter;
import com.firmansyah.barbershop.api.ApiInterface;
import com.firmansyah.barbershop.api.RetroConfig;
import com.firmansyah.barbershop.api.response.JsonRespon;
import com.firmansyah.barbershop.model.Product;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MensClothing extends AppCompatActivity {

    //UI
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private Context mContext;

    //RecyclerView & Adapter
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    private ListProductAdapter listProductAdapter;
    private ArrayList<Product> productsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        ButterKnife.bind(this);

        //Initilization ctgSBasicFood
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MensClothing.this, 2);
        rvProduct.setLayoutManager(gridLayoutManager);
        rvProduct.setHasFixedSize(true);
        listProductAdapter = new ListProductAdapter(mContext, productsList);
        rvProduct.setAdapter(listProductAdapter);

        ctgClothing();
    }

    //Retrive product data
    public void ctgClothing() {
        tvTitle.setText("Pakaian Pria");
        RetroConfig.getApiService(null);

        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);
        Call<JsonRespon> call = request.getBest();
        call.enqueue(new Callback<JsonRespon>() {
            @Override
            public void onResponse(Call<JsonRespon> call, Response<JsonRespon> response) {
                JsonRespon jsonResponse = response.body();
                productsList = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));
                listProductAdapter = new ListProductAdapter(mContext, productsList);
                rvProduct.setAdapter(listProductAdapter);
            }

            @Override
            public void onFailure(Call<JsonRespon> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

}
