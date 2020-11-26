package com.firmansyah.barbershop.view.detail;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.databinding.ActivityDetailProductBinding;
import com.firmansyah.barbershop.util.Const;
import com.firmansyah.barbershop.util.SharePref;
import com.firmansyah.barbershop.view.MapsActivity;
import com.firmansyah.barbershop.viewmodel.FavoriteViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailProduct extends AppCompatActivity implements View.OnClickListener, LocationListener, OnMapReadyCallback {
    MapView mapView;
    LocationManager locationManager;
    GoogleMap map;
    double latitude, longitude;

    ActivityDetailProductBinding bind;

    @BindView(R.id.img_detail_product)
    ImageView imgDetail;
    @BindView(R.id.name_detail_product)
    TextView nameDetail;
    @BindView(R.id.tv_adress_barbershop)
    TextView priceDetail;
    @BindView(R.id.desc_detail_product)
    TextView descDetail;
    @BindView(R.id.btn_add_favorite)
    ImageButton btnFavorite;

    @BindView(R.id.btn_addcart)
    Button btnAddCart;

    int quantity;
    int id_user;
    int id_product;
    int isFavorite;

    String barberName;

    FavoriteViewModel favoriteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityDetailProductBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        ButterKnife.bind(this);

        final Intent acc = getIntent();

        String id_products = acc.getExtras().getString("id_products");
        if (id_products != null){
            id_product = Integer.parseInt(id_products);
        }

        SharePref sharePref = new SharePref(this);
        id_user = sharePref.getInt(Const.ID_USER_KEY);

        //Toast.makeText(this, String.valueOf(id_user), Toast.LENGTH_SHORT).show();

        //checkFavorite(id_product, id_user);

        nameDetail.setText(acc.getStringExtra("nama_barber"));
        priceDetail.setText(acc.getStringExtra("alamat_barber"));
        descDetail.setText(acc.getStringExtra("deskripsi_barber"));

        latitude = Double.parseDouble(acc.getStringExtra("latitude_barber"));
        longitude = Double.parseDouble(acc.getStringExtra("longitude_barber"));
        barberName = acc.getStringExtra("nama_barber");

        Toast.makeText(this, String.valueOf(latitude) + String.valueOf(longitude), Toast.LENGTH_LONG).show();
        //tvIdproducts.setText(acc.getStringExtra("id_products"));

        Picasso.get()
                .load(acc.getStringExtra("gambar_barber"))
                .error(R.mipmap.ic_launcher)
                .into(imgDetail);

        btnAddCart = findViewById(R.id.btn_addcart);
        btnAddCart.setOnClickListener(this);
        btnFavorite.setOnClickListener(this);

        mapView = bind.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //Permission For Location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionListener permissionListener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {
                    Toast.makeText(getApplicationContext(), "Permission not given", Toast.LENGTH_SHORT).show();
                }
            };

            TedPermission.with(this).setPermissionListener(permissionListener)
                    .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET).check();
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
        Toast.makeText(getApplicationContext(), "Sedang Mengambil Data Lokasi, Pastikan GPS anda hidup", Toast.LENGTH_LONG).show();
    }

/*
    private void checkFavorite(Integer id_product, Integer id_user){
        favoriteViewModel = new FavoriteViewModel();
        //favoriteViewModel.checkFavorite(this, id_user, id_product);
        favoriteViewModel.getResult().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("RES CHECK", integer.toString());
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
    private void addToFavorite(int id_product, int id_user) {
        SharePref sharePref = new SharePref(getApplicationContext());
        if (!NetworkUtility.isNetworkConnected(DetailProduct.this)) {
            AppUtilits.viewMessage(DetailProduct.this, getString(R.string.network_not_connect));
        } else {
            FavoriteViewModel favoriteViewModel = new FavoriteViewModel();
            favoriteViewModel.addToFavorite(this, id_product, id_user);
            btnFavorite.setImageResource(R.drawable.ic_favorite);
        }
    }

    private void deleteFromFavorite(int id_product, int id_user) {
        if (!NetworkUtility.isNetworkConnected(DetailProduct.this)) {
            AppUtilits.viewMessage(DetailProduct.this, getString(R.string.network_not_connect));
        } else {
            FavoriteViewModel favoriteViewModel = new FavoriteViewModel();
            favoriteViewModel.deleteFromFavorite(this, id_product, id_user);
            btnFavorite.setImageResource(R.drawable.ic_non_favorite);
        }
    }

    private void addtoCart(int id_product, int id_user, int quantity) {
        if (!NetworkUtility.isNetworkConnected(DetailProduct.this)) {
            AppUtilits.viewMessage(DetailProduct.this, getString(R.string.network_not_connect));
        } else {
                CartViewModel cartViewModel = new CartViewModel();
                cartViewModel.addToCart(this, id_product, id_user, quantity);

            /*
            call.enqueue(new Callback<AddtoCart>() {
                @Override
                public void onResponse(Call<AddtoCart> call, Response<AddtoCart> response) {
                    Log.e("TAG", "respons is" + response.body().getInformation());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {

                            //Toast.makeText(DetailProduct.this, "Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        } else {

                        }
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<AddtoCart> call, Throwable t) {
                    AppUtilits.viewMessage(DetailProduct.this, getString(R.string.fail_add_to_wishlist));
                }
            });



        }
    }\


 */

    //Back
    @OnClick(R.id.img_back)
    void back() {
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_add_favorite:
                if (isFavorite == 0){
                  //  addToFavorite(id_product, id_user);
                    isFavorite = 1;
                } else if(isFavorite == 1) {
                    //deleteFromFavorite(id_product, id_user);
                    isFavorite = 0;
                }
                break;

            case R.id.btn_addcart:
                /* Intent goToMaps = new Intent(this, MapsActivity.class);
                goToMaps.putExtra("latitude_barber", latitude);
                goToMaps.putExtra("longitude_barber", longitude);
                goToMaps.putExtra("name_barber", barberName);
                startActivity(goToMaps);
                 */
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
        public void onLocationChanged(Location location) {
            // longitude = location.getLongitude();
            // latitude = location.getLatitude();

            try {
                Geocoder geocoder = new Geocoder(DetailProduct.this, Locale.getDefault());
                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
            map.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
            mapView.onResume();
        }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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
}
