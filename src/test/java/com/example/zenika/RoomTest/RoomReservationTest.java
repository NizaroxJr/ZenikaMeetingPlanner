package com.example.zenika.RoomTest;

import com.example.zenika.Reservations.RcReservation;
import com.example.zenika.Reservations.RsReservation;
import com.example.zenika.Reservations.SpecReservation;
import com.example.zenika.Reservations.VcReservation;
import com.example.zenika.Decorators.ReservationDecorator;
import com.example.zenika.Decorators.RoomDecorator;
import com.example.zenika.enums.Tool;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

class RoomReservationTest {


    @Test
    void appropriateNumberOfPeopleToRoom() {
        RoomDecorator RoomDecorator=new RoomDecorator(10, List.of(Tool.TABLEAU, Tool.ECRAN));
        ReservationDecorator reservationwrapper=new ReservationDecorator(9, new RsReservation(), 8);
        assertThat(RoomDecorator.canAccommodateReservation(reservationwrapper)).isFalse();
    }


    @Test
    void CannotAssignRsReservationToRoom() {
        RoomDecorator RoomDecorator=new RoomDecorator(3, List.of(Tool.TABLEAU, Tool.ECRAN));
        ReservationDecorator reservationwrapper=new ReservationDecorator(9, new RsReservation(), 3);
        assertThat(RoomDecorator.canAccommodateReservation(reservationwrapper)).isFalse();
    }

    @Test
    void CanAssignRsReservationToRoom() {
        RoomDecorator RoomDecorator=new RoomDecorator(10, List.of(Tool.TABLEAU, Tool.ECRAN));
        ReservationDecorator ReservationDecorator=new ReservationDecorator(9, new RsReservation(), 3);
        assertThat(RoomDecorator.canAccommodateReservation(ReservationDecorator)).isTrue();
    }
    @Test
    void CannotAssignRcReservationToRoom() {
        RoomDecorator RoomDecorator=new RoomDecorator(10, List.of(Tool.ECRAN, Tool.PIEUVRE));
        ReservationDecorator reservationwrapper=new ReservationDecorator(9, new RcReservation(), 3);
        assertThat(RoomDecorator.canAccommodateReservation(reservationwrapper)).isFalse();
    }

    @Test
    void CanAssignRcReservationToRoom() { // No Board but num of people with capacity are good
        RoomDecorator RoomDecorator=new RoomDecorator(10, List.of(Tool.ECRAN, Tool.PIEUVRE));
        ReservationDecorator ReservationDecorator=new ReservationDecorator(9, new RcReservation(), 3);
        assertThat(RoomDecorator.canAccommodateReservation(ReservationDecorator)).isFalse();
    }
    @Test
    void CannotAssignSpecReservationToRoom() {
        RoomDecorator RoomDecorator=new RoomDecorator(10, List.of(Tool.ECRAN, Tool.PIEUVRE));
        ReservationDecorator reservationwrapper=new ReservationDecorator(9, new SpecReservation(), 3);
        assertThat(RoomDecorator.canAccommodateReservation(reservationwrapper)).isFalse();
    }

    @Test
    void CanAssignSpecReservationToRoom() { // All Tools(Only Needs A Board) and the num of people with capacity are good
        RoomDecorator RoomDecorator=new RoomDecorator(10, List.of(Tool.TABLEAU, Tool.ECRAN));
        ReservationDecorator ReservationDecorator=new ReservationDecorator(9, new SpecReservation(), 3);
        assertThat(RoomDecorator.canAccommodateReservation(ReservationDecorator)).isTrue();
    }

    @Test
    void CannotAssignVcReservationToRoom() {
        RoomDecorator RoomDecorator=new RoomDecorator(10, List.of(Tool.ECRAN, Tool.PIEUVRE));
        ReservationDecorator reservationwrapper=new ReservationDecorator(9, new VcReservation(), 3);
        assertThat(RoomDecorator.canAccommodateReservation(reservationwrapper)).isFalse();
    }

    @Test
    void CanAssignVcReservationToRoom() {
        RoomDecorator RoomDecorator=new RoomDecorator(10, List.of(Tool.WEBCAM, Tool.ECRAN, Tool.PIEUVRE));
        ReservationDecorator ReservationDecorator=new ReservationDecorator(9, new VcReservation(), 3);
        assertThat(RoomDecorator.canAccommodateReservation(ReservationDecorator)).isTrue();
    }
    
    @Test
    void CannotAssignOccupiedRoom() {
        RoomDecorator RoomDecorator=new RoomDecorator(10, List.of(Tool.WEBCAM, Tool.ECRAN, Tool.PIEUVRE));
        ReservationDecorator reservationwrapper=new ReservationDecorator(8, new VcReservation(), 3);
        RoomDecorator.addReservation(reservationwrapper);
        ReservationDecorator reservationwrapper2=new ReservationDecorator(8, new VcReservation(), 3);
        assertThat(RoomDecorator.isReservationPossible(reservationwrapper2)).isFalse();
    }

    @Test
    void CannotAssignRoomAtLastHour() {
        RoomDecorator RoomDecorator=new RoomDecorator(10, List.of(Tool.WEBCAM, Tool.ECRAN, Tool.PIEUVRE));
        ReservationDecorator reservationwrapper=new ReservationDecorator(20, new VcReservation(), 3);
        assertThat(RoomDecorator.isReservationPossible(reservationwrapper)).isFalse();
    }
}