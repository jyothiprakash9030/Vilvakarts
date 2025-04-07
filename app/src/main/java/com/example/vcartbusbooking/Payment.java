package com.example.vcartbusbooking;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class Payment extends AppCompatActivity {
    Button proceed_button_Success;
TextView bus_seating_from_address,bus_seatingtoadress,bus_seatingvisibledate,bus_name,Base_Ticket_Amount,with_GST_amount,passenger_count,Total_Amount_Paid;
    @SuppressLint({"MissingInflatedId", "DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_payment);



        getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR    );

        bus_seating_from_address=findViewById(R.id.bus_seating_from_address);
        bus_seatingtoadress = findViewById(R.id.bus_seatingtoadress);
        bus_seatingvisibledate = findViewById(R.id.bus_seatingvisibledate);
        bus_name = findViewById(R.id.bus_name);
        Base_Ticket_Amount = findViewById(R.id.Base_Ticket_Amount);
        with_GST_amount=findViewById(R.id.with_GST_amount);
        passenger_count=findViewById(R.id.passenger_count);
        Total_Amount_Paid=findViewById(R.id.Total_Amount_Paid);
        proceed_button_Success=findViewById(R.id.proceed_button_Success);


        TripDataHolder dataHolder = TripDataHolder.getInstance();
        double amount = dataHolder.getTotalAmount();
        String busType = dataHolder.getBusType();
        String date = dataHolder.getDate();
        String from = dataHolder.getFrom();
        String to = dataHolder.getTo();



        bus_name.setText(busType);
        bus_seatingvisibledate.setText(date);
        bus_seating_from_address.setText(from);
        bus_seatingtoadress.setText(to);

        Base_Ticket_Amount.setText("Ticket Amount:₹ " + String.format("%.2f", amount));
//





        double totalAmount = amount + 50;  // Add 50 to amount


        with_GST_amount.setText("₹ " + String.format("%.2f", totalAmount));



        Total_Amount_Paid.setText("₹ " + String.format("%.2f", totalAmount));



        int seatCount = dataHolder.getPassengercount();
     passenger_count.setText(seatCount + "  Passenger  |");







        proceed_button_Success.setOnClickListener(view -> {


            ProgressDialog progressDialog = new ProgressDialog(Payment.this);
            progressDialog.setMessage("Processing your request...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Intent i=new Intent(Payment.this,Sucessfull_page.class);
            startActivity(i);
        });


    }
}