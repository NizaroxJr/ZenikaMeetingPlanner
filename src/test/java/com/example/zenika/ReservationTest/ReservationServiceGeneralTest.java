package com.example.zenika.ReservationTest;

import com.example.zenika.Models.Reservation;
import com.example.zenika.Repositories.ReservationRepository;
import com.example.zenika.Services.ReservationService;
import com.example.zenika.DTO.NewReservation;
import com.example.zenika.enums.ReservationType;
import com.example.zenika.Models.Room;
import com.example.zenika.Repositories.RoomRepository;
import com.example.zenika.DTO.Response;
import com.example.zenika.Decorators.RoomDecorator;
import com.example.zenika.enums.Tool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceGeneralTest {
    private static List<Room> roomsList;
    private List<Reservation> reservationList;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private ReservationService  reservationTestService;

    @BeforeEach
    public void setUp() {
        List<RoomDecorator> rooms = Arrays.asList(
                new RoomDecorator("E1001", 23, Arrays.asList(Tool.NEANT)),
                new RoomDecorator("E1002", 10, Arrays.asList(Tool.ECRAN)),
                new RoomDecorator("E1003", 8, Arrays.asList(Tool.PIEUVRE)),
                new RoomDecorator("E1004", 4, Arrays.asList(Tool.TABLEAU)),
                new RoomDecorator("E2001", 4, Arrays.asList(Tool.NEANT)),
                new RoomDecorator("E2002", 15, Arrays.asList(Tool.ECRAN, Tool.WEBCAM)),
                new RoomDecorator("E2003", 7, Arrays.asList(Tool.NEANT)),
                new RoomDecorator("E2004", 9, Arrays.asList(Tool.TABLEAU)),
                new RoomDecorator("E3001", 13, Arrays.asList(Tool.ECRAN, Tool.WEBCAM, Tool.PIEUVRE)),
                new RoomDecorator("E3002", 8, Arrays.asList(Tool.NEANT)),
                new RoomDecorator("E3003", 9, Arrays.asList(Tool.ECRAN, Tool.PIEUVRE)),
                new RoomDecorator("E3004", 4, Arrays.asList(Tool.NEANT))
        );
        roomsList = new ArrayList<>();
        for (RoomDecorator room : rooms) {
            Room newRoom = Room.builder()
                    .name(room.getName())
                    .capacity(room.getRealCapacity())
                    .availableTools(room.getAvailableTools())
                    .build();
            roomsList.add(newRoom);
        }
        reservationList = new ArrayList<>();
        when(roomRepository.findAll()).thenReturn(roomsList);
        when(reservationRepository.findAll()).thenReturn(reservationList);
    }

    // Making a reservation at 4 in the morning
    @Test
    void reservationLeftOutOfRange() {
        // Given
        NewReservation newReservation=new NewReservation(4, ReservationType.RS, 8);
        // When
        Response<Reservation> result=this. reservationTestService.createReservation(newReservation);
        // Then
        assertThat(result.isError()).isTrue();
    }

    @Test
    void reservationRightOutOfRange() {
        // Given
        NewReservation newReservation=new NewReservation(23, ReservationType.RS, 8);
        // When
        Response<Reservation> result=this. reservationTestService.createReservation(newReservation);
        // Then
        assertThat(result.isError()).isTrue();
    }

    @Test
    void reservationInRange() {
        // Given
        NewReservation newReservation=new NewReservation(11, ReservationType.RS, 8);
        // When
        Response<Reservation> result=this. reservationTestService.createReservation(newReservation);
        // Then
        assertThat(result.isError()).isFalse();
    }



}