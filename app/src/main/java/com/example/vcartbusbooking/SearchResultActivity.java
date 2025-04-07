package com.example.vcartbusbooking;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity {


    private RecyclerView verrecyclerView;
    private ProductAdapterModel adapterver;
    private List<Productmodel> verproductList;
    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;
    private List<SearchProduct> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.productRecyclerView);

// Set up GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

// Get the search query from the intent
        String searchQuery = getIntent().getStringExtra("searchQuery");

// Load products based on the query
        productList = getProducts(searchQuery);

// Set up the adapter
        adapter = new SearchResultAdapter(productList);
        recyclerView.setAdapter(adapter);


        verrecyclerView=findViewById(R.id.productRecyclerView_ver);
//        verrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 1);
        verrecyclerView.setLayoutManager(gridLayoutManager1);


        // Sample data for the products
        verproductList = new ArrayList<>();
        verproductList.add(new Productmodel("amul", R.drawable.amulmlk));

        verproductList.add(new Productmodel("butter", R.drawable.butter));
        verproductList.add(new Productmodel("Juice", R.drawable.cocount_milk));

        adapterver = new ProductAdapterModel(verproductList);
        verrecyclerView.setAdapter(adapterver);

    }

    private List<SearchProduct> getProducts(String searchQuery) {
        // Create a map of search queries and product lists
        Map<String, List<SearchProduct>> productMap = new HashMap<>();

        // Add products for each search query
        List<SearchProduct> product1List = new ArrayList<>();
        product1List.add(new SearchProduct("amul",R.drawable.amulmlk,"5 min","1 Ltr",37));

        product1List.add(new SearchProduct("cocountmilk",R.drawable.cocount_milk,"13 min","1 Ltr",54));
        product1List.add(new SearchProduct("milk_med",R.drawable.amul,"12 min","1 Ltr",30));

        product1List.add(new SearchProduct("heritage special milk",R.drawable.heritage_special_milk,"5 min","1 Ltr",37));

        List<SearchProduct> defaultList = new ArrayList<>();
       defaultList.add(new SearchProduct("Default Product", R.drawable.cocount_milk,"6 min","1 Ltr",50));

        // Populate the map
        productMap.put("milk", product1List);

        productMap.put("prakash", defaultList); // Default products

        // Get the list of products based on the search query
        return productMap.getOrDefault(searchQuery.toLowerCase(), productMap.get("default"));



    }

}
