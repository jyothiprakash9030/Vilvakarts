package com.example.vcartbusbooking;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.vcartbusbooking.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BusDetailsActivity extends AppCompatActivity {
    TextView fromdata, todata, dateview, no_buses_message;
    private static final String TAG = "API_RESPONSE";
    private RecyclerView recyclerView;
    private LottieAnimationView loadingAnimationView;
    private FrameLayout loaderContainer;
    private List<Trip> allTrips = new ArrayList<>();
    private List<Trip> filteredTrips = new ArrayList<>();
    private TripAdapter tripAdapter;
    private CheckBox acCheckbox;
    private CheckBox sleeperCheckbox;
    private Map<String, Boolean> departureTimeFilters = new HashMap<>();
    private Map<String, Boolean> arrivalTimeFilters = new HashMap<>();
    private TreeSet<String> travelNames = new TreeSet<>(); // For sorted travel names
    private String searchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);

        EdgeToEdge.enable(this);

        getWindow().setStatusBarColor(Color.WHITE);

        getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );

        no_buses_message = findViewById(R.id.no_buses_message);
        fromdata = findViewById(R.id.from);
        todata = findViewById(R.id.to);
        dateview = findViewById(R.id.datevisible);

        recyclerView = findViewById(R.id.recyclerView);
        acCheckbox = findViewById(R.id.ac_checkbox);
        sleeperCheckbox = findViewById(R.id.sleeper);
        loadingAnimationView = findViewById(R.id.loadingAnimationView);
        loaderContainer = findViewById(R.id.loaderContainer);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripAdapter = new TripAdapter(filteredTrips);
        recyclerView.setAdapter(tripAdapter);

        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        String date = getIntent().getStringExtra("textDate1");

        TripDataHolder dataHolder = TripDataHolder.getInstance();
        dataHolder.setFrom(from);
        dataHolder.setTo(to);
        dataHolder.setDate(date);

        todata.setText(to);
        dateview.setText(date);
        fromdata.setText(from);

        ImageView Bus_details_backarrow = findViewById(R.id.Bus_details_backarrow);
        Bus_details_backarrow.setOnClickListener(view -> {
            Intent i = new Intent(BusDetailsActivity.this, SourceAndDestination.class);
            startActivity(i);
        });

        acCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> Log.d(TAG, "AC Checkbox: " + isChecked));
        sleeperCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> Log.d(TAG, "Sleeper Checkbox: " + isChecked));

        departureTimeFilters.put("Morning", false);
        departureTimeFilters.put("Afternoon", false);
        departureTimeFilters.put("Evening", false);
        departureTimeFilters.put("Night", false);

        arrivalTimeFilters.put("Morning", false);
        arrivalTimeFilters.put("Afternoon", false);
        arrivalTimeFilters.put("Evening", false);
        arrivalTimeFilters.put("Night", false);

        Button filter = findViewById(R.id.filter);
        filter.setOnClickListener(v -> {
            LinearLayout mainContainer = new LinearLayout(BusDetailsActivity.this);
            mainContainer.setOrientation(LinearLayout.HORIZONTAL);
            mainContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;
            int popupHeight = screenHeight / 2;

            GradientDrawable backgroundDrawable = new GradientDrawable();
            backgroundDrawable.setColor(Color.WHITE);
            backgroundDrawable.setCornerRadius(dpToPx(22));
            mainContainer.setBackground(backgroundDrawable);

            LinearLayout leftContainer = new LinearLayout(BusDetailsActivity.this);
            leftContainer.setOrientation(LinearLayout.VERTICAL);
            leftContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));

            ScrollView scrollView = new ScrollView(BusDetailsActivity.this);
            scrollView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            LinearLayout popupLayout = new LinearLayout(BusDetailsActivity.this);
            popupLayout.setOrientation(LinearLayout.VERTICAL);
            popupLayout.setPadding(20, 20, 20, 20);

            Map<String, Boolean> tempDepartureTimeFilters = new HashMap<>(departureTimeFilters);
            Map<String, Boolean> tempArrivalTimeFilters = new HashMap<>(arrivalTimeFilters);
            boolean[] tempACChecked = {acCheckbox.isChecked()};
            boolean[] tempSleeperChecked = {sleeperCheckbox.isChecked()};

            TextView searchTitle = new TextView(BusDetailsActivity.this);
            searchTitle.setText("Search Bus Name");
            searchTitle.setTextSize(12);
            searchTitle.setTextColor(Color.BLACK);
            searchTitle.setPadding(0, 0, 0, 10);
            popupLayout.addView(searchTitle);

            EditText searchEditText = new EditText(BusDetailsActivity.this);
            searchEditText.setHint("Enter or select bus name");
            searchEditText.setTextSize(14);
            searchEditText.setBackgroundResource(R.drawable.rounded_input_background);
            searchEditText.setTextColor(Color.BLACK);
            searchEditText.setPadding(30, 20, 10, 20);
            searchEditText.setText(searchQuery);
            popupLayout.addView(searchEditText);

            PopupWindow[] currentPopup = {null};

            searchEditText.setOnFocusChangeListener((v1, hasFocus) -> {
                if (hasFocus && (currentPopup[0] == null || !currentPopup[0].isShowing())) {
                    String currentText = searchEditText.getText().toString().trim().toLowerCase();
                    currentPopup[0] = showTravelNamesPopup(searchEditText, currentText);
                }
            });

            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    String query = s.toString().trim().toLowerCase();
                    if (currentPopup[0] != null && currentPopup[0].isShowing()) {
                        currentPopup[0].dismiss();
                    }
                    currentPopup[0] = showTravelNamesPopup(searchEditText, query);
                }
            });

            addSectionToPopup(popupLayout, "Seat Type", new String[]{"AC", "NON AC", "SLEEPER", "SEATER"},
                    tempACChecked, tempSleeperChecked);
            addSectionToPopup(popupLayout, "Special", new String[]{"CHALLENGED/SENIORS", "WOMEN"}, null, null);
            addSectionToPopup(popupLayout, "Departure Time", new String[]{
                    "Morning (6:00 AM - 12:00 PM)",
                    "Afternoon (12:00 PM - 4:00 PM)",
                    "Evening (4:00 PM - 8:00 PM)",
                    "Night (8:00 PM - 6:00 AM)"
            }, tempDepartureTimeFilters, null);
            addSectionToPopup(popupLayout, "Arrival Time", new String[]{
                    "Morning (6:00 AM - 12:00 PM)",
                    "Afternoon (12:00 PM - 4:00 PM)",
                    "Evening (4:00 PM - 8:00 PM)",
                    "Night (8:00 PM - 6:00 AM)"
            }, tempArrivalTimeFilters, null);
            addArrowSection(popupLayout, "Based on Points");

            scrollView.addView(popupLayout);
            leftContainer.addView(scrollView);

            LinearLayout rightContainer = new LinearLayout(BusDetailsActivity.this);
            rightContainer.setOrientation(LinearLayout.VERTICAL);
            rightContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    0, popupHeight, 1.0f));
            rightContainer.setPadding(20, 20, 20, 20);
            rightContainer.setGravity(Gravity.BOTTOM);

            Button applyButton = new Button(BusDetailsActivity.this);
            applyButton.setText("Apply");
            applyButton.setBackgroundColor(Color.RED);
            applyButton.setTextColor(Color.WHITE);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    dpToPx(40));
            params.gravity = Gravity.CENTER;
            applyButton.setLayoutParams(params);

            applyButton.setOnClickListener(v1 -> {
                acCheckbox.setChecked(tempACChecked[0]);
                sleeperCheckbox.setChecked(tempSleeperChecked[0]);
                departureTimeFilters.clear();
                departureTimeFilters.putAll(tempDepartureTimeFilters);
                arrivalTimeFilters.clear();
                arrivalTimeFilters.putAll(tempArrivalTimeFilters);
                searchQuery = searchEditText.getText().toString().trim().toLowerCase();
                filterTrips();
                PopupWindow popupWindow = (PopupWindow) v1.getTag();
                if (popupWindow != null) popupWindow.dismiss();
            });

            rightContainer.addView(applyButton);

            mainContainer.addView(leftContainer);
            mainContainer.addView(rightContainer);

            PopupWindow popupWindow = new PopupWindow(mainContainer, screenWidth, popupHeight, true);
            popupWindow.setElevation(20);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            popupWindow.showAtLocation(filter, Gravity.BOTTOM, 0, 0);
            applyButton.setTag(popupWindow);
        });

        Button sort = findViewById(R.id.sort);
        sort.setOnClickListener(v -> {
            LinearLayout popupLayout = new LinearLayout(BusDetailsActivity.this);
            popupLayout.setOrientation(LinearLayout.VERTICAL);
            popupLayout.setBackgroundColor(Color.WHITE);
            popupLayout.setPadding(20, 20, 20, 20);
            popupLayout.setElevation(10);

            String[] labels = {"Low price-First", "High price-First", "Departure Earlier", "Departure Last"};
            CheckBox[] checkBoxes = new CheckBox[labels.length];

            for (int i = 0; i < labels.length; i++) {
                LinearLayout row = new LinearLayout(BusDetailsActivity.this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setGravity(Gravity.CENTER_VERTICAL);
                row.setPadding(5, 10, 5, 10);

                checkBoxes[i] = new CheckBox(BusDetailsActivity.this);
                LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                checkBoxParams.setMargins(10, 0, 20, 0);
                checkBoxes[i].setLayoutParams(checkBoxParams);

                TextView textView = new TextView(BusDetailsActivity.this);
                textView.setText(labels[i]);
                textView.setTextSize(14);
                textView.setTextColor(Color.BLACK);

                row.addView(checkBoxes[i]);
                row.addView(textView);
                popupLayout.addView(row);
            }

            Button applyButton = new Button(BusDetailsActivity.this);
            applyButton.setText("Apply");
            applyButton.setTextColor(Color.WHITE);
            applyButton.setTextSize(12);

            GradientDrawable buttonBackground = new GradientDrawable();
            buttonBackground.setColor(Color.RED);
            buttonBackground.setCornerRadius(32);
            applyButton.setBackground(buttonBackground);

            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(dpToPx(80), dpToPx(40));
            buttonParams.gravity = Gravity.CENTER_HORIZONTAL;
            buttonParams.setMargins(80, 20, 0, 20);
            buttonParams.setMarginStart(50);
            applyButton.setLayoutParams(buttonParams);

            applyButton.setOnClickListener(v1 -> {
                List<Trip> sortedTrips = new ArrayList<>(filteredTrips);

                if (checkBoxes[3].isChecked()) {
                    Collections.sort(sortedTrips, Comparator.comparingInt(Trip::getDepartureMinutes).reversed());
                } else if (checkBoxes[2].isChecked()) {
                    Collections.sort(sortedTrips, Comparator.comparingInt(Trip::getDepartureMinutes));
                } else if (checkBoxes[1].isChecked()) {
                    Collections.sort(sortedTrips, Comparator.comparingDouble(Trip::getLowestBaseFare).reversed());
                } else if (checkBoxes[0].isChecked()) {
                    Collections.sort(sortedTrips, Comparator.comparingDouble(Trip::getLowestBaseFare));
                }

                filteredTrips.clear();
                filteredTrips.addAll(sortedTrips);

                runOnUiThread(() -> {
                    tripAdapter.notifyDataSetChanged();
                    if (filteredTrips.isEmpty()) {
                        no_buses_message.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        no_buses_message.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });

                PopupWindow popupWindow = (PopupWindow) v1.getTag();
                if (popupWindow != null) popupWindow.dismiss();
            });

            popupLayout.addView(applyButton);
            PopupWindow popupWindow = new PopupWindow(popupLayout, dpToPx(200), LinearLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAsDropDown(sort, 0, 10);
            applyButton.setTag(popupWindow);
        });

        sendPostRequest();
    }

    private void sendPostRequest() {
        String fromCityId = getIntent().getStringExtra("fromCityId");
        String date_of_journey = getIntent().getStringExtra("textDate1");
        String toCityId = getIntent().getStringExtra("toCityId");

        Log.d(TAG, "fromCityId: " + fromCityId);
        Log.d(TAG, "toCityId: " + toCityId);
        Log.d(TAG, "date_of_journey: " + date_of_journey);

        if (fromCityId == null || toCityId == null || date_of_journey == null) {
            Log.e(TAG, "Missing intent extras");
            return;
        }

        TripDataHolder.getInstance().setFromCityId(Integer.parseInt(fromCityId));
        TripDataHolder.getInstance().setToCityId(Integer.parseInt(toCityId));

        runOnUiThread(() -> {
            loaderContainer.setVisibility(View.VISIBLE);
            loadingAnimationView.playAnimation();
        });

        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("application/json");

                String jsonBody = "{\n" +
                        " \"source_id\":" + fromCityId + ",\n" +
                        " \"destination_id\":" + toCityId + ",\n" +
                        " \"date_of_journey\":\"" + date_of_journey + "\"\n" +
                        "}";
                Log.d(TAG, "Request Body: " + jsonBody);

                RequestBody body = RequestBody.create(mediaType, jsonBody);
                Request request = new Request.Builder()
                        .url(Constants.BASE_URL + "getAvailableTrips")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer pZQpIV1kLqkBXmltpLTrfQ17m3xJiukhzW3PkroV")
                        .build();

                Response response = client.newCall(request).execute();
                Log.d(TAG, "Response Code: " + response.code());

                if (!response.isSuccessful()) {
                    Log.e(TAG, "Server error: " + response.code() + " " + response.message());
                    runOnUiThread(() -> {
                        loadingAnimationView.cancelAnimation();
                        loaderContainer.setVisibility(View.GONE);
                    });
                    return;
                }

                if (response.body() != null) {
                    String responseData = response.body().string();
                    Log.d(TAG, "Full API Response: " + responseData);
                    parseResponse(responseData);
                } else {
                    Log.e(TAG, "Response body is null");
                    runOnUiThread(() -> {
                        loadingAnimationView.cancelAnimation();
                        loaderContainer.setVisibility(View.GONE);
                    });
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception in request: " + e.getMessage(), e);
                runOnUiThread(() -> {
                    loadingAnimationView.cancelAnimation();
                    loaderContainer.setVisibility(View.GONE);
                });
            }
        }).start();
    }

    private void parseResponse(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            Log.d(TAG, "JSON Object: " + jsonObject.toString(2));

            if (!jsonObject.has("data")) {
                Log.e(TAG, "No 'data' field in response");
                runOnUiThread(() -> {
                    loadingAnimationView.cancelAnimation();
                    loaderContainer.setVisibility(View.GONE);
                });
                return;
            }

            JSONObject dataObject = jsonObject.getJSONObject("data");
            if (!dataObject.has("availableTrips")) {
                Log.e(TAG, "No 'availableTrips' field in data");
                runOnUiThread(() -> {
                    loadingAnimationView.cancelAnimation();
                    loaderContainer.setVisibility(View.GONE);

                    // Find the TextView and update it
                    TextView errorMessage = findViewById(R.id.no_buses_message);
                    errorMessage.setText("No buses available on this route");
                    errorMessage.setVisibility(View.VISIBLE);
                });
                return;
            }

            JSONArray dataArray = dataObject.getJSONArray("availableTrips");
            Log.d(TAG, "Data Array Length: " + dataArray.length());

            allTrips.clear();
            filteredTrips.clear();
            travelNames.clear();

            for (int i = 0; i < dataArray.length(); i++) {
                try {
                    JSONObject tripObj = dataArray.getJSONObject(i);
                    Log.d(TAG, "Parsing trip " + i + ": " + tripObj.toString(2));

                    String travelName = tripObj.optString("travels", "Unknown");
                    travelNames.add(travelName);

                    String departureTime = convertToTime(tripObj.optString("departureTime", "0"));
                    String arrivalTime = convertToTime(tripObj.optString("arrivalTime", "0"));
                    int departureMinutes = tripObj.optInt("departureTime", 0);
                    int arrivalMinutes = tripObj.optInt("arrivalTime", 0);

                    String lowestBaseFare = "N/A";
                    if (tripObj.has("fareDetails")) {
                        JSONArray fareDetailsArray = tripObj.getJSONArray("fareDetails");
                        double minFare = Double.MAX_VALUE;

                        String serviceTaxAbsolute = "0.00";

                        for (int j = 0; j < fareDetailsArray.length(); j++) {
                            JSONObject fareDetails = fareDetailsArray.getJSONObject(j);

                            double baseFare = Double.parseDouble(fareDetails.optString("baseFare", "0.00"));
                            if (baseFare < minFare) {
                                minFare = baseFare;
                                lowestBaseFare = String.format("%.2f", baseFare);
                            }

                            serviceTaxAbsolute = fareDetails.optString("serviceTaxAbsolute", "0.00");

                            TripDataHolder dataHolder = TripDataHolder.getInstance();
                            dataHolder.getServiceTax();
                        }
                    }

                    Trip trip = new Trip(
                            tripObj.optString("id", "N/A"),
                            travelName,
                            tripObj.optString("busType", "Unknown"),
                            tripObj.optString("busRoutes", ""),
                            departureTime,
                            arrivalTime,
                            tripObj.optString("duration", "N/A"),
                            lowestBaseFare,
                            tripObj.optInt("availableSeats", 0)
                    );
                    trip.setAC(tripObj.optBoolean("AC", false));
                    trip.setSleeper(tripObj.optBoolean("sleeper", false));
                    trip.setDepartureMinutes(departureMinutes);
                    trip.setArrivalMinutes(arrivalMinutes);
                    allTrips.add(trip);
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing individual trip " + i + ": " + e.getMessage(), e);
                }
            }

            filteredTrips.addAll(allTrips);
            runOnUiThread(() -> {
                loadingAnimationView.cancelAnimation();
                loaderContainer.setVisibility(View.GONE);
                if (allTrips.isEmpty()) {
                    no_buses_message.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    no_buses_message.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                tripAdapter.notifyDataSetChanged();
                Log.d(TAG, "Initial filtered trips count: " + filteredTrips.size());
            });
        } catch (Exception e) {
            Log.e(TAG, "JSON Parsing error: " + e.getMessage(), e);
            runOnUiThread(() -> {
                loadingAnimationView.cancelAnimation();
                loaderContainer.setVisibility(View.GONE);
            });
        }
    }

    private String convertToTime(String minutesStr) {
        try {
            int totalMinutes = Integer.parseInt(minutesStr);
            int hours = totalMinutes / 60 % 24;
            int minutes = totalMinutes % 60;
            return String.format("%02d:%02d", hours, minutes);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Error converting time: " + minutesStr, e);
            return "N/A";
        }
    }

    private void filterTrips() {
        filteredTrips.clear();
        boolean anyFilterActive = acCheckbox.isChecked() || sleeperCheckbox.isChecked() ||
                departureTimeFilters.containsValue(true) || arrivalTimeFilters.containsValue(true) ||
                !searchQuery.isEmpty();

        if (!anyFilterActive) {
            filteredTrips.addAll(allTrips);
        } else {
            for (Trip trip : allTrips) {
                boolean matchesAC = !acCheckbox.isChecked() || trip.isAC();
                boolean matchesSleeper = !sleeperCheckbox.isChecked() || trip.isSleeper();
                boolean matchesDepartureTime = true;
                boolean matchesArrivalTime = true;
                boolean matchesSearch = searchQuery.isEmpty() ||
                        trip.getTravels().toLowerCase().contains(searchQuery);

                if (departureTimeFilters.containsValue(true)) {
                    int minutes = trip.getDepartureMinutes();
                    matchesDepartureTime = false;
                    if (departureTimeFilters.get("Morning") && minutes >= 360 && minutes < 720) matchesDepartureTime = true;
                    else if (departureTimeFilters.get("Afternoon") && minutes >= 720 && minutes < 960) matchesDepartureTime = true;
                    else if (departureTimeFilters.get("Evening") && minutes >= 960 && minutes < 1200) matchesDepartureTime = true;
                    else if (departureTimeFilters.get("Night") && (minutes >= 1200 || minutes < 360)) matchesDepartureTime = true;
                }

                if (arrivalTimeFilters.containsValue(true)) {
                    int minutes = trip.getArrivalMinutes();
                    matchesArrivalTime = false;
                    if (arrivalTimeFilters.get("Morning") && minutes >= 360 && minutes < 720) matchesArrivalTime = true;
                    else if (arrivalTimeFilters.get("Afternoon") && minutes >= 720 && minutes < 960) matchesArrivalTime = true;
                    else if (arrivalTimeFilters.get("Evening") && minutes >= 960 && minutes < 1200) matchesArrivalTime = true;
                    else if (arrivalTimeFilters.get("Night") && (minutes >= 1200 || minutes < 360)) matchesArrivalTime = true;
                }

                if (matchesAC && matchesSleeper && matchesDepartureTime && matchesArrivalTime && matchesSearch) {
                    filteredTrips.add(trip);
                }
            }
        }

        runOnUiThread(() -> {
            tripAdapter.notifyDataSetChanged();
            if (filteredTrips.isEmpty()) {
                no_buses_message.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                no_buses_message.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            Log.d(TAG, "Filtered trips count: " + filteredTrips.size());
        });
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void addSectionToPopup(LinearLayout parentLayout, String sectionTitle, String[] options,
                                   Object extraData1, Object extraData2) {
        TextView title = new TextView(this);
        title.setText(sectionTitle);
        title.setTextSize(14);
        title.setTextColor(Color.BLACK);
        title.setPadding(0, 0, 0, 10);
        parentLayout.addView(title);

        for (String option : options) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(option);
            checkBox.setTextSize(12);
            checkBox.setTextColor(Color.BLACK);

            if (extraData1 != null) {
                if (sectionTitle.equals("Departure Time") && extraData1 instanceof Map) {
                    Map<String, Boolean> tempFilters = (Map<String, Boolean>) extraData1;
                    String period = option.split(" ")[0];
                    checkBox.setChecked(tempFilters.getOrDefault(period, false));
                    checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> tempFilters.put(period, isChecked));
                } else if (sectionTitle.equals("Arrival Time") && extraData1 instanceof Map) {
                    Map<String, Boolean> tempFilters = (Map<String, Boolean>) extraData1;
                    String period = option.split(" ")[0];
                    checkBox.setChecked(tempFilters.getOrDefault(period, false));
                    checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> tempFilters.put(period, isChecked));
                } else if (sectionTitle.equals("Seat Type")) {
                    if ("AC".equals(option) && extraData1 instanceof boolean[]) {
                        boolean[] tempAC = (boolean[]) extraData1;
                        checkBox.setChecked(tempAC[0]);
                        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> tempAC[0] = isChecked);
                    } else if ("SLEEPER".equals(option) && extraData2 instanceof boolean[]) {
                        boolean[] tempSleeper = (boolean[]) extraData2;
                        checkBox.setChecked(tempSleeper[0]);
                        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> tempSleeper[0] = isChecked);
                    }
                }
            }
            parentLayout.addView(checkBox);
        }
    }

    private void addArrowSection(LinearLayout parentLayout, String sectionTitle) {
        TextView title = new TextView(this);
        title.setText(sectionTitle);
        title.setTextSize(14);
        title.setTextColor(Color.BLACK);
        title.setPadding(0, 0, 0, 10);
        parentLayout.addView(title);

        addArrowRow(parentLayout, "Boarding Points", R.drawable.baseline_arrow_outward_24);
        addArrowRow(parentLayout, "Dropping Points", R.drawable.down_side_arrow);
    }

    private void addArrowRow(LinearLayout parentLayout, String optionText, int arrowDrawable) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(5, 10, 5, 10);

        ImageView arrowIcon = new ImageView(this);
        arrowIcon.setImageResource(arrowDrawable);
        arrowIcon.setLayoutParams(new LinearLayout.LayoutParams(90, 100));
        arrowIcon.setPadding(10, 0, 20, 0);
        if ("Boarding Points".equals(optionText)) arrowIcon.setColorFilter(Color.RED);

        TextView textView = new TextView(this);
        textView.setText(optionText);
        textView.setTextSize(14);
        textView.setTextColor(Color.BLACK);

        row.addView(arrowIcon);
        row.addView(textView);
        parentLayout.addView(row);
    }

    private PopupWindow showTravelNamesPopup(EditText searchEditText, String query) {
        LinearLayout popupLayout = new LinearLayout(BusDetailsActivity.this);
        popupLayout.setOrientation(LinearLayout.VERTICAL);
        popupLayout.setBackgroundColor(Color.WHITE);
        popupLayout.setPadding(20, 20, 20, 20);
        popupLayout.setElevation(10);

        ScrollView scrollView = new ScrollView(BusDetailsActivity.this);
        LinearLayout scrollContent = new LinearLayout(BusDetailsActivity.this);
        scrollContent.setOrientation(LinearLayout.VERTICAL);

        for (String travelName : travelNames) {
            if (query.isEmpty() || travelName.toLowerCase().contains(query)) {
                TextView travelText = new TextView(BusDetailsActivity.this);
                travelText.setText(travelName);
                travelText.setTextSize(14);
                travelText.setTextColor(Color.BLACK);
                travelText.setPadding(20, 10, 0, 10);
                travelText.setOnClickListener(v -> {
                    searchEditText.setText(travelName);
                    PopupWindow travelPopup = (PopupWindow) v.getTag();
                    if (travelPopup != null) travelPopup.dismiss();
                });
                scrollContent.addView(travelText);
            }
        }

        if (scrollContent.getChildCount() == 0 && !query.isEmpty()) {
            TextView noResultsText = new TextView(BusDetailsActivity.this);
            noResultsText.setText("No matching bus names found");
            noResultsText.setTextSize(14);
            noResultsText.setTextColor(Color.GRAY);
            noResultsText.setPadding(20, 10, 0, 10);
            scrollContent.addView(noResultsText);
        }

        scrollView.addView(scrollContent);
        popupLayout.addView(scrollView);

        PopupWindow travelPopup = new PopupWindow(popupLayout, dpToPx(200), dpToPx(300), true);
        travelPopup.setClippingEnabled(false);

        int xOffset = dpToPx(200);
        int yOffset = -dpToPx(50);
        travelPopup.showAsDropDown(searchEditText, xOffset, yOffset);

        if (scrollContent.getChildCount() > 0) {
            scrollContent.getChildAt(0).setTag(travelPopup);
        }

        return travelPopup;
    }
}