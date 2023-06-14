package com.example.zenika.Reservations;

import com.example.zenika.Interfaces.ReservationType;
import com.example.zenika.enums.Tool;

import java.util.ArrayList;

public class RcReservation implements ReservationType {
    private static ArrayList<Tool> requiredTools=new ArrayList<>();
    {
        requiredTools.add(Tool.TABLEAU);
        requiredTools.add(Tool.ECRAN);
        requiredTools.add(Tool.PIEUVRE);
    }

    public ArrayList<Tool> getRequiredTools() {
        return requiredTools;
    }
}
