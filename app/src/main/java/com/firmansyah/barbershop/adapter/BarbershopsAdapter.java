package com.firmansyah.barbershop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.model.Barbershop;
import com.firmansyah.barbershop.view.detail.DetailProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarbershopsAdapter extends RecyclerView.Adapter<BarbershopsAdapter.ProductViewHolder> {

    private Context context;
    private List<Barbershop> barbershopsList;

    public BarbershopsAdapter(Context context, List<Barbershop> barbershopsList){
        this.context = context;
        this.barbershopsList = barbershopsList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int i) {

        holder.txt_name.setText(barbershopsList.get(i).getNamaBarber());
        holder.txt_alamat.setText(barbershopsList.get(i).getAlamatBarber());

        Picasso.get()
                .load(barbershopsList.get(i).getGambarBarber())
                .fit()
                .into(holder.img_product);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(holder.itemView.getContext(), DetailProduct.class);

                detailIntent.putExtra("id_barber", barbershopsList.get(i).getIdBarber());
                detailIntent.putExtra("nama_barber", barbershopsList.get(i).getNamaBarber());
                detailIntent.putExtra("gambar_barber", barbershopsList.get(i).getGambarBarber());
                detailIntent.putExtra("gambar_barber_1", barbershopsList.get(i).getGambarBarber1());
                detailIntent.putExtra("gambar_barber_2", barbershopsList.get(i).getGambarBarber2());
                detailIntent.putExtra("gambar_barber_3", barbershopsList.get(i).getGambarBarber3());
                detailIntent.putExtra("alamat_barber", barbershopsList.get(i).getAlamatBarber());
                detailIntent.putExtra("deskripsi_barber", barbershopsList.get(i).getDeskripsiBarber());
                detailIntent.putExtra("latitude_barber", barbershopsList.get(i).getLatitudeBarber());
                detailIntent.putExtra("longitude_barber", barbershopsList.get(i).getLongitudeBarber());

                holder.itemView.getContext().startActivity(detailIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return barbershopsList.size();
    }

     class ProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_barbershop)
        TextView txt_name;
        @BindView(R.id.address_barbershop)
        TextView txt_alamat;
        @BindView(R.id.image_barbershop)
        ImageView img_product;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
     }
}
