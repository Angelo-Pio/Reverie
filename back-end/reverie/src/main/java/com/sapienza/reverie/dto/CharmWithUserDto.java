package com.sapienza.reverie.dto;

import lombok.Data;

@Data
public class CharmWithUserDto {

    private UserDto user;
    private CharmDto charm;
    private CommentDto comment;


}
