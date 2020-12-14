package com.firmansyah.barbershop.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firmansyah.barbershop.api.Api;
import com.firmansyah.barbershop.api.ApiInterface;
import com.firmansyah.barbershop.model.Barbershop;
import com.firmansyah.barbershop.model.Result;
import com.firmansyah.barbershop.model.ResultBarbershop;
import com.firmansyah.barbershop.view.detail.DetailProduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteViewModel extends ViewModel {

    public MutableLiveData<List<Barbershop>> favoriteMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> result = new MutableLiveData<>();

    public MutableLiveData<Integer> getResult() {
        return result;
    }

    public void getFavorite() {
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


    public void addToFavorite(Context context, Integer id_barber) {
        ApiInterface Service;
        Call<Result> Call;
        try {
            Service = Api.getApi().create(ApiInterface.class);
            Call = Service.addtoFavorite(id_barber);
            Call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(retrofit2.Call<Result> call, Response<Result> response) {

                }

                @Override
                public void onFailure(retrofit2.Call<Result> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("ERROR 1", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ERROR EUY", e.getMessage());
        }
    }

    public void checkFavorite(Context context, Integer id_barber) {
        ApiInterface Service;
        Call<Result> Call;
        try {
            Service = Api.getApi().create(ApiInterface.class);
            Call = Service.checkFavorite(id_barber);
            Call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(retrofit2.Call<Result> call, Response<Result> response) {
                    if (response.body() != null){
                        Log.i("FAVO", response.body().getMessage());
                        result.postValue(response.body().getValue());
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<Result> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("ERROR 1", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ERROR EUY", e.getMessage());
        }
    }

    public void deleteFavorite(Context context, Integer id_barber) {
        ApiInterface Service;
        Call<Result> Call;
        try {
            Service = Api.getApi().create(ApiInterface.class);
            Call = Service.deleteFromFavorite(id_barber);
            Call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(retrofit2.Call<Result> call, Response<Result> response) {
                    if (response.body() != null){
                        Log.i("FAV1", response.body().getMessage());
                        result.postValue(response.body().getValue());
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<Result> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("ERROR 1", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ERROR EUY", e.getMessage());
        }
    }


}

