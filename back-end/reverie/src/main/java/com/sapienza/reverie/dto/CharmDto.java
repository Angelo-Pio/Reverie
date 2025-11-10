package com.sapienza.reverie.dto;

import com.sapienza.reverie.Model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CharmDto {

    private Long id;

    private String description;

    private String pictureUrl;

    private LocalDateTime created_at;

    //private User creator;

    //private List<User> collectors;
}
