package com.example.vcartbusbooking;

import android.content.Intent;
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

public class Booking_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_booking_page);
        getWindow().setStatusBarColor(Color.WHITE);

        // Force status bar icons to be dark (black) in both light and dark modes
        getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );





        LinearLayout home=findViewById(R.id.home);
        LinearLayout Booking=findViewById(R.id.Booking);
        LinearLayout help=findViewById(R.id.help);
        LinearLayout account=findViewById(R.id.account);

        home.setOnClickListener(view -> {


            ImageView home_img=findViewById(R.id.home_img);
            TextView home_text=findViewById(R.id.home_text);
            // Change background color to red
            home_img.setImageResource(R.drawable.home_red_color);
            home_text.setTextColor(Color.RED);

            // Delay for 1000 milliseconds (1 second) before starting the activity
            new Handler().postDelayed(() -> {
                Intent i1 = new Intent(Booking_page.this, SourceAndDestination.class);
                startActivity(i1);

                // Reset background color (optional)
//                Booking.setBackgroundColor(Color.TRANSPARENT);
                home_img.setImageResource(R.drawable.home_icon);
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
                Intent i2= new Intent(Booking_page.this, help_page.class);
                startActivity(i2);

                // Reset background color (optional)

                help_img.setImageResource(R.drawable.help);
                help_text.setTextColor(Color.BLACK);

            }, 200);
        });




        account.setOnClickListener(view -> {


            ImageView account_img=findViewById(R.id.account_img);
            TextView  account_text =findViewById(R.id.account_text);
            // Change background color to red
            account_img.setImageResource(R.drawable.account_red_color);
            account_text.setTextColor(Color.RED);

            // Delay for 1000 milliseconds (1 second) before starting the activity
            new Handler().postDelayed(() -> {
                Intent i3 = new Intent(Booking_page.this, Account.class);
                startActivity(i3);

                // Reset background color (optional)
//                Booking.setBackgroundColor(Color.TRANSPARENT);
                account_img.setImageResource(R.drawable.account);
                account_text.setTextColor(Color.BLACK);

            }, 200);
        });







    }
}