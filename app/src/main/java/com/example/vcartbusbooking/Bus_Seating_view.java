package com.example.vcartbusbooking;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcartbusbooking.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Bus_Seating_view extends AppCompatActivity {
    private static final String TAG = "SEAT_LAYOUT";
    private RecyclerView seatRecyclerView;
    private SeatAdapter seatAdapter;
    private List<Seat> upperDeckSeats = new ArrayList<>();
    private List<Seat> lowerDeckSeats = new ArrayList<>();
    private List<Seat> upperGrid = new ArrayList<>();
    private List<Seat> lowerGrid = new ArrayList<>();
    private TextView deckLabel;
    private Button upperDeckButton, lowerDeckButton,btn_info;
    private int maxRowUpper = 0, maxRowLower = 0;
    private int seatSize;

    private TextView passengerCount;
    private TextView totalAmount;
    private LinearLayout footerLayout;
    private TextView from_bus_seating, to_bus_seating, bus_seatingvisibledate, travelsName;


    private int selectedSeatCount = 0;
    private float totalFare = 0;
    private ProgressDialog progressDialog;
    private List<SelectedSeat> selectedSeats = new ArrayList<>(); // Updated to List<SelectedSeat>
    private String currentDeck = "Lower"; // Track the current deck being viewed

    ImageView    driverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bus_seating_view);
        EdgeToEdge.enable(this);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        seatSize = screenWidth / 8;

        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );
        driverImage=findViewById(R.id.driverImage);
        seatRecyclerView = findViewById(R.id.seatRecyclerView);
        deckLabel = findViewById(R.id.deckLabel);
        upperDeckButton = findViewById(R.id.upperDeckButton);
        lowerDeckButton = findViewById(R.id.lowerDeckButton);

        footerLayout = findViewById(R.id.footer_layout);
        passengerCount = findViewById(R.id.passenger_count);
        totalAmount = findViewById(R.id.TotalAmount);
        Button proceedButton = findViewById(R.id.proceedButton);

        btn_info=findViewById(R.id.btn_info);


        btn_info = findViewById(R.id.btn_info);
        btn_info.setOnClickListener(v -> showSeatInfoPopup());






        proceedButton.setOnClickListener(v -> {
            Intent intent = new Intent(Bus_Seating_view.this, Boading_Details.class);
            startActivity(intent);
        });

        footerLayout.setVisibility(GONE);

        from_bus_seating = findViewById(R.id.from_bus_seating);
        to_bus_seating = findViewById(R.id.to_bus_seating);
        bus_seatingvisibledate = findViewById(R.id.bus_seatingvisibledate);
        travelsName = findViewById(R.id.travelsName);

        TripDataHolder dataHolder = TripDataHolder.getInstance();
        String from = dataHolder.getFrom();
        String to = dataHolder.getTo();
        String data = dataHolder.getDate();
        String travels = dataHolder.getTravels();

        from_bus_seating.setText(from);
        to_bus_seating.setText(to);
        bus_seatingvisibledate.setText(data);
        travelsName.setText(travels);

        String tripId = dataHolder.getTripId();
        if (tripId != null && !tripId.isEmpty()) {
            fetchSeatLayout(tripId);
        } else {
            Toast.makeText(this, "No trip selected", Toast.LENGTH_SHORT).show();
            finish();
        }

        upperDeckButton.setOnClickListener(v -> {
            upperDeckButton.setBackgroundResource(R.drawable.btn_lower_active);
            upperDeckButton.setTextColor(getResources().getColor(R.color.white));
            lowerDeckButton.setBackgroundResource(R.drawable.btn_inactive);
            lowerDeckButton.setTextColor(getResources().getColor(R.color.gray));
            showUpperDeck();
        });

        lowerDeckButton.setOnClickListener(v -> {
            lowerDeckButton.setBackgroundResource(R.drawable.btn_lower_active);
            lowerDeckButton.setTextColor(getResources().getColor(R.color.white));
            upperDeckButton.setBackgroundResource(R.drawable.btn_inactive);
            upperDeckButton.setTextColor(getResources().getColor(R.color.gray));
            showLowerDeck();
        });
    }

    private void fetchSeatLayout(String tripId) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading seats...");
        progressDialog.setCancelable(false);
        progressDialog.show(); // Show loader before making API request

        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("application/json");
                String jsonBody = "{\r\n    \"trip_id\":" + tripId + ",\r\n    \"is_uat\":false\r\n}";
                RequestBody body = RequestBody.create(mediaType, jsonBody);
                Request request = new Request.Builder()
                        .url(Constants.BASE_URL+ "getCurrentTripDetails")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer pZQpIV1kLqkBXmltpLTrfQ17m3xJiukhzW3PkroV")
                        .build();

                Response response = client.newCall(request).execute();

                if (!response.isSuccessful()) {
                    String errorMessage = "Server error: " + response.code() + " " + response.message();
                    Log.e(TAG, errorMessage);
                    if (response.body() != null) {
                        errorMessage += " - " + response.body().string();
                    }
                    final String finalErrorMessage = errorMessage;
                    runOnUiThread(() -> {
                        progressDialog.dismiss(); // Dismiss loader in case of error
                        Toast.makeText(this, finalErrorMessage, Toast.LENGTH_LONG).show();
                    });
                    return;
                }

                if (response.body() == null) {
                    Log.e(TAG, "Response body is null");
                    runOnUiThread(() -> {
                        progressDialog.dismiss(); // Dismiss loader if response is null
                        Toast.makeText(this, "No seat data received from server", Toast.LENGTH_SHORT).show();
                    });
                    return;
                }

                String responseData = response.body().string();
                Log.d(TAG, "Seat Layout Response: " + responseData);

                if (responseData.trim().isEmpty()) {
                    Log.e(TAG, "Empty response data");
                    runOnUiThread(() -> {
                        progressDialog.dismiss(); // Dismiss loader if empty response
                        Toast.makeText(this, "Empty response from server", Toast.LENGTH_SHORT).show();
                    });
                    return;
                }

                parseSeatLayout(responseData);

                runOnUiThread(progressDialog::dismiss); // Dismiss loader after successful data fetching

            } catch (java.net.UnknownHostException e) {
                Log.e(TAG, "Network error: Unable to reach server", e);
                runOnUiThread(() -> {
                    progressDialog.dismiss(); // Dismiss loader in case of network error
                    Toast.makeText(this, "Network error: Check your internet connection", Toast.LENGTH_LONG).show();
                });
            } catch (java.io.IOException e) {
                Log.e(TAG, "IO Error fetching seat layout: " + e.getMessage(), e);
                runOnUiThread(() -> {
                    progressDialog.dismiss(); // Dismiss loader in case of IO error
                    Toast.makeText(this, "IO Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error fetching seat layout: " + e.getMessage(), e);
                runOnUiThread(() -> {
                    progressDialog.dismiss(); // Dismiss loader for any unexpected error
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    private void parseSeatLayout(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            JSONArray seatsArray = dataObject.getJSONArray("seats");
            upperDeckSeats.clear();
            lowerDeckSeats.clear();
            int maxColUpper = 0, maxColLower = 0;

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int screenWidth = displayMetrics.widthPixels;
            seatSize = screenWidth / 16;

            for (int i = 0; i < seatsArray.length(); i++) {
                JSONObject seatObj = seatsArray.getJSONObject(i);
                Seat seat = new Seat(
                        seatObj.getString("name"),
                        seatObj.getBoolean("available"),
                        seatObj.getString("fare"),
                        seatObj.getInt("row"),
                        seatObj.getInt("column"),
                        seatObj.getInt("zIndex"),
                        seatObj.getBoolean("ladiesSeat"),
                        seatObj.getBoolean("malesSeat"),
                        seatObj.getInt("length"),
                        seatObj.getInt("width")
                );
                if (seat.getZIndex() == 1) {
                    upperDeckSeats.add(seat);
                    maxRowUpper = Math.max(maxRowUpper, seat.getRow());
                    maxColUpper = Math.max(maxColUpper, seat.getColumn());
                } else {
                    lowerDeckSeats.add(seat);
                    maxRowLower = Math.max(maxRowLower, seat.getRow());
                    maxColLower = Math.max(maxColLower, seat.getColumn());
                }
            }

            upperGrid = createVerticalGrid(upperDeckSeats, maxRowUpper, maxColUpper, false); // No driver in upper deck
            lowerGrid = createVerticalGrid(lowerDeckSeats, maxRowLower, maxColLower, true);  // Driver in lower deck
            runOnUiThread(() -> {
                upperDeckButton.setEnabled(!upperGrid.isEmpty());
                lowerDeckButton.setEnabled(!lowerGrid.isEmpty());
                if (upperGrid.isEmpty() && lowerGrid.isEmpty()) {
                    Toast.makeText(this, "No seats available for this trip", Toast.LENGTH_SHORT).show();
                    seatRecyclerView.setVisibility(GONE);
                    deckLabel.setVisibility(GONE);
                } else {
                    lowerDeckButton.setBackgroundResource(R.drawable.btn_lower_active);
                    lowerDeckButton.setTextColor(getResources().getColor(R.color.white));
                    Toast.makeText(this, "No seats available for this trip", Toast.LENGTH_SHORT).show();
                    upperDeckButton.setBackgroundResource(R.drawable.btn_inactive);
                    upperDeckButton.setTextColor(getResources().getColor(R.color.gray));
                    showLowerDeck();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error parsing seat layout: " + e.getMessage(), e);
            runOnUiThread(() -> Toast.makeText(this, "Error parsing seats: " + e.getMessage(), Toast.LENGTH_LONG).show());
        }
    }
    private List<Seat> createVerticalGrid(List<Seat> seats, int maxRow, int maxCol, boolean includeDriver) {
        List<Seat> grid = new ArrayList<>();
        int gridWidth = maxCol + 1; // Number of columns
        int gridHeight = maxRow + 1; // Number of rows

        // Initialize grid with null values
        for (int i = 0; i < gridWidth * gridHeight; i++) {
            grid.add(null);
        }

        // Populate seats from the server response
        for (Seat seat : seats) {
            int col = seat.getColumn();
            int row = seat.getRow();
            int reversedRow = maxRow - row;
            int index = col * gridHeight + reversedRow;
            if (index >= 0 && index < grid.size()) {
                grid.set(index, seat);
            } else {
                Log.w(TAG, "Seat " + seat.getName() + " index " + index + " out of bounds (size: " + grid.size() + ")");
            }
        }

        return grid;
    }

    private void showUpperDeck() {
        if (!upperGrid.isEmpty()) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, maxRowUpper + 1);
            layoutManager.setOrientation(GridLayoutManager.VERTICAL);
            seatRecyclerView.setLayoutManager(layoutManager);
            seatAdapter = new SeatAdapter(upperGrid, this::onSeatSelected, seatSize);
            seatRecyclerView.setAdapter(seatAdapter);
            driverImage.setVisibility(GONE);
            deckLabel.setText("Upper Deck");
            seatRecyclerView.setVisibility(VISIBLE);
            deckLabel.setVisibility(VISIBLE);
            upperDeckButton.setEnabled(false);
            lowerDeckButton.setEnabled(true);
            currentDeck = "Upper"; // Set current deck
        } else {
            Toast.makeText(this, "No upper deck seats available", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLowerDeck() {
        if (!lowerGrid.isEmpty()) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, maxRowLower + 1);
            layoutManager.setOrientation(GridLayoutManager.VERTICAL);
            seatRecyclerView.setLayoutManager(layoutManager);
            seatAdapter = new SeatAdapter(lowerGrid, this::onSeatSelected, seatSize);
            seatRecyclerView.setAdapter(seatAdapter);
            deckLabel.setText("Lower Deck");

            driverImage.setVisibility(VISIBLE);
            seatRecyclerView.setVisibility(VISIBLE);
            deckLabel.setVisibility(VISIBLE);
            upperDeckButton.setEnabled(true);
            lowerDeckButton.setEnabled(false);
            currentDeck = "Lower"; // Set current deck
        } else {
            Toast.makeText(this, "No lower deck seats available", Toast.LENGTH_SHORT).show();
        }
    }

    private void onSeatSelected(Seat seat) {
        TripDataHolder dataHolder = TripDataHolder.getInstance();
        if (seat != null && seat.isAvailable() && !seat.isDriverSeat()) { // Exclude driver seat
            double fare = Double.parseDouble(seat.getFare());
            String seatName = seat.getName();
            SelectedSeat selectedSeat = new SelectedSeat(seatName, currentDeck);

            // Get the current lists from TripDataHolder
            List<Boolean> seatAvailability = dataHolder.getSeatAvailability();
            List<Boolean> ladiesSeats = dataHolder.getLadiesSeats();
            List<Boolean> malesSeats = dataHolder.getMalesSeats();
            Log.d(TAG, "Selected Seat: " + seatName +
                    ", Available: " + seat.isAvailable() +
                    ", LadiesSeat: " + seat.isLadiesSeat() +
                    ", MalesSeat: " + seat.isMalesSeat());

            if (seat.isSelected()) {
                // Seat is selected
                selectedSeatCount++;
                totalFare += fare;
                selectedSeats.add(selectedSeat); // Add seat with deck info

                // Add seat details to TripDataHolder
                seatAvailability.add(seat.isAvailable());
                ladiesSeats.add(seat.isLadiesSeat());
                malesSeats.add(seat.isMalesSeat());

                Toast.makeText(this, "Selected " + currentDeck + " Deck seat: " + seatName + " (₹" + seat.getFare() + ")", Toast.LENGTH_SHORT).show();
            } else {
                // Seat is deselected
                selectedSeatCount--;
                totalFare -= fare;
                selectedSeats.remove(selectedSeat); // Remove seat with deck info

                // Remove seat details from TripDataHolder (assuming order matches selectedSeats)
                int index = selectedSeats.size(); // Index of the last seat before removal
                if (index < seatAvailability.size()) {
                    seatAvailability.remove(index);
                    ladiesSeats.remove(index);
                    malesSeats.remove(index);
                }

                Toast.makeText(this, "Deselected " + currentDeck + " Deck seat: " + seatName, Toast.LENGTH_SHORT).show();
            }

            // Update TripDataHolder with the current lists
            dataHolder.setSelectedSeats(new ArrayList<>(selectedSeats));
            dataHolder.setSeatAvailability(seatAvailability);
            dataHolder.setLadiesSeats(ladiesSeats);
            dataHolder.setMalesSeats(malesSeats);
            dataHolder.setPassengercount(selectedSeatCount);
            dataHolder.setTotalAmount(totalFare);

            // Update UI
            totalAmount.setText("₹" + totalFare);
            passengerCount.setText(selectedSeatCount + " seat(s) selected |");
            footerLayout.setVisibility(selectedSeatCount > 0 ? VISIBLE : GONE);
        } else if (seat != null && !seat.isDriverSeat()) {
            Toast.makeText(this, "Seat " + seat.getName() + " is not available", Toast.LENGTH_SHORT).show();
        }
    }




        // ... (existing code)


        // ... (existing code)

        @SuppressLint("ClickableViewAccessibility")
        private void showSeatInfoPopup() {
            // Create the popup layout programmatically
            LinearLayout popupLayout = new LinearLayout(this);
            popupLayout.setOrientation(LinearLayout.VERTICAL);
            popupLayout.setBackgroundColor(Color.WHITE);
            popupLayout.setPadding(dpToPx(20), dpToPx(20), dpToPx(20), dpToPx(20));
            popupLayout.setElevation(10);

            // Add title
            TextView title = new TextView(this);
            title.setText("Seat Information");
            title.setTextSize(18);
            title.setTextColor(Color.BLACK);
            title.setTypeface(null, android.graphics.Typeface.BOLD);
            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            titleParams.gravity = android.view.Gravity.CENTER_HORIZONTAL;
            titleParams.setMargins(0, 0, 0, dpToPx(10));
            title.setLayoutParams(titleParams);
            popupLayout.addView(title);

            // Horizontal ScrollView to ensure all seats are visible
            HorizontalScrollView scrollView = new HorizontalScrollView(this);
            LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            scrollView.setLayoutParams(scrollParams);

            // Vertical layout for seat info (to stack rows if needed, but we'll use one row)
            LinearLayout seatRow = new LinearLayout(this);
            seatRow.setOrientation(LinearLayout.VERTICAL);
            seatRow.setGravity(android.view.Gravity.CENTER);

            // Horizontal layout for seat items
            LinearLayout seatItemsContainer = new LinearLayout(this);
            seatItemsContainer.setOrientation(LinearLayout.VERTICAL);
            seatItemsContainer.setGravity(android.view.Gravity.CENTER);

            // Define seat types using a simple SeatInfo class for clarity
            class SeatInfo {
                String label;
                int imageResId;

                SeatInfo(String label, int imageResId) {
                    this.label = label;
                    this.imageResId = imageResId;
                }
            }

            // List of seat types
            SeatInfo[] seatInfos = {
                    new SeatInfo("Ladies Sleeper Available", R.drawable.lad_sleeper_avl),
                    new SeatInfo("Ladies Sleeper Unavailable", R.drawable.lad_sleeper_bkd),
                    new SeatInfo("Sleeper Available", R.drawable.sleeper_aval),
                    new SeatInfo("Sleeper Unavailable", R.drawable.sleeper_unavailable),
                    new SeatInfo("Seater Available", R.drawable.seat_avl),
                    new SeatInfo("Seater Unavailable", R.drawable.seat_bkd)
            };

            // Add each seat type to the horizontal layout
            for (SeatInfo seatInfo : seatInfos) {
                LinearLayout seatItem = new LinearLayout(this);
                seatItem.setOrientation(LinearLayout.HORIZONTAL);
                seatItem.setGravity(android.view.Gravity.CENTER);
                seatItem.setPadding(dpToPx(10), dpToPx(5), dpToPx(10), dpToPx(5)); // Consistent padding

                // Seat image
                ImageView seatImage = new ImageView(this);
                seatImage.setImageResource(seatInfo.imageResId);
                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(dpToPx(40), dpToPx(40));
                imageParams.setMargins(dpToPx(5), 0, dpToPx(10), 0); // Margin between image and text
                seatImage.setLayoutParams(imageParams);
                seatItem.addView(seatImage);

                // Seat label
                TextView seatText = new TextView(this);
                seatText.setText(seatInfo.label);
                seatText.setTextSize(12);
                seatText.setTextColor(Color.BLACK);
                seatText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                seatText.setMaxWidth(dpToPx(80)); // Fixed max width to prevent wrapping
                seatText.setLines(2); // Allow two lines if needed
                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                textParams.gravity = android.view.Gravity.CENTER_VERTICAL;
                seatText.setLayoutParams(textParams);
                seatItem.addView(seatText);

                seatItemsContainer.addView(seatItem);
            }

            seatRow.addView(seatItemsContainer);
            scrollView.addView(seatRow);
            popupLayout.addView(scrollView);

            // Create and show the PopupWindow
            PopupWindow popupWindow = new PopupWindow(popupLayout, dpToPx(300), LinearLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAsDropDown(btn_info, 0, dpToPx(10));

            // Dismiss popup when clicking outside
            popupLayout.setOnTouchListener((v, event) -> {
                popupWindow.dismiss();
                return true;
            });
        }

        // Helper method to convert dp to pixels
        private int dpToPx(int dp) {
            float density = getResources().getDisplayMetrics().density;
            return Math.round(dp * density);
        }

        // ... (rest of your existing code)
    }

        // Helper method to convert dp to pixels




















