package com.example.zenika.Controllers;

import com.example.zenika.Models.Room;
import com.example.zenika.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rooms")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getRooms(){
        return this.roomService.getRooms();
    }

    @GetMapping("/generated")
    public List<Room> createRooms(){
        return this.roomService.generateRooms();
    }
}
