package com.firmansyah.barbershop.view.detail;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.databinding.ActivityDetailProductBinding;
import com.firmansyah.barbershop.util.Const;
import com.firmansyah.barbershop.util.NetworkUtility;
import com.firmansyah.barbershop.viewmodel.FavoriteViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailProduct extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    MapView mapView;
    LocationManager locationManager;
    GoogleMap map;
    double latitude, longitude;

    ActivityDetailProductBinding bind;

    @BindView(R.id.img_detail_product)
    ImageView imgDetail;
    @BindView(R.id.iv_gambar_detail_1)
    ImageView imgDetail1;
    @BindView(R.id.iv_gambar_detail_2)
    ImageView imgDetail2;
    @BindView(R.id.iv_gambar_detail_3)
    ImageView imgDetail3;
    @BindView(R.id.name_detail_product)
    TextView nameDetail;
    @BindView(R.id.tv_adress_barbershop)
    TextView priceDetail;
    @BindView(R.id.desc_detail_product)
    TextView descDetail;
    @BindView(R.id.btn_add_favorite)
    ImageView btnFavorite;

    @BindView(R.id.btn_menuju_lokasi)
    Button btnMenujuLokasi;

    int quantity;
    int id_user;
    int id_product;
    int isFavorite;

    int id_barber;

    Intent acc = null;

    String barberName;

    FavoriteViewModel favoriteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityDetailProductBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        ButterKnife.bind(this);

        acc = getIntent();

        String id_barbers = acc.getExtras().getString("id_barber");
        if (!id_barbers.equals("")){
            id_barber = Integer.parseInt(id_barbers);
        }

        checkFavorite(id_barber);

        nameDetail.setText(acc.getStringExtra("nama_barber"));
        priceDetail.setText(acc.getStringExtra("alamat_barber"));
        descDetail.setText(acc.getStringExtra("deskripsi_barber"));

        latitude = Double.parseDouble(acc.getStringExtra("latitude_barber"));
        longitude = Double.parseDouble(acc.getStringExtra("longitude_barber"));
        barberName = acc.getStringExtra("nama_barber");

        Glide.with(this)
                .load(acc.getStringExtra("gambar_barber"))
                .into(imgDetail);
        Glide.with(this)
                .load(acc.getStringExtra("gambar_barber_1"))
                .into(imgDetail1);
        Glide.with(this)
                .load(acc.getStringExtra("gambar_barber_2"))
                .into(imgDetail2);
        Glide.with(this)
                .load(acc.getStringExtra("gambar_barber_3"))
                .into(imgDetail3);

        btnMenujuLokasi = findViewById(R.id.btn_menuju_lokasi);
        btnMenujuLokasi.setOnClickListener(this);
        btnFavorite.setOnClickListener(this);

        mapView = bind.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }


    private void checkFavorite(Integer id_barber){
        favoriteViewModel = new FavoriteViewModel();
        favoriteViewModel.checkFavorite(this, id_barber);
        favoriteViewModel.getResult().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.i("RES CHECK", integer.toString());
                if (integer == 1){
                    btnFavorite.setImageResource(R.drawable.ic_favorite);
                    isFavorite = 1;
                } else {
                    btnFavorite.setImageResource(R.drawable.ic_non_favorite);
                    isFavorite = 0;
                }

            }
        });
    }

    private void addToFavorite(int id_barber) {
        if (!NetworkUtility.isNetworkConnected(DetailProduct.this)) {
            Toast.makeText(this, getString(R.string.network_not_connect), Toast.LENGTH_SHORT).show();
        } else {
            if (isFavorite == 0) {
                FavoriteViewModel favoriteViewModel = new FavoriteViewModel();
                favoriteViewModel.addToFavorite(this, id_barber);
                btnFavorite.setImageResource(R.drawable.ic_favorite);
                isFavorite = 1;
            }
        }
    }

    private void deleteFromFavorite(int id_barber) {
        if (!NetworkUtility.isNetworkConnected(DetailProduct.this)) {
            Toast.makeText(this, getString(R.string.network_not_connect), Toast.LENGTH_SHORT).show();
        } else {
            FavoriteViewModel favoriteViewModel = new FavoriteViewModel();
            favoriteViewModel.deleteFavorite(this, id_barber);
            btnFavorite.setImageResource(R.drawable.ic_non_favorite);
        }
    }

    //Back
    @OnClick(R.id.img_back)
    void back() {
        onBackPressed();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_add_favorite:
                if (isFavorite == 0){
                    addToFavorite(id_barber);
                    isFavorite = 1;
                } else if(isFavorite == 1) {
                    deleteFromFavorite(id_barber);
                    isFavorite = 0;
                }
                break;

            case R.id.btn_menuju_lokasi:

                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+latitude+","+longitude);
                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

                // Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng loc = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(loc)
                .title(barberName));

        map.getUiSettings().setMyLocationButtonEnabled(false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
        map.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
        mapView.onResume();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void keGambar1(View view) {
        Dialog builder = new Dialog(this, android.R.style.Theme_Light);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.BLACK));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(this);
        Glide.with(this)
                .load(acc.getStringExtra("gambar_barber_1"))
                .into(imageView);

        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    public void keGambar2(View view) {
        Dialog builder = new Dialog(this, android.R.style.Theme_Light);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.BLACK));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(this);
        Glide.with(this)
                .load(acc.getStringExtra("gambar_barber_2"))
                .into(imageView);

        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    public void keGambar3(View view) {
        Dialog builder = new Dialog(this, android.R.style.Theme_Light);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.BLACK));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(this);
        Glide.with(this)
                .load(acc.getStringExtra("gambar_barber_3"))
                .into(imageView);

        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }


}
