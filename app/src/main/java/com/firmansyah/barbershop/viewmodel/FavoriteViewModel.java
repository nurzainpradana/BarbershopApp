package com.firmansyah.barbershop.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.api.Api;
import com.firmansyah.barbershop.api.ApiInterface;
import com.firmansyah.barbershop.api.RetroConfig;
import com.firmansyah.barbershop.model.Barbershop;
import com.firmansyah.barbershop.model.Result;
import com.firmansyah.barbershop.model.ResultBarbershop;
import com.firmansyah.barbershop.util.AppUtilits;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<List<Barbershop>> favoriteMutableLiveData = new MutableLiveData<>();

    public void setFavorite(Context context) {
        ApiInterface Service;
        Call<ResultBarbershop> Call;
        try {
            Service = Api.getApi().create(ApiInterface.class);
            Call = Service.getFavorite();
            Call.enqueue(new Callback<ResultBarbershop>() {
                @Override
                public void onResponse(retrofit2.Call<ResultBarbershop> call, Response<ResultBarbershop> response) {
                    List<Barbershop> barber = response.body().getResult();
                    favoriteMutableLiveData.postValue(barber);
                }

                @Override
                public void onFailure(retrofit2.Call<ResultBarbershop> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("ERROR 1", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ERROR EUY", e.getMessage());
        }
    }

    public MutableLiveData<List<Barbershop>> getFavoriteMutableLiveData() {
        return favoriteMutableLiveData;
    }
}

