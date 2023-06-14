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
        // Select an available room for the new reservation
        Room selectedRoom = this.selectAvailableRoom(newReservation);

        // Check if no room is available
        if (selectedRoom == null) {
            return new Response<>(
                    true,
                    "We are fully booked for " + newReservation.getReservationType() +
                            " Reservations. We apologize for the inconvenience. Please try another reservation type."
            );
        }

        // Create a new reservation object with the selected room and provided details
        Reservation reservation = Reservation.builder()
                .room(selectedRoom)
                .startingHour(newReservation.getStartingHour())
                .numberOfPeople(newReservation.getNumOfPeople())
                .reservationType(newReservation.getReservationType())
                .build();

        // Save the reservation to the repository
        reservationRepository.save(reservation);

        return new Response<>(
                "Your reservation for room " + selectedRoom.getName() +
                        " has been successfully registered. We look forward to hosting you!",
                reservation
        );
    }



    private Room selectAvailableRoom(NewReservation newReservation) {
        // Retrieve all rooms from the repository
        List<Room> rooms = roomRepository.findAll();
        // Retrieve all reservations from the repository
        List<Reservation> reservations = reservationRepository.findAll();

        Room selectedRoom = null;
        int minCapacityDifference = Integer.MAX_VALUE;

        // Iterate through each room to find an available one
        for (Room room : rooms) {
            // Store the current reservations for the room
            List<ReservationDecorator> currentReservations = new ArrayList<>();

            // Iterate through each reservation to check if it belongs to the current room
            for (Reservation reservation : reservations) {
                if (reservation.getRoom().getId() == room.getId()) {
                    // Create a decorator for the reservation and add it to the current reservations list
                    currentReservations.add(new ReservationDecorator(
                            reservation.getStartingHour(),
                            ResevationTypeBuilder.getReservationTypeFromReservationType(reservation.getReservationType()),
                            reservation.getNumberOfPeople()
                    ));
                }
            }

            // Create a decorator for the room
            RoomDecorator RoomDecorator = new RoomDecorator(
                    room.getCapacity(),
                    room.getAvailableTools(),
                    currentReservations
            );

            // Create a decorator for the new reservation
            ReservationDecorator newReservationWrapper = new ReservationDecorator(
                    newReservation.getStartingHour(),
                    ResevationTypeBuilder.getReservationTypeFromReservationType(newReservation.getReservationType()),
                    newReservation.getNumOfPeople()
            );

            // Check if the new reservation can be accommodated in the current room
            if (RoomDecorator.isReservationPossible(newReservationWrapper)) {
                // Calculate the difference in capacity after accommodating the new reservation
                int capacityDifference = (int) (RoomDecorator.getFullRatio() * room.getCapacity()) - newReservation.getNumOfPeople();
                // Check if the current room has a smaller capacity difference compared to the previous selected room
                if (minCapacityDifference > capacityDifference) {
                    // Update the selected room and the minimum capacity difference
                    minCapacityDifference = capacityDifference;
                    selectedRoom = room;
                }
            }
        }

        // Return the selected room
        return selectedRoom;
    }



}
