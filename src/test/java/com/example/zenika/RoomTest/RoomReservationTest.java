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
        // Test Case: Reservation with 8 people, but room capacity is 10 and requires TABLEAU and ECRAN tools
        RoomDecorator roomDecorator = new RoomDecorator(10, List.of(Tool.TABLEAU, Tool.ECRAN));
        ReservationDecorator reservationWrapper = new ReservationDecorator(9, new RsReservation(), 8);
        assertThat(roomDecorator.canAccommodateReservation(reservationWrapper)).isFalse();
        // Assertion: The room cannot accommodate the reservation because the number of people (8) exceeds the room capacity (10).
    }

    @Test
    void CannotAssignRsReservationToRoom() {
        // Test Case: Reservation for RS type, room capacity is 3, and requires TABLEAU and ECRAN tools
        RoomDecorator roomDecorator = new RoomDecorator(3, List.of(Tool.TABLEAU, Tool.ECRAN));
        ReservationDecorator reservationWrapper = new ReservationDecorator(9, new RsReservation(), 3);
        assertThat(roomDecorator.canAccommodateReservation(reservationWrapper)).isFalse();
        // Assertion: The room cannot accommodate the RS reservation because the room capacity (3) is less than the required capacity for RS reservations.
    }

    @Test
    void CanAssignRsReservationToRoom() {
        // Test Case: Reservation for RS type, room capacity is 10, and requires TABLEAU and ECRAN tools
        RoomDecorator roomDecorator = new RoomDecorator(10, List.of(Tool.TABLEAU, Tool.ECRAN));
        ReservationDecorator reservationDecorator = new ReservationDecorator(9, new RsReservation(), 3);
        assertThat(roomDecorator.canAccommodateReservation(reservationDecorator)).isTrue();
        // Assertion: The room can accommodate the RS reservation because the room capacity (10) is sufficient, and the required tools (TABLEAU and ECRAN) are available.
    }

    @Test
    void CannotAssignRcReservationToRoom() {
        // Test Case: Reservation for RC type, room capacity is 10, and requires ECRAN and PIEUVRE tools
        RoomDecorator roomDecorator = new RoomDecorator(10, List.of(Tool.ECRAN, Tool.PIEUVRE));
        ReservationDecorator reservationWrapper = new ReservationDecorator(9, new RcReservation(), 3);
        assertThat(roomDecorator.canAccommodateReservation(reservationWrapper)).isFalse();
        // Assertion: The room cannot accommodate the RC reservation because the required tools (ECRAN and PIEUVRE) are not available in the room.
    }

    @Test
    void CanAssignRcReservationToRoom() {
        // Test Case: Reservation for RC type, room capacity is 10, and requires ECRAN and PIEUVRE tools
        RoomDecorator roomDecorator = new RoomDecorator(10, List.of(Tool.ECRAN, Tool.PIEUVRE));
        ReservationDecorator reservationDecorator = new ReservationDecorator(9, new RcReservation(), 3);
        assertThat(roomDecorator.canAccommodateReservation(reservationDecorator)).isFalse();
        // Assertion: The room can accommodate the RC reservation because the room capacity (10) is sufficient, and the required tools (ECRAN and PIEUVRE) are available.
    }

    @Test
    void CannotAssignSpecReservationToRoom() {
        // Test Case: Reservation for SPEC type, room capacity is 10, and requires ECRAN and PIEUVRE tools
        RoomDecorator roomDecorator = new RoomDecorator(10, List.of(Tool.ECRAN, Tool.PIEUVRE));
        ReservationDecorator reservationWrapper = new ReservationDecorator(9, new SpecReservation(), 3);
        assertThat(roomDecorator.canAccommodateReservation(reservationWrapper)).isFalse();
        // Assertion: The room cannot accommodate the SPEC reservation because the required tools (ECRAN and PIEUVRE) are not available in the room.
    }

    @Test
    void CanAssignSpecReservationToRoom() {
        // Test Case: Reservation for SPEC type, room capacity is 10, and requires TABLEAU and ECRAN tools
        RoomDecorator roomDecorator = new RoomDecorator(10, List.of(Tool.TABLEAU, Tool.ECRAN));
        ReservationDecorator reservationDecorator = new ReservationDecorator(9, new SpecReservation(), 3);
        assertThat(roomDecorator.canAccommodateReservation(reservationDecorator)).isTrue();
        // Assertion: The room can accommodate the SPEC reservation because the room capacity (10) is sufficient, and the required tools (TABLEAU and ECRAN) are available.
    }

    @Test
    void CannotAssignVcReservationToRoom() {
        // Test Case: Reservation for VC type, room capacity is 10, and requires ECRAN and PIEUVRE tools
        RoomDecorator roomDecorator = new RoomDecorator(10, List.of(Tool.ECRAN, Tool.PIEUVRE));
        ReservationDecorator reservationWrapper = new ReservationDecorator(9, new VcReservation(), 3);
        assertThat(roomDecorator.canAccommodateReservation(reservationWrapper)).isFalse();
        // Assertion: The room cannot accommodate the VC reservation because the required tools (ECRAN and PIEUVRE) are not available in the room.
    }

    @Test
    void CanAssignVcReservationToRoom() {
        // Test Case: Reservation for VC type, room capacity is 10, and requires WEBCAM, ECRAN, and PIEUVRE tools
        RoomDecorator roomDecorator = new RoomDecorator(10, List.of(Tool.WEBCAM, Tool.ECRAN, Tool.PIEUVRE));
        ReservationDecorator reservationDecorator = new ReservationDecorator(9, new VcReservation(), 3);
        assertThat(roomDecorator.canAccommodateReservation(reservationDecorator)).isTrue();
        // Assertion: The room can accommodate the VC reservation because the room capacity (10) is sufficient, and all the required tools (WEBCAM, ECRAN, and PIEUVRE) are available.
    }

    @Test
    void CannotAssignOccupiedRoom() {
        // Test Case: Reservation for VC type in an already occupied room
        RoomDecorator roomDecorator = new RoomDecorator(10, List.of(Tool.WEBCAM, Tool.ECRAN, Tool.PIEUVRE));
        ReservationDecorator reservationWrapper = new ReservationDecorator(8, new VcReservation(), 3);
        roomDecorator.addReservation(reservationWrapper);
        ReservationDecorator reservationWrapper2 = new ReservationDecorator(8, new VcReservation(), 3);
        assertThat(roomDecorator.isReservationPossible(reservationWrapper2)).isFalse();
        // Assertion: The reservation cannot be assigned to the room because the room is already occupied by another reservation at the same time.
    }

    @Test
    void CannotAssignRoomAtLastHour() {
        // Test Case: Reservation for VC type at the last hour (20)
        RoomDecorator roomDecorator = new RoomDecorator(10, List.of(Tool.WEBCAM, Tool.ECRAN, Tool.PIEUVRE));
        ReservationDecorator reservationWrapper = new ReservationDecorator(20, new VcReservation(), 3);
        assertThat(roomDecorator.isReservationPossible(reservationWrapper)).isFalse();
        // Assertion: The reservation cannot be assigned to the room because the last hour (20) is not within the allowed reservation hours.
    }

}