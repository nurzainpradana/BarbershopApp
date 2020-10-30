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
import com.firmansyah.barbershop.model.Product;
import com.firmansyah.barbershop.util.Const;
import com.firmansyah.barbershop.view.detail.DetailProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BestAdapter extends RecyclerView.Adapter<BestAdapter.ViewHolder> {


    Product product;
    private Context context = null;
    private ArrayList<Product> productList;

    public BestAdapter(Context context, ArrayList<Product> productList){
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public BestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        holder.txt_name.setText(productList.get(i).getName());
        holder.txt_price.setText("Rp " + productList.get(i).getPrice());

        Picasso.get()
                .load(Const.IMAGE_PRODUCT_URL + productList.get(i).getImage())
                .error(R.drawable.ic_atk)
                .fit()
                .into(holder.img_product);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(holder.itemView.getContext(), DetailProduct.class);

                detailIntent.putExtra("id_products", productList.get(i).getId_products());
                detailIntent.putExtra("name", productList.get(i).getName());
                detailIntent.putExtra("price", productList.get(i).getPrice());
                detailIntent.putExtra("description", productList.get(i).getDescription());
                detailIntent.putExtra("image", productList.get(i).getImage());

                holder.itemView.getContext().startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_barbershop)
        TextView txt_name;
        @BindView(R.id.address_barbershop)
        TextView txt_price;
        @BindView(R.id.image_barbershop)
        ImageView img_product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
