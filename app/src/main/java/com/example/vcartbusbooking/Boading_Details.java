package com.example.vcartbusbooking;
import static com.airbnb.lottie.L.TAG;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import com.example.vcartbusbooking.R;
import com.example.vcartbusbooking.utils.Constants;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class Boading_Details extends AppCompatActivity {
    private RadioGroup radioGroup;
    private OkHttpClient client;
    private String selectedBpId;
    private Button proceed_button;
    private ProgressBar progressBar;
    private Toast toast;
    private LinearLayout proceedLayout; // Reference to the LinearLayout


    TextView boarding_point_from,boarding_point_to,boarding_point_Date,bus_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boading_details);
        radioGroup = findViewById(R.id.radioGroupBoardingTimes);
        progressBar = findViewById(R.id.progressBar);
        client = new OkHttpClient().newBuilder().build();
        proceed_button = findViewById(R.id.proceed_button);
        proceedLayout = findViewById(R.id.proceed_layout); // Add ID to LinearLayout in XML
        proceed_button.setEnabled(false); // Initially disable the button
        proceedLayout.setVisibility(View.GONE); // Initially hide the LinearLayout

        boarding_point_from=findViewById(R.id.boarding_point_from);
        boarding_point_to=findViewById(R.id.boarding_point_to);
        boarding_point_Date=findViewById(R.id.boarding_point_Date);
        bus_name=findViewById(R.id.bus_name);
        TripDataHolder dataHolder = TripDataHolder.getInstance();
        String busType = dataHolder.getTravels();
        String date = dataHolder.getDate();
        String from = dataHolder.getFrom();
        String to = dataHolder.getTo();
        boarding_point_from.setText(from);
        boarding_point_to.setText(to);
        boarding_point_Date.setText(date);
        bus_name.setText(busType);
        fetchBoardingTimes();
        proceed_button.setOnClickListener(view -> {
            Log.d("DEBUG", "Proceed button clicked, selectedBpId: " + selectedBpId);
            if (selectedBpId == null || selectedBpId.trim().isEmpty()) {
                Log.d("DEBUG", "No boarding point selected, showing Toast");
                if (toast != null) toast.cancel();
                toast = Toast.makeText(Boading_Details.this, "Please select a boarding point", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Log.d("DEBUG", "Boarding point selected, proceeding to next activity");
                Intent intent = new Intent(Boading_Details.this, Droping_point_details.class);
                startActivity(intent);
            }
        });
    }
    @SuppressLint("RestrictedApi")
    private void fetchBoardingTimes() {
        MediaType mediaType = MediaType.parse("application/json");
        TripDataHolder dataHolder = TripDataHolder.getInstance();
        String tripId = dataHolder.getTripId();
        if (tripId == null || tripId.isEmpty()) {
            Toast.makeText(this, "Trip ID is missing", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Trip ID is null or empty");
            return;
        }
        String jsonBody = "{\"trip_id\":\"" + tripId + "\",\"is_uat\":false}";
        RequestBody body = RequestBody.create(mediaType, jsonBody);
        Request request = new Request.Builder()
                .url(Constants.BASE_URL+"getCurrentTripDetails")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(Boading_Details.this,
                        "Network Failure: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                Log.d(TAG, "Response: " + responseBody);
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONObject data = jsonObject.getJSONObject("data");
                        if (data.has("boardingTimes")) {
                            Object droppingTimesObj = data.get("boardingTimes");
                            JSONArray droppingTimesArray;
                            if (droppingTimesObj instanceof JSONArray) {
                                droppingTimesArray = (JSONArray) droppingTimesObj;
                            } else if (droppingTimesObj instanceof JSONObject) {
                                droppingTimesArray = new JSONArray();
                                droppingTimesArray.put((JSONObject) droppingTimesObj);
                            } else {
                                runOnUiThread(() -> Toast.makeText(Boading_Details.this,
                                        "Invalid dropping points data", Toast.LENGTH_LONG).show());
                                return;
                            }
                            if (droppingTimesArray.length() == 0) {
                                runOnUiThread(() -> {
                                    Toast.makeText(Boading_Details.this, "No boarding points available", Toast.LENGTH_LONG).show();
                                    proceed_button.setEnabled(false);
                                    proceedLayout.setVisibility(View.GONE);
                                });
                            } else {
                                runOnUiThread(() -> populateRadioButtons(droppingTimesArray));
                            }
                        } else {
                            runOnUiThread(() -> Toast.makeText(Boading_Details.this,
                                    "No boarding points found", Toast.LENGTH_LONG).show());
                        }
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(Boading_Details.this,
                                "Error parsing response", Toast.LENGTH_LONG).show());
                    }
                }
            }
        });
    }
    private void populateRadioButtons(JSONArray boardingTimes) {
        try {
            for (int i = 0; i < boardingTimes.length(); i++) {
                JSONObject boardingTime = boardingTimes.getJSONObject(i);
                String address = boardingTime.getString("address");
                String location = boardingTime.getString("location");
                String time = boardingTime.getString("time");
                String bpId = boardingTime.getString("bpId");

                int totalMinutes = Integer.parseInt(time);
                int hours = totalMinutes / 60;
                int minutes = totalMinutes % 60;
                String amPm = (hours >= 12) ? "PM" : "AM";
                int formattedHour = (hours % 12 == 0) ? 12 : (hours % 12);
                String formattedTime = String.format("%02d:%02d %s", formattedHour, minutes, amPm);

                String fullText = location + "\n" + address + "\n" + formattedTime;
                SpannableString spannable = new SpannableString(fullText);
                spannable.setSpan(new AbsoluteSizeSpan(16, true), 0, location.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, location.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new AbsoluteSizeSpan(14, true), location.length() + 1, location.length() + 1 + address.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(spannable);
                radioButton.setId(View.generateViewId());
                radioButton.setTag(bpId);
                radioButton.setTypeface(ResourcesCompat.getFont(this, R.font.neue_haas_unica_pro_regular));
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                radioButton.setPadding(10, 20, 10, 20);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 10, 0, 30);
                radioButton.setLayoutParams(params);

                radioGroup.addView(radioButton);
            }

            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId != -1) {
                    RadioButton selectedRadioButton = findViewById(checkedId);
                    selectedBpId = (String) selectedRadioButton.getTag();
                    proceed_button.setEnabled(true); // Enable button
                    proceedLayout.setVisibility(View.VISIBLE); // Show LinearLayout
                    Log.d("BOARDING_POINT", "Selected bpId: " + selectedBpId);
                    TripDataHolder dataHolder = TripDataHolder.getInstance();
                    dataHolder.setBpId(Integer.parseInt(selectedBpId));


                } else {
                    selectedBpId = null; // Reset if no selection
                    proceed_button.setEnabled(false); // Disable button
                    proceedLayout.setVisibility(View.GONE); // Hide LinearLayout
                    Log.d("BOARDING_POINT", "No boarding point selected");
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error Populating Radio Buttons: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public String getSelectedBpId() {
        return selectedBpId;
    }
}

