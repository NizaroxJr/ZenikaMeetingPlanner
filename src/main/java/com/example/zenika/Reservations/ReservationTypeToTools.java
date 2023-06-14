package com.example.zenika.Reservations;

import com.example.zenika.enums.ReservationType;
import com.example.zenika.enums.Tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationTypeToTools {
    public static Map<ReservationType, List<Tool>> reservationTypeToTools(){
        Map<ReservationType, List<Tool>> typeToTools=new HashMap<>();
        typeToTools.put(ReservationType.VC, List.of(Tool.PIEUVRE, Tool.ECRAN, Tool.WEBCAM));
        typeToTools.put(ReservationType.SPEC, List.of(Tool.TABLEAU));
        typeToTools.put(ReservationType.RC, List.of(Tool.PIEUVRE, Tool.ECRAN, Tool.TABLEAU));
        typeToTools.put(ReservationType.RS, List.of());
        return typeToTools;
    }
}
