package com.example.zenika.Services;

import com.example.zenika.Models.Room;
import com.example.zenika.Repositories.RoomRepository;
import com.example.zenika.Decorators.RoomDecorator;
import com.example.zenika.enums.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> generateRooms() {
        //Generating Rooms
        List<RoomDecorator> rooms=Arrays.asList(
                new RoomDecorator("E1001", 23, Arrays.asList(Tool.NEANT)),
            new RoomDecorator("E1002", 10, Arrays.asList(Tool.ECRAN)),
            new RoomDecorator("E1003", 8, Arrays.asList(Tool.PIEUVRE)),
            new RoomDecorator("E1004", 4, Arrays.asList(Tool.TABLEAU)),

            new RoomDecorator("E2001", 4, Arrays.asList(Tool.NEANT)),
            new RoomDecorator("E2002", 15, Arrays.asList(Tool.ECRAN, Tool.WEBCAM)),
            new RoomDecorator("E2003", 7, Arrays.asList(Tool.NEANT)),
            new RoomDecorator("E2004", 9, Arrays.asList(Tool.TABLEAU)),

            new RoomDecorator("E3001", 13, Arrays.asList(Tool.ECRAN, Tool.WEBCAM, Tool.PIEUVRE)),
            new RoomDecorator("E3002", 8, Arrays.asList(Tool.NEANT)),
            new RoomDecorator("E3003", 9, Arrays.asList(Tool.ECRAN, Tool.PIEUVRE)),
            new RoomDecorator("E3004", 4, Arrays.asList(Tool.NEANT))
        );

        for (RoomDecorator room: rooms) {
            Room newRoom=Room.builder().
                    name(room.getName())
                    .capacity(room.getRealCapacity()).availableTools(room.getAvailableTools())
                    .build();
            this.roomRepository.save(newRoom);
        };
        return this.roomRepository.findAll();
    }

    public List<Room> getRooms() {
        return this.roomRepository.findAll();
    }
}
