package com.example.vcartbusbooking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vcartbusbooking.utils.ApiClient;
import com.example.vcartbusbooking.utils.ApiService;
import com.example.vcartbusbooking.utils.City;
import com.example.vcartbusbooking.utils.CityResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourceAndDestination extends AppCompatActivity {
    Button buttonSearchBus;
    String selectedDate;
    HashMap<String, String> cityMap = new HashMap<>();
    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "SearchHistory";
    private static final String KEY_FROM_CITIES = "fromCities";
    private static final String KEY_TO_CITIES = "toCities";
    private List<String> allCities = new ArrayList<>(); // API-fetched cities

    private HorizontalScrollView horizontalScrollView;
    private final Handler handler = new Handler();
    private int scrollX = 0;
    private final int scrollSpeed = 2; // Speed of scrolling
    private final int delay = 1000; // Delay in milliseconds




    private LinearLayout imageContainer;
//    private List<Integer> imageList = Arrays.asList(
//            R.drawable.img4, R.drawable.img2, R.drawable.kerala,
//            R.drawable.img4, R.drawable.tsrtc, R.drawable.assam, R.drawable.tourtist
//    );
    private int scrollPosition = 0;


    private int centerPosition = -1;  // Track the center image





    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_source_and_destination);
        getWindow().setStatusBarColor(Color.WHITE);

        getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );


        horizontalScrollView = findViewById(R.id.horizontalScrollView);
        imageContainer = findViewById(R.id.imageContainer);

//        addImages();
        applyAlphaEffect(); // Initial effect

        // Detect scrolling and adjust alpha
        horizontalScrollView.getViewTreeObserver().addOnScrollChangedListener(() -> applyAlphaEffect());

        // Start Auto Scrolling
        startAutoScroll();









        buttonSearchBus = findViewById(R.id.buttonSearchBus);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.editTextFrom);
        AutoCompleteTextView autoCompleteTextView1 = findViewById(R.id.editTextTo);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        ImageView swapButton = findViewById(R.id.swapButton);
        swapButton.setOnClickListener(view -> {
            String fromText = autoCompleteTextView.getText().toString();
            String toText = autoCompleteTextView1.getText().toString();
            autoCompleteTextView.setText(toText);
            autoCompleteTextView1.setText(fromText);
        });

        // Fetch cities from API
        fetchCities(autoCompleteTextView, autoCompleteTextView1);

        // Set up focus listeners to show previous searches when cursor is placed
        autoCompleteTextView.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                Log.d("SearchDebug", "From EditText gained focus");
                showSearchHistory(autoCompleteTextView, KEY_FROM_CITIES);
            }
        });

        autoCompleteTextView1.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                Log.d("SearchDebug", "To EditText gained focus");
                showSearchHistory(autoCompleteTextView1, KEY_TO_CITIES);
            }
        });

        // Calendar and date picker setup
        ImageView calendarImage = findViewById(R.id.calendarImage);
        TextView dateVisible = findViewById(R.id.text_date);
        calendarImage.setOnClickListener(view -> showDatePicker(dateVisible));
        dateVisible.setOnClickListener(view -> showDatePicker(dateVisible));

        // Today and tomorrow logic
        setupTodayTomorrow(dateVisible);

        // Search button logic with saving search history
        buttonSearchBus.setOnClickListener(view -> {
            String from = autoCompleteTextView.getText().toString().trim();
            String to = autoCompleteTextView1.getText().toString().trim();
            String textDate1 = dateVisible.getText().toString().trim();

            if (from.isEmpty() || to.isEmpty() || textDate1.isEmpty()) {
                Toast.makeText(SourceAndDestination.this, "Please fill all fields and select a date", Toast.LENGTH_SHORT).show();
            } else {
                String fromCityId = cityMap.get(from);
                String toCityId = cityMap.get(to);

                if (fromCityId == null || toCityId == null) {
                    Toast.makeText(SourceAndDestination.this, "Invalid source or destination", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save the search history
                saveSearchHistory(from, to);

                Intent intent = new Intent(SourceAndDestination.this, BusDetailsActivity.class);
                intent.putExtra("from", from);
                intent.putExtra("to", to);
                intent.putExtra("fromCityId", fromCityId);
                intent.putExtra("toCityId", toCityId);
                intent.putExtra("textDate1", textDate1);
                startActivity(intent);
            }
        });

        // Other UI setup (home, booking, help, account)
        setupNavigationButtons();
    }


//    private void addImages() {
//        for (int imageRes : imageList) {
//            ImageView imageView = new ImageView(this);
//            imageView.setImageResource(imageRes);
//            imageView.setLayoutParams(new LinearLayout.LayoutParams(150, 180)); // Set width & height
//            imageView.setPadding(8, 8, 8, 8);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageContainer.addView(imageView);
//        }
//    }

    private void applyAlphaEffect() {
        int centerX = horizontalScrollView.getScrollX() + horizontalScrollView.getWidth() / 2;
        int minDistance = Integer.MAX_VALUE;
        int newCenterPosition = -1;

        for (int i = 0; i < imageContainer.getChildCount(); i++) {
            ImageView imageView = (ImageView) imageContainer.getChildAt(i);
            int imageCenterX = imageView.getLeft() + imageView.getWidth() / 2;
            int distance = Math.abs(centerX - imageCenterX);

            if (distance < minDistance) {
                minDistance = distance;
                newCenterPosition = i;
            }
            // Apply fade effect
            float alphaValue = (distance < 100) ? 1.0f : 0.3f;
            imageView.setAlpha(alphaValue);
        }

        if (newCenterPosition != centerPosition) {
            centerPosition = newCenterPosition;
        }
    }

    private void startAutoScroll() {
        final int scrollIncrement = 100; // Increase this for faster scrolling
        final int delay = 1000; // Reduce delay for faster updates (milliseconds)

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (scrollPosition < imageContainer.getWidth() - horizontalScrollView.getWidth()) {
                    scrollPosition += scrollIncrement; // Moves faster now
                } else {
                    scrollPosition = 0; // Reset to start
                }

                horizontalScrollView.smoothScrollTo(scrollPosition, 0);
                applyAlphaEffect(); // Update transparency effect

                handler.postDelayed(this, delay); // Reduce delay for speed
            }
        }, delay);
    }





    private void fetchCities(AutoCompleteTextView autoCompleteTextView, AutoCompleteTextView autoCompleteTextView1) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");

        apiService.GetCities(body).enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CityResponse apiResponse = response.body();
                    List<City> cityList = apiResponse.getCityData().getCities();

                    if (cityList == null || cityList.isEmpty()) {
                        Log.e("API Error", "City list is empty!");
                        return;
                    }

                    allCities.clear();
                    for (int i = 0; i < cityList.size(); i++) {
                        allCities.add(cityList.get(i).getName());
                        cityMap.put(cityList.get(i).getName(), cityList.get(i).getId());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SourceAndDestination.this,
                            android.R.layout.simple_dropdown_item_1line, allCities);

                    runOnUiThread(() -> {
                        autoCompleteTextView.setAdapter(adapter);
                        autoCompleteTextView1.setAdapter(adapter);
                        autoCompleteTextView.setThreshold(0); // Show suggestions even with no input
                        autoCompleteTextView1.setThreshold(0);
                        Log.d("SearchDebug", "API cities loaded: " + allCities.size());
                    });
                } else {
                    Log.e("API Error", "Response unsuccessful: Code " + response.code() + ", Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                Log.e("API Error", "Request failed: " + t.getMessage());
            }
        });
    }

    private void saveSearchHistory(String from, String to) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> fromCities = sharedPreferences.getStringSet(KEY_FROM_CITIES, new HashSet<>());
        Set<String> toCities = sharedPreferences.getStringSet(KEY_TO_CITIES, new HashSet<>());

        Set<String> updatedFromCities = new HashSet<>(fromCities);
        Set<String> updatedToCities = new HashSet<>(toCities);
        updatedFromCities.add(from);
        updatedToCities.add(to);

        editor.putStringSet(KEY_FROM_CITIES, updatedFromCities);
        editor.putStringSet(KEY_TO_CITIES, updatedToCities);
        editor.apply();

        Log.d("SearchDebug", "Saved - From: " + updatedFromCities + ", To: " + updatedToCities);
    }

    private void showSearchHistory(AutoCompleteTextView autoCompleteTextView, String key) {
        Set<String> history = sharedPreferences.getStringSet(key, new HashSet<>());
        Log.d("SearchDebug", "Retrieved history for " + key + ": " + history);

        List<String> suggestions = new ArrayList<>(history);
        if (!allCities.isEmpty()) {
            suggestions.addAll(allCities); // Add API cities below history
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, suggestions);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(0); // Ensure dropdown shows even with no input
        autoCompleteTextView.showDropDown(); // Force dropdown to show
        Log.d("SearchDebug", "Dropdown shown with " + suggestions.size() + " items");
    }

    private void showDatePicker(TextView dateVisible) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                SourceAndDestination.this,
                (view1, selectedYear, selectedMonth, selectedDay) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(selectedYear, selectedMonth, selectedDay);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    selectedDate = sdf.format(selectedCalendar.getTime());
                    dateVisible.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void setupTodayTomorrow(TextView dateVisible) {
        TextView textToday = findViewById(R.id.text_today);
        TextView textTomorrow = findViewById(R.id.text_tomorrow);
        LinearLayout today = findViewById(R.id.today);
        LinearLayout tomorrow = findViewById(R.id.tomorrow);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String tomorrowDate = sdf.format(calendar.getTime());

        dateVisible.setText(todayDate);
        boolean[] isTomorrowDisplayed = {false};

        today.setOnClickListener(view -> {
            dateVisible.setText(todayDate);
            isTomorrowDisplayed[0] = false;
            updateButtonStyles(today, textToday, tomorrow, textTomorrow);
        });

        tomorrow.setOnClickListener(view -> {
            dateVisible.setText(tomorrowDate);
            isTomorrowDisplayed[0] = true;
            updateButtonStyles(tomorrow, textTomorrow, today, textToday);
        });
    }

    private void updateButtonStyles(LinearLayout clicked, TextView clickedText, LinearLayout other, TextView otherText) {
        clicked.setBackgroundResource(R.drawable.today_and_tomorrow_background_click__button_bg);
        clickedText.setTextColor(Color.WHITE);
        other.setBackgroundResource(R.drawable.today_and_tomorrow_background_unclick__button_bg);
        otherText.setTextColor(Color.BLACK);

        new Handler().postDelayed(() -> {
            clicked.setBackgroundResource(R.drawable.today_and_tomorrow_background_click__button_bg);
            clickedText.setTextColor(Color.WHITE);
            other.setBackgroundResource(R.drawable.today_and_tomorrow_background_unclick__button_bg);
            otherText.setTextColor(Color.BLACK);
        }, 100);
    }

    private void setupNavigationButtons() {
        LinearLayout home = findViewById(R.id.home);
        LinearLayout booking = findViewById(R.id.Booking);
        LinearLayout help = findViewById(R.id.help);
        LinearLayout account = findViewById(R.id.account);

        booking.setOnClickListener(view -> {
            ImageView bookingImage = findViewById(R.id.Booking_image);
            TextView bookingText = findViewById(R.id.Booking_textview);
            bookingImage.setImageResource(R.drawable.booking_red_color);
            bookingText.setTextColor(Color.RED);

            new Handler().postDelayed(() -> {
                Intent i1 = new Intent(SourceAndDestination.this, Booking_page.class);
                startActivity(i1);
                bookingImage.setImageResource(R.drawable.booking);
                bookingText.setTextColor(Color.BLACK);
            }, 200);
        });

        help.setOnClickListener(view -> {
            ImageView helpImg = findViewById(R.id.help_img);
            TextView helpText = findViewById(R.id.help_text);
            helpImg.setImageResource(R.drawable.help_red_color);
            helpText.setTextColor(Color.RED);

            new Handler().postDelayed(() -> {
                Intent i1 = new Intent(SourceAndDestination.this, help_page.class);
                startActivity(i1);
                helpImg.setImageResource(R.drawable.help);
                helpText.setTextColor(Color.BLACK);
            }, 200);
        });

        account.setOnClickListener(view -> {
            ImageView accountImg = findViewById(R.id.account_img);
            TextView accountText = findViewById(R.id.account_text);
            accountImg.setImageResource(R.drawable.account_red_color);
            accountText.setTextColor(Color.RED);

            new Handler().postDelayed(() -> {
                Intent i1 = new Intent(SourceAndDestination.this, Account.class);
                startActivity(i1);
                accountImg.setImageResource(R.drawable.account);
                accountText.setTextColor(Color.BLACK);
            }, 200);
        });
    }

    public void gotosource(View view) {
        view.setBackgroundResource(R.drawable.shopping_button_corner_radius_bg);
        TextView textView = findViewById(R.id.text_shopping);
        textView.setTextColor(getResources().getColor(R.color.white));

        new Handler().postDelayed(() -> {
            view.setBackgroundResource(android.R.color.white);
            textView.setTextColor(getResources().getColor(R.color.black));
        }, 1000);

        Intent intent = new Intent(SourceAndDestination.this, Floating_screen_grocery.class);
        startActivity(intent);
    }
}