package com.example.vcartbusbooking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class wait_a_second_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        getWindow().setStatusBarColor(Color.WHITE);

        // Force status bar icons to be dark (black) in both light and dark modes
        getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );

        setContentView(R.layout.activity_wait_a_second_page);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(wait_a_second_page.this, Payment.class);
            startActivity(intent);
            finish();
        }, 1000); // 3 seconds delay
    }
}