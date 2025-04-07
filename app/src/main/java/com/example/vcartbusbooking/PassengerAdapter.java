package com.example.vcartbusbooking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class PassengerAdapter extends RecyclerView.Adapter<PassengerAdapter.ViewHolder> {
    private List<PassengerInfo> passengerList;
    private String[] defaultGenderOptions = {"Select Gender", "Male", "Female", "Other"};
    private String[] ladiesSeatGenderOptions = {"Female (Reserved)"};
    private int selectedPosition = -1;

    public PassengerAdapter(List<PassengerInfo> passengerList) {
        this.passengerList = passengerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_passenger, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PassengerInfo passenger = passengerList.get(position);
        holder.nameEditText.setText(passenger.getName());
        holder.ageEditText.setText(passenger.getAge());

        // Get selected seats and ladies seat info from TripDataHolder
        TripDataHolder dataHolder = TripDataHolder.getInstance();
        List<SelectedSeat> selectedSeats = dataHolder.getSelectedSeats();
        List<Boolean> ladiesSeats = dataHolder.getLadiesSeats();

        if (position < selectedSeats.size()) {
            SelectedSeat selectedSeat = selectedSeats.get(position);
            holder.seatNumber.setText("Seat Number: " + selectedSeat.getSeatName() + " (" + selectedSeat.getDeck() + " Deck)");

            // Check if this seat is a ladies' reserved seat
            boolean isLadiesSeat = position < ladiesSeats.size() && ladiesSeats.get(position);

            // Setup gender spinner
            String[] genderOptions = isLadiesSeat ? ladiesSeatGenderOptions : defaultGenderOptions;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.itemView.getContext(),
                    android.R.layout.simple_spinner_item, genderOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.genderSpinner.setAdapter(adapter);

            if (isLadiesSeat) {
                // Set "Female (Reserved)" and disable interaction
                holder.genderSpinner.setSelection(0);
                holder.genderSpinner.setEnabled(false);
                holder.genderSpinner.setClickable(false);
                holder.genderSpinner.setFocusable(false);
                passenger.setGender("Female"); // Ensure passenger gender is set correctly
            } else {
                int spinnerPosition = Arrays.asList(defaultGenderOptions).indexOf(passenger.getGender());
                holder.genderSpinner.setSelection(spinnerPosition >= 0 ? spinnerPosition : 0);
                holder.genderSpinner.setEnabled(true);
                holder.genderSpinner.setClickable(true);
                holder.genderSpinner.setFocusable(true);
            }

            // Debugging log
            Log.d("PassengerAdapter", "Position: " + position + ", isLadiesSeat: " + isLadiesSeat + ", Gender: " + passenger.getGender());

        } else {
            holder.seatNumber.setText("Seat Number: N/A");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.itemView.getContext(),
                    android.R.layout.simple_spinner_item, defaultGenderOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.genderSpinner.setAdapter(adapter);
            holder.genderSpinner.setSelection(0);
        }

        // Handle primary passenger selection via RadioButton
        holder.radioButton.setChecked(position == selectedPosition);
        holder.radioButton.setOnClickListener(v -> {
            if (holder.getAdapterPosition() != selectedPosition) {
                showConfirmationDialog(holder.itemView.getContext(), () -> {
                    selectedPosition = holder.getAdapterPosition();
                    notifyDataSetChanged();
                });
            }
        });

        // Name TextWatcher
        holder.nameEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                passenger.setName(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Age TextWatcher
        holder.ageEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                passenger.setAge(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Gender Spinner Selection
        holder.genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (position < ladiesSeats.size() && !ladiesSeats.get(position)) { // Only update if not a ladies' seat
                    String selectedGender = (String) parent.getItemAtPosition(pos);
                    passenger.setGender(selectedGender);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    public int getItemCount() {
        return passengerList.size();
    }

    public void updatePassengerList(List<SelectedSeat> selectedSeats) {
        passengerList.clear();
        for (SelectedSeat seat : selectedSeats) {
            PassengerInfo passenger = new PassengerInfo();
            passenger.setSeatNumber(seat.getSeatName());
            passenger.setDeck(seat.getDeck());
            passengerList.add(passenger);
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText nameEditText, ageEditText;
        Spinner genderSpinner;
        RadioButton radioButton;
        TextView seatNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            seatNumber = itemView.findViewById(R.id.seatNumber);
            nameEditText = itemView.findViewById(R.id.editTextName);
            genderSpinner = itemView.findViewById(R.id.spinnerGender);
            ageEditText = itemView.findViewById(R.id.editTextAge);
            radioButton = itemView.findViewById(R.id.checkbox_notifications);
        }
    }

    private void showConfirmationDialog(Context context, Runnable onConfirm) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Selection")
                .setMessage("Do you want to set this as the primary passenger?")
                .setPositiveButton("Yes", (dialog, which) -> onConfirm.run())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
