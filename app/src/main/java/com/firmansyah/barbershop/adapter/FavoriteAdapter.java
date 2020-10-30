package com.firmansyah.barbershop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.model.CartItem;
import com.firmansyah.barbershop.util.Const;
import com.firmansyah.barbershop.view.detail.DetailProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context context;
    private List<CartItem> cartItemList;

    public FavoriteAdapter(Context context, List<CartItem> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_wishlist, parent, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.txt_name.setText(cartItemList.get(i).getName());
        holder.txt_price.setText("Rp " + cartItemList.get(i).getPrice());

        Picasso.get()
                .load(Const.IMAGE_PRODUCT_URL + cartItemList.get(i).getImage())
                .error(R.drawable.ic_atk)
                .fit()
                .into(holder.img_product);

        //Move to detail
        holder.itemView.setOnClickListener(v -> {
            Intent detailIntent = new Intent(holder.itemView.getContext(), DetailProduct.class);

            detailIntent.putExtra("id_products", cartItemList.get(i).getId_products());
            detailIntent.putExtra("name", cartItemList.get(i).getName());
            detailIntent.putExtra("price", cartItemList.get(i).getPrice());
            detailIntent.putExtra("description", cartItemList.get(i).getDescription());
            detailIntent.putExtra("image", cartItemList.get(i).getImage());

            holder.itemView.getContext().startActivity(detailIntent);
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_barbershop)
        TextView txt_name;
        @BindView(R.id.address_barbershop)
        TextView txt_price;
        @BindView(R.id.image_barbershop)
        ImageView img_product;
        @BindView(R.id.btn_cart_delete)
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
