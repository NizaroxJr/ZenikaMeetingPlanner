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
        boolean isCapacityAssignable = reservation.getNumberOfPeople() <= (fullRatio * this.realCapacity);

        if (isCapacityAssignable) {
            if (reservation.getReservationType().getRequiredTools().isEmpty()) {
                return (fullRatio * this.realCapacity) > 3;
            } else {
                boolean hasAllTools = true;
                for (Tool requiredTool : reservation.getReservationType().getRequiredTools()) {
                    if (!this.availableTools.contains(requiredTool)) {
                        hasAllTools = false;
                        break;
                    }
                }
                return hasAllTools;
            }
        }

        return false;
    }

    public boolean isReservationPossible(ReservationDecorator reservation) {
        int reservationDuration = RoomDecorator.reservationDuration;
        boolean canAccommodate = this.canAccommodateReservation(reservation);

        if (!canAccommodate) {
            return false;
        }

        int startingHour = reservation.getStartingHour();
        int endingHour = startingHour + reservationDuration;

        if (startingHour < minReservationHour || endingHour > maxReservationHour) {
            return false;
        }

        for (ReservationDecorator existingReservation : this.reservations) {
            int existingStartHour = existingReservation.getStartingHour();
            int existingEndHour = existingStartHour + reservationDuration;

            if (existingStartHour == minReservationHour && startingHour < existingEndHour + cleaningDuration) {
                return false;
            }

            if (existingEndHour == maxReservationHour && endingHour > existingStartHour - cleaningDuration) {
                return false;
            }

            boolean isNoOverlap = (endingHour <= existingStartHour - cleaningDuration) ||
                    (startingHour >= existingEndHour + cleaningDuration);

            if (!isNoOverlap) {
                return false;
            }
        }

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
