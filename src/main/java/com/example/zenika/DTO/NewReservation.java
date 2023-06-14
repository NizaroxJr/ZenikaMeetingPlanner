package com.example.zenika.DTO;

import com.example.zenika.enums.ReservationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewReservation {
    private int startingHour;
    private ReservationType reservationType;
    private int numOfPeople;

}
