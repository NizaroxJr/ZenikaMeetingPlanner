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
import org.junit.jupiter.api.*;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationServiceMondayTest {
    private static List<Room> roomsList=new ArrayList<>();
    private List<Reservation> reservationList=new ArrayList<>();
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private ReservationService  reservationTestService;

    @BeforeAll
    static void beforeAll() {
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

        for (RoomDecorator room : rooms) {
            Room newRoom = Room.builder()
                    .name(room.getName())
                    .capacity(room.getRealCapacity())
                    .availableTools(room.getAvailableTools())
                    .build();
            roomsList.add(newRoom);
        }
    }

    @BeforeEach
    public void setUp() {
        when(roomRepository.findAll()).thenReturn(roomsList);
        when(reservationRepository.findAll()).thenReturn(reservationList);
    }
    @Test
    @Order(1)
    void MondayUseCaseTest() {
        NewReservation newReservation1 = new NewReservation(9, ReservationType.VC, 8);
        Response<Reservation> result1 = this. reservationTestService.createReservation(newReservation1);
        Reservation res1=result1.getData();
        res1.setRoom(roomsList.get(0));
        reservationList.add(res1);
        assertThat(result1.isError()).isFalse();

        NewReservation newReservation2 = new NewReservation(9, ReservationType.VC, 6);
        Response<Reservation> result2 = this. reservationTestService.createReservation(newReservation2);
        assertThat(result2.isError()).isTrue();

        NewReservation newReservation3=new NewReservation(11, ReservationType.RC, 4);
        Response<Reservation> result3=this. reservationTestService.createReservation(newReservation3);
        assertThat(result3.isError()).isTrue();

        NewReservation newReservation4=new NewReservation(11, ReservationType.RS, 2);
        Response<Reservation> result4=this. reservationTestService.createReservation(newReservation4);
        System.out.println(result4.getMessage());
        Reservation res4=result4.getData();
        res4.setRoom(roomsList.get(2));
        reservationList.add(res4);
        assertThat(result4.isError()).isFalse();

        NewReservation newReservation5=new NewReservation(11, ReservationType.SPEC, 9);
        Response<Reservation> result5=this. reservationTestService.createReservation(newReservation5);
        assertThat(result5.isError()).isTrue();

        NewReservation newReservation6=new NewReservation(9, ReservationType.RC, 7);
        Response<Reservation> result6=this. reservationTestService.createReservation(newReservation6);
        assertThat(result6.isError()).isTrue();

        NewReservation newReservation7 = new NewReservation(8, ReservationType.VC, 9);
        Response<Reservation> result7 = this. reservationTestService.createReservation(newReservation7);
        assertThat(result7.isError()).isTrue();

        NewReservation newReservation8 = new NewReservation(8, ReservationType.SPEC, 10);
        Response<Reservation> result8 = this. reservationTestService.createReservation(newReservation8);
        assertThat(result8.isError()).isTrue();

    }

}
