package com.example.vcartbusbooking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class help_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help_page);
        getWindow().setStatusBarColor(Color.WHITE);

        // Force status bar icons to be dark (black) in both light and dark modes
        getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );


        String[] faqItems = {
                "What is Vilvakart?",
                "How do I place an order on Vilvakart?",
                "What payment options are available?",
                "How can I track my order?",
                "What is Vilvakart's return policy?",
                "How do I contact Vilvakart customer support?",
                "Are there any shipping charges?",
                "Does Vilvakart offer discounts or promotions?",
                "How do I cancel my order?",
                "How can I create an account on Vilvakart?"
        };

        // Find the ListView
        ListView faqList = findViewById(R.id.faq_list);

        // Remove the divider between rows
        faqList.setDivider(null);
        faqList.setDividerHeight(0);

        // Set the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, faqItems);
        faqList.setAdapter(adapter);

        // Adjust the height of the ListView
        setListViewHeightBasedOnChildren(faqList);








        /////////////////////////////////////

        LinearLayout home=findViewById(R.id.home);
        LinearLayout Booking=findViewById(R.id.Booking);
        LinearLayout help=findViewById(R.id.help);
        LinearLayout account=findViewById(R.id.account);

        home.setOnClickListener(view -> {


            ImageView home_img=findViewById(R.id.home_img);
            TextView home_text=findViewById(R.id.home_text);
            // Change background color to red
            home_img.setImageResource(R.drawable.home);
            home_text.setTextColor(Color.RED);

            // Delay for 1000 milliseconds (1 second) before starting the activity
            new Handler().postDelayed(() -> {
                Intent i1 = new Intent(help_page.this, SourceAndDestination.class);
                startActivity(i1);

                // Reset background color (optional)
//                Booking.setBackgroundColor(Color.TRANSPARENT);
                home_img.setImageResource(R.drawable.home_icon);
                home_text.setTextColor(Color.BLACK);

            }, 200);
        });




        Booking.setOnClickListener(view -> {


            ImageView booking_img=findViewById(R.id.booking_img);
            TextView booking_text=findViewById(R.id.booking_text);
            // Change background color to red
            booking_img.setImageResource(R.drawable.booking_red_color);
            booking_text.setTextColor(Color.RED);

            // Delay for 1000 milliseconds (1 second) before starting the activity
            new Handler().postDelayed(() -> {
                Intent i1 = new Intent(help_page.this, Booking_page.class);
                startActivity(i1);

                // Reset background color (optional)

                booking_img.setImageResource(R.drawable.booking);
                booking_text.setTextColor(Color.BLACK);

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
                Intent i1 = new Intent(help_page.this, Account.class);
                startActivity(i1);

                // Reset background color (optional)
//                Booking.setBackgroundColor(Color.TRANSPARENT);
                account_img.setImageResource(R.drawable.account);
                account_text.setTextColor(Color.BLACK);

            }, 200);
        });




    }

    // Method to adjust ListView height dynamically
    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}