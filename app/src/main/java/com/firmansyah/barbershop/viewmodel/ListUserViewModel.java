package com.firmansyah.barbershop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firmansyah.barbershop.api.Api;
import com.firmansyah.barbershop.api.ApiInterface;
import com.firmansyah.barbershop.model.ResultUser;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUserViewModel extends ViewModel {
    private MutableLiveData<ResultUser> listUser = new MutableLiveData<>();

    public MutableLiveData<ResultUser> getListUser() {
        return listUser;
    }

    public void setListUser() {
        ApiInterface Service;
        Call<ResultUser> Call;

        try {
            Service = Api.getApi().create(ApiInterface.class);
            Call = Service.getAllUser();
            Call.enqueue(new Callback<ResultUser>() {
                @Override
                public void onResponse(retrofit2.Call<ResultUser> call, Response<ResultUser> response) {
                    ResultUser listUsers;
                    listUsers = response.body();
                    listUser.postValue(listUsers);
                }

                @Override
                public void onFailure(retrofit2.Call<ResultUser> call, Throwable t) {
                    Log.d("Error Bosq", Objects.requireNonNull(t.getMessage()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
