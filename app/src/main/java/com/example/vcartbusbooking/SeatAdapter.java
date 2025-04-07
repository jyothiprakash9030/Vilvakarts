package com.example.vcartbusbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    private List<Seat> seats;
    private Consumer<Seat> onSeatClickListener;
    private Context context;
    private int seatSize;
     LinearLayout footer;

    public SeatAdapter(List<Seat> seats, Consumer<Seat> onSeatClickListener, int seatSize) {
        this.seats = seats;
        this.onSeatClickListener = onSeatClickListener;
        this.seatSize = seatSize;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_seat, parent, false);
        return new SeatViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Seat seat = seats.get(position);
        if (seat == null) {
            holder.itemView.setVisibility(View.INVISIBLE);
            return;
        }

        holder.itemView.setVisibility(View.VISIBLE);
        holder.seatName.setText(seat.getName());

        boolean isSleeper = seat.getLength() == 2 || seat.getWidth() == 2;

        // Handle driver seat


        // Set seat image based on type, availability, ladies status, and selection
        if (seat.isSelected() && seat.isAvailable()) {
            holder.seatImage.setImageResource(
                    isSleeper ? R.drawable.sleeper_seat : R.drawable.seat_highlight_selected
            );
        } else if (seat.isLadiesSeat() && seat.isAvailable()) {
            holder.seatImage.setImageResource(
                    isSleeper ? R.drawable.lad_sleeper_avl : R.drawable.lad_seat_avl
            );
        } else if (seat.isLadiesSeat() && !seat.isAvailable()) {
            holder.seatImage.setImageResource(
                    isSleeper ? R.drawable.lad_sleeper_bkd : R.drawable.lad_seat_bkd
            );
        } else if (!seat.isLadiesSeat() && seat.isAvailable()) {
            holder.seatImage.setImageResource(
                    isSleeper ? R.drawable.sleeper_aval : R.drawable.seat_avl
            );
        } else {
            holder.seatImage.setImageResource(
                    isSleeper ? R.drawable.sleeper_unavailable : R.drawable.seat_bkd
            );
        }

        holder.itemView.setOnClickListener(v -> {
            if (seat.isAvailable()) {
                seat.setSelected(!seat.isSelected()); // Toggle selection
                notifyItemChanged(position); // Update the view
                onSeatClickListener.accept(seat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    static class SeatViewHolder extends RecyclerView.ViewHolder {
        ImageView seatImage;
        TextView seatName;

        SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            seatImage = itemView.findViewById(R.id.seatImage);
            seatName = itemView.findViewById(R.id.seatName);
        }
    }
}
