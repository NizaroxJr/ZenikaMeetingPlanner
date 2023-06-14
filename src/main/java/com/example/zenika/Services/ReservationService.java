package com.example.zenika.Services;

import com.example.zenika.Models.Reservation;
import com.example.zenika.Models.Room;
import com.example.zenika.Repositories.ReservationRepository;
import com.example.zenika.DTO.NewReservation;
import com.example.zenika.Repositories.RoomRepository;
import com.example.zenika.Reservations.ResevationTypeBuilder;
import com.example.zenika.Decorators.ReservationDecorator;
import com.example.zenika.DTO.Response;
import com.example.zenika.Decorators.RoomDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }


    public List<Reservation> getReservations(){
        return this.reservationRepository.findAll();
    }

    public Response<Reservation> createReservation(NewReservation newReservation) {
        Room selectedRoom = this.selectAvailableRoom(newReservation);

        if (selectedRoom == null) {
            return new Response<>(
                    true,
                    "We are fully booked for " + newReservation.getReservationType() +
                            " Reservations. We apologize for the inconvenience. Please try another reservation type."
            );
        }

        Reservation reservation = Reservation.builder()
                .room(selectedRoom)
                .startingHour(newReservation.getStartingHour())
                .numberOfPeople(newReservation.getNumOfPeople())
                .reservationType(newReservation.getReservationType())
                .build();

        reservationRepository.save(reservation);

        return new Response<>(
                "Your reservation for room " + selectedRoom.getName() +
                        " has been successfully registered. We look forward to hosting you!",
                reservation
        );
    }


    private Room selectAvailableRoom(NewReservation newReservation) {
        List<Room> rooms = roomRepository.findAll();
        List<Reservation> reservations = reservationRepository.findAll();

        Room selectedRoom = null;
        int minCapacityDifference = Integer.MAX_VALUE;

        for (Room room : rooms) {
            List<ReservationDecorator> currentReservations = new ArrayList<>();

            for (Reservation reservation : reservations) {
                if (reservation.getRoom().getId() == room.getId()) {
                    currentReservations.add(new ReservationDecorator(
                            reservation.getStartingHour(),
                            ResevationTypeBuilder.getReservationTypeFromReservationType(reservation.getReservationType()),
                            reservation.getNumberOfPeople()
                    ));
                }
            }

            RoomDecorator RoomDecorator = new RoomDecorator(
                    room.getCapacity(),
                    room.getAvailableTools(),
                    currentReservations
            );

            ReservationDecorator newReservationWrapper = new ReservationDecorator(
                    newReservation.getStartingHour(),
                    ResevationTypeBuilder.getReservationTypeFromReservationType(newReservation.getReservationType()),
                    newReservation.getNumOfPeople()
            );

            if (RoomDecorator.isReservationPossible(newReservationWrapper)) {
                int capacityDifference = (int) (RoomDecorator.getFullRatio() * room.getCapacity()) - newReservation.getNumOfPeople();
                if (minCapacityDifference > capacityDifference) {
                    minCapacityDifference = capacityDifference;
                    selectedRoom = room;
                }
            }
        }

        return selectedRoom;
    }


}
