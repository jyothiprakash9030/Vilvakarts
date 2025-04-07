package com.example.vcartbusbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);

        getWindow().setStatusBarColor(Color.WHITE);

        // Force status bar icons to be dark (black) in both light and dark modes
        getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );

        LinearLayout home=findViewById(R.id.home);
        LinearLayout Booking=findViewById(R.id.Booking);
        LinearLayout help=findViewById(R.id.help);
        LinearLayout account=findViewById(R.id.account);
        LinearLayout logout=findViewById(R.id.logout);

        logout.setOnClickListener(view -> {
            // Get SharedPreferences
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            // Reset verification status
            editor.putBoolean("isVerified", false);
            editor.apply(); // Apply changes

            // Redirect to login page (Using_MobileNumber_login)
            Intent intent = new Intent(Account.this, Using_MobileNumber_login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clears back stack
            startActivity(intent);
            finish(); // Finish current activity
        });



        home.setOnClickListener(view -> {


            ImageView home_img=findViewById(R.id.home_img);
            TextView home_text=findViewById(R.id.home_text);
            // Change background color to red
            home_img.setImageResource(R.drawable.home);
            home_text.setTextColor(Color.RED);

            // Delay for 1000 milliseconds (1 second) before starting the activity
            new Handler().postDelayed(() -> {
                Intent i1 = new Intent(Account.this, SourceAndDestination.class);
                startActivity(i1);

                // Reset background color (optional)
//                Booking.setBackgroundColor(Color.TRANSPARENT);
                home_img.setImageResource(R.drawable.booking);
                home_text.setTextColor(Color.BLACK);

            }, 200);
        });




        help.setOnClickListener(view -> {


            ImageView help_img=findViewById(R.id.help_img);
            TextView help_text=findViewById(R.id.help_text);
            // Change background color to red
            help_img.setImageResource(R.drawable.help_red_color);
            help_text.setTextColor(Color.RED);

            // Delay for 1000 milliseconds (1 second) before starting the activity
            new Handler().postDelayed(() -> {
                Intent i1 = new Intent(Account.this, help_page.class);
                startActivity(i1);

                // Reset background color (optional)

                help_img.setImageResource(R.drawable.help);
                help_text.setTextColor(Color.BLACK);

            }, 200);
        });




        Booking.setOnClickListener(view -> {


            ImageView booking_img=findViewById(R.id.booking_img);
            TextView  booking_text =findViewById(R.id.booking_text);
            // Change background color to red
            booking_img.setImageResource(R.drawable.booking_red_color);
            booking_text.setTextColor(Color.RED);

            // Delay for 1000 milliseconds (1 second) before starting the activity
            new Handler().postDelayed(() -> {
                Intent i1 = new Intent(Account.this, Booking_page.class);
                startActivity(i1);

                // Reset background color (optional)
//                Booking.setBackgroundColor(Color.TRANSPARENT);
                booking_img.setImageResource(R.drawable.booking);
                booking_text.setTextColor(Color.BLACK);

            }, 200);
        });







    }
}