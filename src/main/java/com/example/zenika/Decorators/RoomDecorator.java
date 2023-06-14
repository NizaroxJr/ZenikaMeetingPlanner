package com.example.zenika.Decorators;

import com.example.zenika.Models.Room;
import com.example.zenika.enums.Tool;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDecorator{
    private static double fullRatio=0.7;
    private static double cleaningDuration=1;
    private static int minReservationHour=8;
    private static int maxReservationHour=20;
    private static int reservationDuration=1;
    private static long generatedRooms=0;
    private long id;
    private String name;
    private int realCapacity;
    List<Tool> availableTools;
    List<ReservationDecorator> reservations=new ArrayList<>();

    public RoomDecorator(String name, int realCapacity, List<Tool> availableTools) {
        this.name = name;
        this.id=++generatedRooms;
        this.realCapacity = realCapacity;
        this.availableTools = availableTools;
        this.reservations= new ArrayList<>();
    }

    public RoomDecorator(int realCapacity, List<Tool> availableTools, List<ReservationDecorator> reservations) {
        this.realCapacity = realCapacity;
        this.availableTools = availableTools;
        this.reservations = reservations;
    }

    public RoomDecorator(int realCapacity, List<Tool> availableTools) {
        this.realCapacity = realCapacity;
        this.availableTools = availableTools;
    }
    public boolean canAccommodateReservation(ReservationDecorator reservation) {
        // Check if the reservation's number of people can be assigned to the room's capacity
        boolean isCapacityAssignable = reservation.getNumberOfPeople() <= (fullRatio * this.realCapacity);

        // If the capacity can be assigned, continue with additional checks
        if (isCapacityAssignable) {
            // Check if the reservation type requires any tools
            if (reservation.getReservationType().getRequiredTools().isEmpty()) {
                // If no tools are required, check if the adjusted capacity is greater than 3
                return (fullRatio * this.realCapacity) > 3;
            } else {
                boolean hasAllTools = true;
                // Iterate through the required tools of the reservation type
                for (Tool requiredTool : reservation.getReservationType().getRequiredTools()) {
                    // Check if the room has all the required tools
                    if (!this.availableTools.contains(requiredTool)) {
                        hasAllTools = false;
                        break;
                    }
                }
                return hasAllTools;
            }
        }

        // If the capacity cannot be assigned, return false
        return false;
    }


    public boolean isReservationPossible(ReservationDecorator reservation) {
        int reservationDuration = RoomDecorator.reservationDuration;

        // Check if the reservation can be accommodated
        boolean canAccommodate = this.canAccommodateReservation(reservation);

        // If the reservation cannot be accommodated, return false
        if (!canAccommodate) {
            return false;
        }

        int startingHour = reservation.getStartingHour();
        int endingHour = startingHour + reservationDuration;

        // Check if the reservation falls within the allowable reservation hours
        if (startingHour < minReservationHour || endingHour > maxReservationHour) {
            return false;
        }

        // Iterate through existing reservations to check for conflicts
        for (ReservationDecorator existingReservation : this.reservations) {
            int existingStartHour = existingReservation.getStartingHour();
            int existingEndHour = existingStartHour + reservationDuration;

            // Check if the new reservation overlaps with an existing reservation at the start time
            if (existingStartHour == minReservationHour && startingHour < existingEndHour + cleaningDuration) {
                return false;
            }

            // Check if the new reservation overlaps with an existing reservation at the end time
            if (existingEndHour == maxReservationHour && endingHour > existingStartHour - cleaningDuration) {
                return false;
            }

            // Check if there is no overlap between the new reservation and the existing reservation
            boolean isNoOverlap = (endingHour <= existingStartHour - cleaningDuration) ||
                    (startingHour >= existingEndHour + cleaningDuration);

            // If there is an overlap, return false
            if (!isNoOverlap) {
                return false;
            }
        }

        // If all checks pass, the reservation is possible
        return true;
    }



    public void addReservation(ReservationDecorator reservationDecorator){
        this.reservations.add(reservationDecorator);
    }

    public static double getFullRatio() {
        return fullRatio;
    }

    public static void setFullRatio(double fullRatio) {
        RoomDecorator.fullRatio = fullRatio;
    }
}
