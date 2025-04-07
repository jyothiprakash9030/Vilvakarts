package com.example.vcartbusbooking;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcartbusbooking.utils.Constants;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Passenger_details extends AppCompatActivity implements PaymentResultListener {
    RecyclerView recyclerView;
    PassengerAdapter adapter;
    private static final String TAG = "PassengerDetailsLog";
    private List<PassengerInfo> passengerList = new ArrayList<>();
    TextView bus_name, passenger_point_to, passenger_point_from, passenger_date, totalamount;
    private ProgressDialog progressDialog;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_passenger_details);

        getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );

        passenger_point_to = findViewById(R.id.passenger_point_to);
        totalamount = findViewById(R.id.totalamount);
        passenger_point_from = findViewById(R.id.passenger_point_from);
        bus_name = findViewById(R.id.bus_name);
        passenger_date = findViewById(R.id.date);
        TextView passengercount = findViewById(R.id.passengercount);
        Button BlockTicket = findViewById(R.id.BlockTicket);

        // Initialize Razorpay Checkout
        Checkout.preload(getApplicationContext());

        TripDataHolder dataHolder = TripDataHolder.getInstance();
        double amount = dataHolder.getTotalAmount();
        String busType = dataHolder.getTravels();
        String date = dataHolder.getDate();
        String from = dataHolder.getFrom();
        String to = dataHolder.getTo();

        totalamount.setText("₹ " + String.format("%.2f", amount));
        bus_name.setText(busType);
        passenger_point_from.setText(from);
        passenger_point_to.setText(to);
        passenger_date.setText(date);

        List<SelectedSeat> selectedSeats = dataHolder.getSelectedSeats();
        StringBuilder seatNumbersText = new StringBuilder();
        for (int i = 0; i < selectedSeats.size(); i++) {
            seatNumbersText.append(selectedSeats.get(i).getSeatName());
            if (i < selectedSeats.size() - 1) seatNumbersText.append(", ");
        }
        String numericSeatNumbersText = seatNumbersText.toString().replaceAll("[^0-9,]", "");
        Log.d("SelectedSeats", "Seat Numbers: " + numericSeatNumbersText);

        TextView seatTextView = findViewById(R.id.selected_seats_textview);
        seatTextView.setText(seatNumbersText.toString());
        int seatCount = dataHolder.getPassengercount();
        passengercount.setText("Selected - " + seatCount + " Seat(s)");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PassengerAdapter(passengerList);
        recyclerView.setAdapter(adapter);
        adapter.updatePassengerList(selectedSeats);

        BlockTicket.setOnClickListener(view -> {
            if (areAllPassengerDetailsFilled()) {
                progressDialog = new ProgressDialog(Passenger_details.this);
                progressDialog.setMessage("Processing your request...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                blockTicket();
            } else {
                Log.e(TAG, "Passenger details incomplete");
                Toast.makeText(this, "Please fill all passenger details correctly", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePassengerFields(int seatCount) {
        passengerList.clear();
        for (int i = 0; i < seatCount; i++) {
            passengerList.add(new PassengerInfo());
        }
        adapter.notifyDataSetChanged();
    }

    private boolean areAllPassengerDetailsFilled() {
        boolean allFilled = true;
        boolean hasPrimaryPassenger = false;
        List<String> emailList = new ArrayList<>();

        int passengerCount = recyclerView.getChildCount();
        if (passengerCount == 0) {
            Log.e(TAG, "No passengers added");
            return false;
        }

        for (int i = 0; i < passengerCount; i++) {
            View itemView = recyclerView.getChildAt(i);
            if (itemView != null) {
                EditText nameEditText = itemView.findViewById(R.id.editTextName);
                EditText ageEditText = itemView.findViewById(R.id.editTextAge);
                Spinner genderSpinner = itemView.findViewById(R.id.spinnerGender);
                RadioButton radioButton = itemView.findViewById(R.id.checkbox_notifications);
                EditText passenger_Email = itemView.findViewById(R.id.passenger_Email);
                EditText passenger_whats = itemView.findViewById(R.id.passenger_whats);

                String name = nameEditText.getText().toString().trim();
                String ageStr = ageEditText.getText().toString().trim();
                String gender = genderSpinner.getSelectedItem().toString();
                boolean isPrimary = radioButton.isChecked();
                String email = passenger_Email.getText().toString().trim();
                String whatsnumber = passenger_whats.getText().toString().trim();

                if (name.isEmpty()) {
                    nameEditText.setError("* Required");
                    allFilled = false;
                }
                if (ageStr.isEmpty() || Integer.parseInt(ageStr) < 1 || Integer.parseInt(ageStr) > 100) {
                    ageEditText.setError("Enter valid age (1-100)");
                    allFilled = false;
                }
                if (gender.equals("Select Gender")) {
                    allFilled = false;
                    Log.e(TAG, "Gender not selected for passenger " + (i + 1));
                }
                if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
                    passenger_Email.setError("Enter valid @gmail.com");
                    allFilled = false;
                } else if (emailList.contains(email)) {
                    passenger_Email.setError("Email must be unique");
                    allFilled = false;
                } else {
                    emailList.add(email);
                }
                if (!whatsnumber.matches("\\d{10}")) {
                    passenger_whats.setError("Enter 10-digit number");
                    allFilled = false;
                }
                if (isPrimary) {
                    hasPrimaryPassenger = true;
                }
            }
        }

        if (passengerCount > 1 && !hasPrimaryPassenger) {
            Log.e(TAG, "No primary passenger selected");
            allFilled = false;
        }

        return allFilled;
    }

    private void blockTicket() {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        TripDataHolder dataHolder = TripDataHolder.getInstance();
        double fare = dataHolder.getTotalAmount();
        double serviceTax = dataHolder.getServiceTax();
        List<SelectedSeat> selectedSeats = dataHolder.getSelectedSeats();

        // Declare variables at method scope
        final String[] primaryContact = {""};
        final String[] primaryEmail = {""};

        try {
            String tripId = dataHolder.getTripId();
            String boardingPointId = String.valueOf(dataHolder.getBpId());
            String droppingPointId = String.valueOf(dataHolder.getDpId());
            String source = String.valueOf(dataHolder.getFromCityId());
            String destination = String.valueOf(dataHolder.getToCityId());

            // Validation checks
            if (tripId == null || tripId.trim().isEmpty()) throw new IllegalStateException("Trip ID is missing");
            if (boardingPointId == null || boardingPointId.trim().isEmpty()) throw new IllegalStateException("Boarding Point ID is missing");
            if (droppingPointId == null || droppingPointId.trim().isEmpty()) throw new IllegalStateException("Dropping Point ID is missing");
            if (source == null || source.trim().isEmpty() || source.equals("null")) throw new IllegalStateException("Source is missing");
            if (destination == null || destination.trim().isEmpty() || destination.equals("null")) throw new IllegalStateException("Destination is missing");
            if (selectedSeats == null || selectedSeats.isEmpty()) throw new IllegalStateException("Selected seats are missing");

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("availableTripId", tripId);
            jsonBody.put("boardingPointId", boardingPointId);
            jsonBody.put("droppingPointId", droppingPointId);
            jsonBody.put("source", source);
            jsonBody.put("destination", destination);
            jsonBody.put("serviceCharge", 0);
            jsonBody.put("bookingType", "STANDARD");
            jsonBody.put("paymentMode", "CASH");

            JSONArray inventoryItemsArray = new JSONArray();
            int passengerCount = recyclerView.getChildCount();

            if (passengerCount == 0) {
                throw new IllegalStateException("No passenger data available");
            }

            for (int i = 0; i < passengerCount; i++) {
                View itemView = recyclerView.getChildAt(i);
                if (itemView == null) {
                    throw new IllegalStateException("Passenger item view at index " + i + " is null");
                }

                EditText nameEditText = itemView.findViewById(R.id.editTextName);
                EditText ageEditText = itemView.findViewById(R.id.editTextAge);
                Spinner genderSpinner = itemView.findViewById(R.id.spinnerGender);
                RadioButton radioButton = itemView.findViewById(R.id.checkbox_notifications);
                EditText passenger_Email = itemView.findViewById(R.id.passenger_Email);
                EditText passenger_whats = itemView.findViewById(R.id.passenger_whats);

                String name = nameEditText.getText().toString().trim();
                String ageStr = ageEditText.getText().toString().trim();
                String gender = genderSpinner.getSelectedItem().toString();
                boolean isPrimary = radioButton.isChecked();
                String email = passenger_Email.getText().toString().trim();
                String whatsnumber = passenger_whats.getText().toString().trim();

                if (name.isEmpty() || ageStr.isEmpty() || gender.equals("Select Gender") || email.isEmpty() || whatsnumber.isEmpty()) {
                    throw new IllegalStateException("Passenger " + i + " has incomplete details");
                }

                String seatName = i < selectedSeats.size() ? selectedSeats.get(i).getSeatName() : "";
                if (seatName.isEmpty()) {
                    throw new IllegalStateException("No seat available for passenger " + i);
                }

                JSONObject passengerJson = new JSONObject();
                passengerJson.put("name", name);
                passengerJson.put("mobile", whatsnumber);
                passengerJson.put("title", gender.equals("Female") ? "Ms" : "Mr");
                passengerJson.put("email", email);
                passengerJson.put("age", Integer.parseInt(ageStr));
                passengerJson.put("gender", gender.toUpperCase());
                passengerJson.put("address", "1/46 test");
                passengerJson.put("idType", "Pancard");
                passengerJson.put("idNumber", "QWERT1234Y");
                passengerJson.put("primary", isPrimary);

                JSONObject inventoryItem = new JSONObject();
                inventoryItem.put("seatName", seatName);
                inventoryItem.put("fare", fare);
                inventoryItem.put("serviceTax", serviceTax);
                inventoryItem.put("operatorServiceCharge", 0);
                inventoryItem.put("ladiesSeat", gender.equals("Female"));
                inventoryItem.put("passenger", passengerJson);

                inventoryItemsArray.put(inventoryItem);

                if (isPrimary || (i == 0 && primaryContact[0].isEmpty())) {
                    primaryContact[0] = whatsnumber;
                    primaryEmail[0] = email;
                }
            }

            jsonBody.put("inventoryItems", inventoryItemsArray);

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody requestBody = RequestBody.create(mediaType, jsonBody.toString());

            Request request = new Request.Builder()
                    .url("https://api-629-bis.vilvabusiness.com/api/prod/blockTicket")
                    .method("POST", requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer HKNhrBJ96rosxNtDI28Mr5nTyOyluTMwyerurEFU")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("API", "Network failure: " + e.getMessage());
                    runOnUiThread(() -> {
                        progressDialog.dismiss();
                        Toast.makeText(Passenger_details.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    Log.d("API Response", "Code: " + response.code() + ", Body: " + responseData);
                    runOnUiThread(() -> {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonResponse = new JSONObject(responseData);
                                boolean success = jsonResponse.getBoolean("success");
                                String message = jsonResponse.getString("message");
                                String blockKey = jsonResponse.getString("data");

                                if (success) {
                                    String razorpayKey = "rzp_live_5YtHiQya6csewc"; // Replace with your actual key
                                    int amount = (int) (dataHolder.getTotalAmount() * 100);
                                    String currency = "INR";
                                    String description = "Ticket Booking #" + blockKey;
                                    String orderId = blockKey; // Replace with proper order ID if needed
                                    String callbackUrl = "https://your-callback-url.com";

                                    startRazorpayPayment(razorpayKey, orderId, amount, currency, description,
                                            primaryContact[0], primaryEmail[0], callbackUrl);
                                } else {
                                    Log.e("API Error", "Ticket blocking failed: " + message);
                                    Toast.makeText(Passenger_details.this, "Ticket blocking failed: " + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Log.e("JSON Error", "Error parsing response: " + e.getMessage(), e);
                                Toast.makeText(Passenger_details.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("API Errors", "Failed with code: " + response.code() + ", Body: " + responseData);
                            Toast.makeText(Passenger_details.this, "API error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        } catch (Exception e) {
            Log.e("JSON Error", "Error constructing JSON: " + e.getMessage(), e);
            runOnUiThread(() -> {
                progressDialog.dismiss();
                Toast.makeText(Passenger_details.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void startRazorpayPayment(String key, String orderId, int amount, String currency,
                                      String description, String contact, String email, String callbackUrl) {
        Checkout checkout = new Checkout();
        checkout.setKeyID(key);

        try {
            JSONObject options = new JSONObject();
            options.put("name", "VCart Bus Booking");
            options.put("description", description);
            options.put("order_id", orderId);
            options.put("currency", currency);
            options.put("amount", amount);

            JSONObject prefill = new JSONObject();
            prefill.put("contact", contact);
            prefill.put("email", email);
            options.put("prefill", prefill);

            options.put("callback_url", callbackUrl);

            checkout.open(this, options); // Use 'this' since Passenger_details implements PaymentResultListener

        } catch (JSONException e) {
            Log.e("Razorpay Error", "Error in starting Razorpay Checkout: " + e.getMessage(), e);
            Toast.makeText(this, "Error starting payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // PaymentResultListener implementation
    @Override
    public void onPaymentSuccess(String paymentId) {
        Log.d("Razorpay", "Payment successful: " + paymentId);
        Toast.makeText(this, "Payment Successful: " + paymentId, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Passenger_details.this, wait_a_second_page.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPaymentError(int code, String response) {
        Log.e("Razorpay", "Payment failed: Code " + code + ", Response: " + response);
        Toast.makeText(this, "Payment Failed: " + response, Toast.LENGTH_SHORT).show();
    }
}