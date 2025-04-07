package com.example.vcartbusbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {
    private Context context;
    private List<Goods> goodsList;

    public GoodsAdapter(Context context, List<Goods> goodsList) {
        this.context = context;
        this.goodsList = goodsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.goods_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Goods goods = goodsList.get(position);

        holder.name.setText(goods.getName());
        holder.description.setText(goods.getDescription());
        holder.price.setText("₹" + goods.getSp()); // Selling Price
        holder.weight.setText(goods.getWeight());

        Glide.with(context)
                .load(goods.getImageUrl())
                .placeholder(R.drawable.whatsapp)
                .error(R.drawable.shopping_button_corner_radius_bg)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, price, weight;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.goodsName);
            description = itemView.findViewById(R.id.goodsDescription);
            price = itemView.findViewById(R.id.goodsPrice);
            weight = itemView.findViewById(R.id.goodsWeight);
            imageView = itemView.findViewById(R.id.goodsImage);
        }
    }
}
