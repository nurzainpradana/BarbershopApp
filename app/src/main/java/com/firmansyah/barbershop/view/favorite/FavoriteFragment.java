package com.firmansyah.barbershop.view.favorite;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.adapter.BarbershopsAdapter;
import com.firmansyah.barbershop.api.ApiInterface;
import com.firmansyah.barbershop.api.RetroConfig;
import com.firmansyah.barbershop.api.response.JsonRespon;
import com.firmansyah.barbershop.model.Barbershop;
import com.firmansyah.barbershop.model.Product;
import com.firmansyah.barbershop.util.Const;
import com.firmansyah.barbershop.util.SharePref;
import com.firmansyah.barbershop.view.home.HomeFragment;
import com.firmansyah.barbershop.viewmodel.BarbershopViewModel;
import com.firmansyah.barbershop.viewmodel.FavoriteViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {
    private RecyclerView rvFavorite;
    FavoriteViewModel favoriteViewModel;


    public static Fragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriteViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FavoriteViewModel.class);

        rvFavorite = view.findViewById(R.id.rv_list_barbershop);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        loadDataBarbershop();
    }

    private void loadDataBarbershop() {
        favoriteViewModel.setFavorite(getContext());
        favoriteViewModel.getFavoriteMutableLiveData().observe(this.getViewLifecycleOwner(), new Observer<List<Barbershop>>() {
            @Override
            public void onChanged(List<Barbershop> barbershops) {
                BarbershopsAdapter barbershopsAdapter = new BarbershopsAdapter(getContext(), barbershops);
                rvFavorite.setAdapter(barbershopsAdapter);
                onResume();
            }
        });

    }
}
