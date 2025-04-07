package com.example.vcartbusbooking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vcartbusbooking.utils.ApiClient;
import com.example.vcartbusbooking.utils.ApiService;
import com.example.vcartbusbooking.utils.City;
import com.example.vcartbusbooking.utils.CityResponse;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_welcome_page);

        new Handler().postDelayed(() -> {
            // Redirect to Second Activity
            Intent intent = new Intent(WelcomePage.this, Welcomepagesecond.class);
            startActivity(intent);
            finish(); // Optional: Call finish() to close the current activity
        }, 3000); // Delay time in milliseconds



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }




}