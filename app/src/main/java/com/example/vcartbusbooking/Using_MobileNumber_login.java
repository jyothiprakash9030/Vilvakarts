package com.example.vcartbusbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Using_MobileNumber_login extends AppCompatActivity {

    private EditText editTextMobile;
    private Button buttonLogin;

    private static final String API_URL = "https://api-629-bis.vilvabusiness.com/api/request-otp";

    // Singleton OkHttpClient instance with timeout settings
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);

        // Force status bar icons to be dark (black) in both light and dark modes
        getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );
        // Check if OTP is already verified
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isVerified = prefs.getBoolean("isVerified", false);

        if (isVerified) {
            // Skip OTP screens and go directly to home page
            Intent intent = new Intent(this, SourceAndDestination.class);
            startActivity(intent);
            finish();
            return; // Stop further execution
        }

        setContentView(R.layout.activity_using_mobile_number_login);

        editTextMobile = findViewById(R.id.et_phone_number);
        buttonLogin = findViewById(R.id.btn_generate_otp);

        buttonLogin.setOnClickListener(v -> {
            String mobile = editTextMobile.getText().toString().trim();

            if (mobile.isEmpty() || mobile.length() < 10) {
                editTextMobile.setError("Enter a valid mobile number");
                return;
            }
            else
            {
                buttonLogin.setText("Generating..");
            }

            // Show "Button Clicked" message


            // Send OTP request
            sendOtpRequest(mobile);

            // Delay navigation slightly (500ms) to allow Toast to appear
            new android.os.Handler().postDelayed(() -> {
                Intent intent = new Intent(Using_MobileNumber_login.this, MobileOtp.class);
                intent.putExtra("mobile", mobile); // Pass mobile number
                startActivity(intent);
            }, 500);
        });


    }


    private void sendOtpRequest(String mobile) {
        new Thread(() -> {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", mobile);

                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, jsonObject.toString());

                Request request = new Request.Builder()
                        .url(API_URL)
                        .post(body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json") // Optional, helps in some cases
                        .build();

                Response response = client.newCall(request).execute();
                String responseBody = response.body() != null ? response.body().string() : "No response body";

                if (!response.isSuccessful()) {
                    Log.e("API Error", "Request Failed: " + response.code() + " - " + responseBody);
                    runOnUiThread(() -> Toast.makeText(Using_MobileNumber_login.this, "Failed: " + response.code(), Toast.LENGTH_SHORT).show());
                    return;
                }

                Log.d("API Response", "Success: " + responseBody);
                runOnUiThread(() -> Toast.makeText(Using_MobileNumber_login.this, "OTP Sent Successfully!", Toast.LENGTH_SHORT).show());

            } catch (IOException e) {
                Log.e("Network Error", "IOException: " + e.getMessage(), e);
                runOnUiThread(() -> Toast.makeText(Using_MobileNumber_login.this, "Network Error! Check API URL & Internet.", Toast.LENGTH_SHORT).show());
            } catch (JSONException e) {
                Log.e("JSON Error", "JSONException: " + e.getMessage(), e);
                runOnUiThread(() -> Toast.makeText(Using_MobileNumber_login.this, "JSON Error!", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}

