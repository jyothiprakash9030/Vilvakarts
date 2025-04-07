package com.example.vcartbusbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MobileOtp extends AppCompatActivity {

    private EditText[] otpInputs = new EditText[6];
    private Button verifyOtpButton;
    private TextView timerText;
    private String mobileNumber;
    private CountDownTimer countDownTimer;
    private static final long OTP_TIMER_DURATION = 120000; // 120 seconds (2 minutes)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mobile_otp);
        getWindow().setStatusBarColor(Color.WHITE);

        // Force status bar icons to be dark (black) in both light and dark modes
        getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );
        // Initialize OTP input fields
        otpInputs[0] = findViewById(R.id.inputotp1);
        otpInputs[1] = findViewById(R.id.inputotp2);
        otpInputs[2] = findViewById(R.id.inputotp3);
        otpInputs[3] = findViewById(R.id.inputotp4);
        otpInputs[4] = findViewById(R.id.inputotp5);
        otpInputs[5] = findViewById(R.id.inputotp6);
        verifyOtpButton = findViewById(R.id.sign);
        timerText = findViewById(R.id.timerText);

        mobileNumber = getIntent().getStringExtra("mobile");

        setupOtpInputs();  // Set OTP input behavior
        startOtpCountdown(); // Start countdown timer

        verifyOtpButton.setOnClickListener(v -> validateOtp());
    }

    // Method to start the OTP countdown timer
    private void startOtpCountdown() {
        countDownTimer = new CountDownTimer(OTP_TIMER_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                timerText.setText("Enter OTP in " + secondsRemaining + " seconds");
            }

            @Override
            public void onFinish() {
                timerText.setText("OTP expired! Request a new one.");
                verifyOtpButton.setEnabled(false); // Disable verify button after timeout
            }
        }.start();
    }

    // Method to manage OTP input fields (auto-focus next field)
    private void setupOtpInputs() {
        for (int i = 0; i < otpInputs.length; i++) {
            final int index = i;
            otpInputs[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().trim().isEmpty() && index < otpInputs.length - 1) {
                        otpInputs[index + 1].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    // Method to validate and verify OTP
    private void validateOtp() {


        StringBuilder otpBuilder = new StringBuilder();
        for (EditText otpInput : otpInputs) {
            otpBuilder.append(otpInput.getText().toString());
        }

        String otp = otpBuilder.toString();

        if (otp.length() < 6) {
            Toast.makeText(this, "Enter complete OTP", Toast.LENGTH_SHORT).show();
            return;
        }
else {
            verifyOtpButton.setText("Verify OTP..");
        }
        verifyOtp(otp);
    }

    // Method to send OTP verification request to the server
    private void verifyOtp(String otp) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("application/json");

                // Prepare request data with mobile number and OTP
                String requestBody = String.format("{\"username\":\"%s\", \"otp\":\"%s\"}", mobileNumber, otp);
                RequestBody body = RequestBody.create(mediaType, requestBody);

                // API Request
                Request request = new Request.Builder()
                        .url("https://api-629-bis.vilvabusiness.com/api/submit-otp")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();

                // Send request and get the response
                Response response = client.newCall(request).execute();
                String responseBody = response.body() != null ? response.body().string() : "No response";

                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Toast.makeText(MobileOtp.this, "OTP Verified!", Toast.LENGTH_SHORT).show();
                        goToHomePage(); // Go to next page if OTP is valid
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(MobileOtp.this, "Invalid OTP! Response: " , Toast.LENGTH_LONG).show();
                    });
                }

                System.out.println("API Response: " + responseBody);

            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MobileOtp.this, "Network Error!", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    // Navigate to the home page after successful OTP verification
    private void goToHomePage() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Stop the timer if OTP is verified
        }

        // Save verification status
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isVerified", true); // Mark OTP as verified
        editor.apply();

        // Navigate to home page
        Intent intent = new Intent(MobileOtp.this, SourceAndDestination.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}