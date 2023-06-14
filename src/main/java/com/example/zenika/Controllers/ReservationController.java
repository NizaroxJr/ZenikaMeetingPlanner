package com.example.zenika.Controllers;

import com.example.zenika.Models.Reservation;
import com.example.zenika.Services.ReservationService;
import com.example.zenika.DTO.NewReservation;
import com.example.zenika.DTO.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("reservations")
@RestController
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public Response<Reservation> createReservation(@RequestBody NewReservation newReservation){
        return this.reservationService.createReservation(newReservation);
    }
    @GetMapping
    public List<Reservation> getReservations(){
        return this.reservationService.getReservations();
    }
}
