package com.example.vcartbusbooking;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Using_Email_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_using_email_login);
        getWindow().setStatusBarColor(Color.WHITE);

        // Force status bar icons to be dark (black) in both light and dark modes
        getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );

//        TextInputLayout textInputLayout = findViewById(R.id.textInputLayout);
//        TextInputEditText editText = findViewById(R.id.et_phone_number);
//
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!s.toString().trim().isEmpty()) {
//                    textInputLayout.setBackgroundResource(R.drawable.source_destination_background);
//
//// Change outline to red
//                     // Change hint text color
//                } else {
//                    textInputLayout.setBoxStrokeColor(Color.BLACK); // Default color
//                    textInputLayout.setHintTextColor(ColorStateList.valueOf(Color.BLACK)); // Default hint color
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//        });
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        Button button=findViewById(R.id.btn_generate_otp_email);
//
//        button.setOnClickListener(view -> {
//
//            Intent intent= new Intent(Using_Email_login.this, SourceAndDestination.class);
//            startActivity(intent);
//            finish();

//        });

    }

}