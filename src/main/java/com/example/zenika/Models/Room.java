package com.example.zenika.Models;

import com.example.zenika.enums.Tool;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Room {
    @Id
    @SequenceGenerator(
            name="room_sequence",
            sequenceName = "room_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "room_sequence"
    )
    private Long id;
    private String name;
    private int capacity;

    @ElementCollection(targetClass = Tool.class)
    @Enumerated(EnumType.STRING)
    private List<Tool> availableTools= new ArrayList<>();
}
