package com.firmansyah.barbershop.view.home;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.adapter.BarbershopsAdapter;
import com.firmansyah.barbershop.adapter.CartAdapter;
import com.firmansyah.barbershop.model.Barbershop;
import com.firmansyah.barbershop.model.ResultBarbershop;
import com.firmansyah.barbershop.viewmodel.BarbershopViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    private Context mContext;
    private List<Barbershop> barbershopList;

    private RecyclerView rvBarbershop;
    BarbershopViewModel barbershopViewModel;


    public static Fragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        barbershopViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(BarbershopViewModel.class);

        rvBarbershop = view.findViewById(R.id.rv_list_barbershop);
        rvBarbershop.setLayoutManager(new LinearLayoutManager(getContext()));
        loadDataBarbershop();
    }

    private void loadDataBarbershop() {
        barbershopViewModel.setBarshop(getContext());
        barbershopViewModel.getBarberMutableLiveData().observe(this.getViewLifecycleOwner(), new Observer<List<Barbershop>>() {
            @Override
            public void onChanged(List<Barbershop> barbershops) {
                BarbershopsAdapter barbershopsAdapter = new BarbershopsAdapter(getContext(), barbershops);
                rvBarbershop.setAdapter(barbershopsAdapter);
                onResume();
            }
        });

    }

}
