package com.firmansyah.barbershop.view.favorite;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.adapter.BarbershopsAdapter;
import com.firmansyah.barbershop.model.Barbershop;
import com.firmansyah.barbershop.viewmodel.FavoriteViewModel;

import java.util.List;

import butterknife.ButterKnife;

public class FavoriteFragment extends Fragment {
    public static FavoriteFragment ff;
    private RecyclerView rvFavorite;
    FavoriteViewModel favoriteViewModel;

    Context mContext;

    public static BarbershopsAdapter barbershopsAdapter;


    public static Fragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ff = this;
        mContext = getContext();
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

        rvFavorite = view.findViewById(R.id.rv_favorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        loadDataBarbershop();
    }

    public void loadDataBarbershop() {
        favoriteViewModel.getFavorite();
        favoriteViewModel.getFavoriteMutableLiveData().observe(this.getViewLifecycleOwner(), new Observer<List<Barbershop>>() {
            @Override
            public void onChanged(List<Barbershop> barbershops) {
                barbershopsAdapter = new BarbershopsAdapter(mContext, barbershops);
                rvFavorite.setAdapter(barbershopsAdapter);
            }
        });

    }


    public void updateData(){
        barbershopsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataBarbershop();
    }
}
