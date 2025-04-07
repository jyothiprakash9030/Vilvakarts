package com.example.vcartbusbooking;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private List<Trip> tripList;

    public TripAdapter(List<Trip> tripList) {
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);

        if (holder.busType != null) holder.busType.setText(trip.getBusType());
        if (holder.busRoutes != null) holder.busRoutes.setText(trip.getBusRoutes());
        if (holder.travels != null) holder.travels.setText(trip.getTravels());
        if (holder.departureTime != null) holder.departureTime.setText(trip.getDepartureTime());
        if (holder.arrivalTime != null) holder.arrivalTime.setText(trip.getArrivalTime());
        if (holder.duration != null) holder.duration.setText("(" + trip.getDuration() + ")");
        if (holder.price != null) {
            double fare = trip.getLowestBaseFare();
            holder.price.setText("Price: " + (fare == Double.MAX_VALUE ? "Not Available" : String.format("%.2f", fare)));
        }
        if (holder.availableSeats != null) {
            holder.availableSeats.setText(trip.getAvailableSeats() + " Seats available");
        }

        if (holder.busType != null) {
            holder.busType.setText(trip.getBusType() + (trip.isAC() ? " (AC)" : " (Non-AC)"));
        }

        holder.itemView.setOnClickListener(view -> {
            TripDataHolder tripDataHolder = TripDataHolder.getInstance();

            tripDataHolder.setTripId(trip.getId()); // Ensure there's a setter for ID
            tripDataHolder.setTravels(trip.getTravels());
//            tripDataHolder.setDepartureTime(trip.getDepartureTime());
//            tripDataHolder.setArrivalTime(trip.getArrivalTime());
//            tripDataHolder.setDuration(trip.getDuration());
            tripDataHolder.setFare(trip.getLowestBaseFare());

            Intent intent = new Intent(view.getContext(), Bus_Seating_view.class);
            view.getContext().startActivity(intent);

            Log.d("TripAdapter", "Trip data saved in TripDataHolder and launching Bus_Seating_view");
        });

    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView busType, busRoutes, travels, departureTime, arrivalTime, duration, price, availableSeats;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            busType = itemView.findViewById(R.id.busType);
            travels = itemView.findViewById(R.id.travels);
            departureTime = itemView.findViewById(R.id.departureTime);
            arrivalTime = itemView.findViewById(R.id.arrivalTime);
            duration = itemView.findViewById(R.id.duration);
            price = itemView.findViewById(R.id.price);
            availableSeats = itemView.findViewById(R.id.availableSeats);
        }
    }
}