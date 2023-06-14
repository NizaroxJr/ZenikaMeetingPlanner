package com.example.zenika.Decorators;

import com.example.zenika.Interfaces.ReservationType;
import com.example.zenika.Models.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDecorator {
    private static long generatedReservations=0;
    private long id;
    private int startingHour;
    private ReservationType reservationType;
    private int numberOfPeople;

    public ReservationDecorator(int startAt, ReservationType reservationType, int numberOfPeople) {
        this.id=++generatedReservations;
        this.startingHour = startAt;
        this.reservationType = reservationType;
        this.numberOfPeople = numberOfPeople;
    }
}
