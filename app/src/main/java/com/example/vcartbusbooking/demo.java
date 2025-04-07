package com.example.vcartbusbooking;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class demo extends AppCompatActivity {
    private static final String TAG = "API_RESPONSE";
    private RecyclerView recyclerView;
    private TripAdapter tripAdapter;
    private List<Trip> tripList = new ArrayList<>();
    private LinearLayout seatsContainer;
    private int selectedSeats = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        tripAdapter = new TripAdapter(tripList);
//        recyclerView.setAdapter(tripAdapter);

//        makeRequest();



        // Call this method whenever seat selection changes
        updateSeatFields(2);
    }

//    private void makeRequest() {
//        OkHttpClient client = new OkHttpClient().newBuilder().build();
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"source_id\": 3,   \r\n    \"destination_id\": 6,\r\n    \"date_of_journey\": \"2025-02-20\",\r\n    \"user_id\":41\r\n}\r\n");
//
//        Request request = new Request.Builder()
//                .url("https://api-629-bis.vilvabusiness.com/api/getAvailableTrips")
//                .method("POST", body)
//                .addHeader("Accept", "application/json")
//                .addHeader("Content-Type", "application/json")
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e(TAG, "Request Failed: " + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    Log.e(TAG, "Request Failed: " + response.code());
//                    return;
//                }
//
//                String responseData = response.body().string();
//                Log.d(TAG, "Full API Response: " + responseData);
//
//                try {
//                    JSONObject jsonResponse = new JSONObject(responseData);
//                    // Use the "data" key instead of "trips"
//                    JSONArray tripsArray = jsonResponse.optJSONArray("data");
//                    if (tripsArray == null) {
//                        Log.e(TAG, "No 'data' key found in the response. Response: " + jsonResponse.toString());
//                        return;
//                    }
//
//                    List<Trip> tempList = new ArrayList<>();
//                    for (int i = 0; i < tripsArray.length(); i++) {
//                        JSONObject tripObject = tripsArray.getJSONObject(i);
//                        // Assuming you want to display the bus type as the bus name.
//                        String busName = tripObject.optString("bus_type", "N/A");
//                        String departureTime = tripObject.optString("departure_time", "N/A");
//                        String arrivalTime = tripObject.optString("arrival_time", "N/A");
//                        String price = tripObject.optString("price", "N/A");
//
//                        tempList.add(new Trip(busName, departureTime, arrivalTime, price));
//                    }
//
//                    runOnUiThread(() -> {
//                        tripList.clear();
//                        tripList.addAll(tempList);
//                        tripAdapter.notifyDataSetChanged();
//                    });
//
//                } catch (JSONException e) {
//                    Log.e(TAG, "JSON Parsing Error: " + e.getMessage());
//                }
//            }
//
//        });


    //    }
    private void updateSeatFields(int seatCount) {
        seatsContainer.removeAllViews(); // Clear previous fields

        for (int i = 0; i < seatCount; i++) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(10, 10, 10, 10);

            EditText name = new EditText(this);
            name.setHint("Name");

            EditText gender = new EditText(this);
            gender.setHint("Gender");

            EditText age = new EditText(this);
            age.setHint("Age");
            age.setInputType(InputType.TYPE_CLASS_NUMBER);

            layout.addView(name);
            layout.addView(gender);
            layout.addView(age);

            seatsContainer.addView(layout);
        }


    }
}