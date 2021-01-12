package com.firmansyah.barbershop.view.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.adapter.BarbershopsAdapter;
import com.firmansyah.barbershop.model.Barbershop;
import com.firmansyah.barbershop.viewmodel.BarbershopViewModel;

import java.util.List;

import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    public static HomeFragment hf;

    private Context mContext;
    private List<Barbershop> barbershopList;

    public BarbershopsAdapter barbershopsAdapter;
    private RecyclerView rvBarbershop;
    BarbershopViewModel barbershopViewModel;

    LinearLayout llSearch;

    TextView tvDataNotFound;

    EditText etCari;
    ImageButton btnCari;

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hf = this;
        mContext = getContext();
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

        llSearch = view.findViewById(R.id.ll_search);
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rvBarbershop = view.findViewById(R.id.rv_list_barbershop);

        tvDataNotFound = view.findViewById(R.id.tv_data_not_found);

        etCari = view.findViewById(R.id.et_cari);
        btnCari = view.findViewById(R.id.btn_cari);

        rvBarbershop.setLayoutManager(new LinearLayoutManager(getContext()));

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCari.getText() == null || etCari.getText().toString().equals("")){
                    loadDataBarbershop();
                }else {
                    loadCariBarber(etCari.getText().toString());
                }
                //barbershopsAdapter.notifyDataSetChanged();
            }
        });

        loadDataBarbershop();
    }

    private void loadCariBarber(String kata_kunci) {
        barbershopViewModel.setCariBarshop(mContext, kata_kunci);
        barbershopViewModel.getCariBarberMutableLiveData().observe(this.getViewLifecycleOwner(), new Observer<List<Barbershop>>() {
            @Override
            public void onChanged(List<Barbershop> barbershops) {
                if (barbershops != null) {
//                    tvDataNotFound.setVisibility(View.INVISIBLE);
                    if (barbershops.size() > 0) {
                        tvDataNotFound.setVisibility(View.INVISIBLE);
                        barbershopsAdapter = new BarbershopsAdapter(mContext, barbershops);
                        rvBarbershop.setAdapter(barbershopsAdapter);
                        Log.i("TEST", "Case 1");
                    } else {
                        tvDataNotFound.setVisibility(View.VISIBLE);
                        loadDataBarbershop();
                        Log.i("TEST", "Case 2");
                    }
                } else {
                    tvDataNotFound.setVisibility(View.INVISIBLE);
//                    loadDataBarbershop();
//                    Log.i("TEST", "Case 3");
                }
                //tvDataNotFound.setVisibility(View.VISIBLE);
            }
        });
    }

    public void loadDataBarbershop() {
        barbershopViewModel.setBarshop(mContext);
        barbershopViewModel.getBarberMutableLiveData().observe(this.getViewLifecycleOwner(), new Observer<List<Barbershop>>() {
            @Override
            public void onChanged(List<Barbershop> barbershops) {
                barbershopsAdapter = new BarbershopsAdapter(mContext, barbershops);
                rvBarbershop.setAdapter(barbershopsAdapter);
            }
        });

    }
}
