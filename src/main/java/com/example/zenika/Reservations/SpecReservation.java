package com.example.zenika.Reservations;

import com.example.zenika.Interfaces.ReservationType;
import com.example.zenika.enums.Tool;

import java.util.ArrayList;

public class SpecReservation implements ReservationType {
    private static ArrayList<Tool> reauiredTools=new ArrayList<>();
    {
        reauiredTools.add(Tool.TABLEAU);
    }

    public ArrayList<Tool> getRequiredTools() {
        return reauiredTools;
    }
}
