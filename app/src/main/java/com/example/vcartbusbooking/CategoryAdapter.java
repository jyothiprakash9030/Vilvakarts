package com.example.vcartbusbooking;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final Context context;
    private List<Category> categoryList;
    private static final String BASE_URL = "https://your-api.com/"; // Change this to your actual base URL

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position); // Get the category object

        // Get category name
        holder.textView.setText(category.getName());

        // Handle Image URL (if API returns a relative path, prepend BASE_URL)
//        String imageUrl = category.getImage();
//        if (imageUrl != null && !imageUrl.startsWith("http")) {
//            imageUrl = BASE_URL + imageUrl;
//        }

        String imageUrl = "https://png.pngtree.com/png-clipart/20221013/ourmid/pngtree-simulation-cosmetics-product-sample-skin-care-essential-oil-png-image_6158599.png";

        // Log image URL for debugging
        Log.d("ImageURL", "Loading image: " + imageUrl);

        // Load image with Glide
        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache images for better performance

                .error(R.drawable.tsrtc) // Show if the image fails to load
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.categoryName);
            imageView = itemView.findViewById(R.id.categoryImage);
        }
    }
}
