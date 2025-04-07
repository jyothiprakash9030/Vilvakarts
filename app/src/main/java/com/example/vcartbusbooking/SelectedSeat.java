package com.example.vcartbusbooking;

public class SelectedSeat {
    private String seatName;
    private String deck; // "Upper" or "Lower"

    public SelectedSeat(String seatName, String deck) {
        this.seatName = seatName;
        this.deck = deck;
    }

    public String getSeatName() {
        return seatName;
    }

    public String getDeck() {
        return deck;
    }

    @Override
    public String toString() {
        return deck + " Deck: " + seatName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectedSeat that = (SelectedSeat) o;
        return seatName.equals(that.seatName) && deck.equals(that.deck);
    }

    @Override
    public int hashCode() {
        int result = seatName.hashCode();
        result = 31 * result + deck.hashCode();
        return result;
    }
}