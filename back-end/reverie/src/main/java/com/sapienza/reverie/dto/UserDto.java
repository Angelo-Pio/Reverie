package com.sapienza.reverie.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private String password;

    private String profilePictureUrl;
}
