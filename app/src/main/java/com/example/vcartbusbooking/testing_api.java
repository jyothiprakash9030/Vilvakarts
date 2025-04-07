package com.example.vcartbusbooking;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class testing_api extends AppCompatActivity {
    private static final String TAG = "testing_api";
    private TextView responseTextView;
    private OkHttpClient client;
    private Button fetchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_api);
        fetchTripDetails();

    }

    private void fetchTripDetails() {
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"trip_id\": 2000000151720004008,\r\n    \"type\":1\r\n    }\r\n");

            Request request = new Request.Builder()
                    .url("https://api-629-bis.vilvabusiness.com/api/getCurrentTripDetails")
                    .post(body)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.body() != null) {
                    String responseData = response.body().string();
                    Log.d(TAG, "Response: " + responseData);
                } else {
                    Log.d(TAG, "No Response");
                }
            } catch (IOException e) {
                Log.e(TAG, "Request failed: " + e.getMessage());
            }
        }).start();
    }
}





