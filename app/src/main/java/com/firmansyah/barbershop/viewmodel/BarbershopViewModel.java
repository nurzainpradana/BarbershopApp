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
import com.firmansyah.barbershop.model.ResultUser;
import com.firmansyah.barbershop.model.User;
import com.firmansyah.barbershop.util.Const;
import com.firmansyah.barbershop.util.SharePref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarbershopViewModel extends ViewModel {

    private MutableLiveData<List<Barbershop>> barberMutableLiveData = new MutableLiveData<>();

    public void setBarshop(Context context) {
        ApiInterface Service;
        Call<ResultBarbershop> Call;
        try {
            Service = Api.getApi().create(ApiInterface.class);
            Call = Service.getAllbarber();
            Call.enqueue(new Callback<ResultBarbershop>() {
                @Override
                public void onResponse(retrofit2.Call<ResultBarbershop> call, Response<ResultBarbershop> response) {
                    List<Barbershop> barber = response.body().getResult();
                    barberMutableLiveData.postValue(barber);
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

    public MutableLiveData<List<Barbershop>> getBarberMutableLiveData() {
        return barberMutableLiveData;
    }
}

