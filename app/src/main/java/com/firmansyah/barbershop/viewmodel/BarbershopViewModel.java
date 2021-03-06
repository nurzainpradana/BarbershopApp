package com.firmansyah.barbershop.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firmansyah.barbershop.api.Api;
import com.firmansyah.barbershop.api.ApiInterface;
import com.firmansyah.barbershop.model.Barbershop;
import com.firmansyah.barbershop.model.ResultBarbershop;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarbershopViewModel extends ViewModel {

    public MutableLiveData<List<Barbershop>> barberMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Barbershop>> cariBarberMutableLiveData = new MutableLiveData<>();

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

    public void setCariBarshop(Context context, String kataKunci) {
        ApiInterface Service;
        Call<ResultBarbershop> Call;
        try {
            Service = Api.getApi().create(ApiInterface.class);
            Call = Service.getSearchBarber(kataKunci);
            Call.enqueue(new Callback<ResultBarbershop>() {
                @Override
                public void onResponse(retrofit2.Call<ResultBarbershop> call, Response<ResultBarbershop> response) {
                    if (response.body() != null){
                        List<Barbershop> barber = response.body().getResult();
                        cariBarberMutableLiveData.postValue(barber);
                        Log.d("ERROR VM", cariBarberMutableLiveData.toString());
                    } else {
                        Log.d("ERROR VM", "error");
                    }

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

    public MutableLiveData<List<Barbershop>> getCariBarberMutableLiveData() {
        return cariBarberMutableLiveData;
    }
}

