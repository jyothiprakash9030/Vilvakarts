package com.example.vcartbusbooking;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
public class Goods_Main extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GoodsAdapter goodsAdapter;
    private List<Goods> goodsList;
    private static final String API_URL = "https://api-629-bis.vilvabusiness.com/cache/product.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_goods_main); // Set the correct layout file

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        goodsList = new ArrayList<>();
        goodsAdapter = new GoodsAdapter(this, goodsList);
        recyclerView.setAdapter(goodsAdapter);





        fetchGoods();
    }

    private void fetchGoods() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, API_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("API_RESPONSE", response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);

                                int id = obj.optInt("id", 0);
                                int categoryId = obj.optInt("category_id", 0);
                                String name = obj.optString("name", "Unknown");
                                String description = obj.optString("description", "No Description");
                                String imageUrl = obj.optString("image_url", "");
                                String mrp = obj.optString("mrp", "0");
                                String sp = obj.optString("sp", "0");
                                int stock = obj.optInt("stock", 0);
                                String weight = obj.optString("weight", "N/A");

                                goodsList.add(new Goods(id, categoryId, name, description, imageUrl, mrp, sp, stock, weight));
                            }
                            goodsAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.e("JSON_ERROR", "Parsing error: " + e.getMessage());
                            Toast.makeText(Goods_Main.this, "JSON Parsing Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API_ERROR", "Volley error: " + error.getMessage());
                Toast.makeText(Goods_Main.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }




}