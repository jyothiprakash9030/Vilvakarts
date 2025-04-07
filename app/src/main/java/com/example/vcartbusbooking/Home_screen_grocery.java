package com.example.vcartbusbooking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Home_screen_grocery extends AppCompatActivity {
    private EditText searchEditText;
    private ImageView searchIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_screen_grocery);
        getWindow().setStatusBarColor(Color.WHITE);

        // Force status bar icons to be dark (black) in both light and dark modes
        getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );



        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        // Create a list of brands
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(R.drawable.britannia, "Britannia"));
        brands.add(new Brand(R.drawable.amul, "Amul"));
        brands.add(new Brand(R.drawable.nestle, "Nestle"));
        brands.add(new Brand(R.drawable.cadbury, "Cadbury"));
        brands.add(new Brand(R.drawable.itc, "ITC"));
        brands.add(new Brand(R.drawable.pg, "P&G"));

        // Set up the RecyclerView
        BrandAdapter adapter = new BrandAdapter(this, brands);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // 3 columns
        recyclerView.setAdapter(adapter);

//////// product page


//        RecyclerView recyclerView1 = findViewById(R.id.recycler_view1);
//        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//        // Create dummy data
//        List<product> productList = new ArrayList<>();
//        productList.add(new product("Amul Toned Fresh Milk", "1 Ltr", "₹60", R.drawable.amul, "8 Min"));
//        productList.add(new product("Amul Ginger Doodh", "1 Ltr", "₹60", R.drawable.amulginger, "5 Min"));
//        productList.add(new product("butter ", "1 Ltr", "₹120", R.drawable.butter, "6Min"));
//        productList.add(new product("Eggs Pack", "1 tray", "₹320", R.drawable.egg, "8 Min"));
//        productList.add(new product("Cocount milk", "1 Ltr", "₹120", R.drawable.cocount_milk, "10 Min"));
//
//
//        // Set adapter
//        ProductAdapter adapter1 = new ProductAdapter(productList);
//        recyclerView1.setAdapter(adapter1);


        searchEditText = findViewById(R.id.action_search);
        searchIcon = findViewById(R.id.search_icon);

        // Set click listener for the search icon
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString().trim();

                if (!query.isEmpty()) {
                    // Pass the query to SearchResultActivity
                    Intent intent = new Intent(Home_screen_grocery.this, SearchResultActivity.class);
                    intent.putExtra("searchQuery", query);
                    startActivity(intent);
                } else {
                    // Show a message if the input is empty
                    Toast.makeText(Home_screen_grocery.this, "Please enter a product to search", Toast.LENGTH_SHORT).show();
                }
            }
        });



////////////////////////
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontalScrollView);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        ImageButton btnLeft = findViewById(R.id.btnLeft);
        ImageButton btnRight = findViewById(R.id.btnRight);

        // Categories to display
        String[] categories = {
                "Fruits & Vegetables",
                "Groceries",
                "Dairy Products",
                "milk",
                "soft drinks",
                "Snacks",


        };

        // Dynamically add TextViews for each category
        for (String category : categories) {
            TextView textView = new TextView(this);

            // Set category name as text
            textView.setText(category);

            // Set static style for all TextViews
            textView.setTextSize(16);
            textView.setPadding(20, 20, 20, 20);
            textView.setGravity(android.view.Gravity.CENTER);

            // Ensure text is single line and adjusts width automatically
            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.END);

            // Set LayoutParams to wrap content for automatic width
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Auto width
                    LinearLayout.LayoutParams.WRAP_CONTENT // Auto height
            ));

            // Add TextView to the LinearLayout
            linearLayout.addView(textView);
        }

        // Scroll buttons functionality
        btnLeft.setOnClickListener(v -> horizontalScrollView.smoothScrollBy(300, 0));
        btnRight.setOnClickListener(v -> horizontalScrollView.smoothScrollBy(-300, 0));
    }

    public void skipnowgrocery(View view)
    {
        view.setBackgroundResource(R.drawable.busbookingbackground);

        // Change the TextView color
        TextView textView = findViewById(R.id.text_shopping);
     // Replace with your color resource

        // Set the background to invisible after 3000 milliseconds
        new android.os.Handler().postDelayed(() -> {
            textView.setTextColor(getResources().getColor(R.color.white));// Set the background to transparent
        }, 1000);

        // Navigate to the next screen
        Intent intent = new Intent(Home_screen_grocery.this, SourceAndDestination.class);
        startActivity(intent);


    }




}