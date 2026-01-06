package com.sapienza.reverie.Service;

import com.sapienza.reverie.Model.Charm;
import com.sapienza.reverie.Model.Comment;
import com.sapienza.reverie.Model.User;
import com.sapienza.reverie.dto.CharmDto;
import com.sapienza.reverie.dto.CharmWithUserDto;
import com.sapienza.reverie.dto.CommentDto;
import com.sapienza.reverie.dto.UserDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class Mapper {

    public static CharmWithUserDto toCharmWithUserDto(Charm charm, User user,Comment comment) {

        CharmWithUserDto charmWithUserDto = new CharmWithUserDto();
        charmWithUserDto.setCharm(toCharmDto(charm));
        charmWithUserDto.setUser(toUserDto(user));
        charmWithUserDto.setComment(toCommentDto(comment));
        return charmWithUserDto;
    }

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setProfilePictureUrl(user.getProfilePictureUrl());
        return userDto;
    }

    public static CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setId(comment.getId());
        commentDto.setCreated_at(comment.getCreated_at());
        return commentDto;
    }

    public static List<CommentDto> toCommentDtoList(List<Comment> comments) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtoList.add(toCommentDto(comment));
        }
        return commentDtoList;

    }

    public static CharmDto toCharmDto(Charm charm) {
        CharmDto charmDto = new CharmDto();
        charmDto.setId(charm.getId());
        charmDto.setComments(toCommentDtoList(charm.getComments()));
        charmDto.setPictureUrl(charm.getPictureUrl());
        charmDto.setDescription(charm.getDescription());
        charmDto.setCreated_at(charm.getCreated_at());
        charmDto.setCreator(charm.getCreator().getUsername());
        charmDto.setCollected_in(charm.getCollected_in());
        return charmDto;
    }

    public static List<CharmDto> toCharmDtoList(List<Charm> charms) {
        List<CharmDto> charmDtoList = new ArrayList<>();
        for (Charm charm : charms) {
            charmDtoList.add(toCharmDto(charm));
        }
        return charmDtoList;
    }
}
