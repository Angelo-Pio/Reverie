package com.sapienza.reverie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentDto {

    private CommentDto commentDto;
    private UserDto userDto;
}
