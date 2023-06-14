package com.example.zenika.Reservations;

import com.example.zenika.Interfaces.ReservationType;

public class ResevationTypeBuilder {

    public static ReservationType getReservationTypeFromReservationType(com.example.zenika.enums.ReservationType reservationType){
        if(reservationType== com.example.zenika.enums.ReservationType.VC)return new VcReservation();
        if(reservationType== com.example.zenika.enums.ReservationType.RS)return new RsReservation();
        if(reservationType== com.example.zenika.enums.ReservationType.SPEC)return new SpecReservation();
        return new RcReservation();
    }
}
