package com.example.vcartbusbooking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private List<SearchProduct> productList;


    public SearchResultAdapter(List<SearchProduct> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchProduct currentProduct = productList.get(position);
        holder.productNameTextView.setText(currentProduct.getProductName());
        holder.productImageView.setImageResource(currentProduct.getProductImage());
        holder.delivery_time.setText(currentProduct.getDelivery_time());
        holder.product_description.setText(currentProduct.getProduct_description());
        holder.product_price.setText("₹" + currentProduct.getProduct_price());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImageView;
        public TextView productNameTextView ,delivery_time,product_description,product_price;

        public ViewHolder(View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.product_image);
            productNameTextView = itemView.findViewById(R.id.product_title);
            delivery_time=itemView.findViewById(R.id.delivery_time);
            product_description=itemView.findViewById(R.id.product_description);
            product_price=itemView.findViewById(R.id.product_price);

        }
    }

}
