package com.example.zenika.Reservations;

import com.example.zenika.Interfaces.ReservationType;
import com.example.zenika.enums.Tool;

import java.util.ArrayList;

public class RsReservation implements ReservationType {
    private static ArrayList<Tool> requiredTools=new ArrayList<>();

    public ArrayList<Tool> getRequiredTools() {
        return requiredTools;
    }
}
