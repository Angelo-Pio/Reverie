package com.sapienza.reverie.Controller;

import com.sapienza.reverie.Model.Charm;
import com.sapienza.reverie.Model.Comment;
import com.sapienza.reverie.Model.User;
import com.sapienza.reverie.Repository.CharmRepository;
import com.sapienza.reverie.Repository.CommentRepository;
import com.sapienza.reverie.Repository.UserRepository;
import com.sapienza.reverie.Service.CharmService;
import com.sapienza.reverie.Service.FileStorageService;
import com.sapienza.reverie.Service.Mapper;
import com.sapienza.reverie.dto.CharmDto;
import com.sapienza.reverie.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reverie/api")
public class ReverieController {

    @Autowired
    CharmService charmService;




    /*TODO
     * authentication (sign up page + google sign in)
     * profile picture
     * qr code scanning and generation -> link to image
     * navigation improvements
     *
     * */

    @PostMapping("/charm/comment")
    public ResponseEntity<?> addComment(@RequestParam Long charm_id, @RequestParam String comment_content, @RequestParam Long user_id) {
        return charmService.addComment(charm_id, comment_content, user_id);
    }

    @GetMapping("/charm/comment")
    public ResponseEntity<?> getComments(@RequestParam Long charm_id) {
        return charmService.getComments(charm_id);
    }

    //TODO on those ones I own !
    @GetMapping("/charm/comment/recent")
    public ResponseEntity<?> getMostRecentlyCommentedCharms(@RequestParam Long user_id) {
        return charmService.getMostRecentlyCommentedCharms(user_id);

    }

    @GetMapping("/charm/random")
    public ResponseEntity<?> getDashboardCharms(@RequestParam Long user_id) {
        return charmService.getDashboardCharms(user_id);
    }

    @GetMapping("/charm/download/{charm_id}")
    public ResponseEntity<?> downloadCharm(@PathVariable Long charm_id) {
        return charmService.downloadCharm(charm_id);
    }


    @GetMapping("/charm/created")
    public ResponseEntity<?> getAllCharmsCreated(@RequestParam Long user_id) {
        return charmService.getAllCharmsCreated(user_id);
    }

    @GetMapping("/charm/collected")
    public ResponseEntity<?> getAllCharmsCollected(@RequestParam Long user_id) {
        return charmService.getAllCharmsCollected(user_id);
    }

    @GetMapping("/charm/all")
    public ResponseEntity<?> getAllCharms(@RequestParam Long user_id) {
        return charmService.getAllCharms(user_id);
    }

    @GetMapping("/charm")
    public ResponseEntity<?> getCharmById(@RequestParam Long charm_id) {
        return charmService.getCharmById(charm_id);
    }


    @PostMapping(value = "/charms", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createCharm(
            @RequestPart("charmDto") CharmDto charmDto,
            @RequestParam("user_id") Long user_id,
            @RequestPart("file") MultipartFile file
    ) {
        return charmService.createCharm(charmDto, user_id, file);
    }

    @PostMapping("/charms/collect")
    public ResponseEntity<?> addCharmToUserCollection(@RequestParam Long charm_id, @RequestParam Long user_id) {
        return charmService.addCharmToUserCollection(charm_id, user_id);
    }

    @GetMapping("/user/profilePicture")
    public ResponseEntity<?> getUserProfilePicture(@RequestParam Long user_id) {
        return charmService.getUserProfilePicture(user_id);
    }

    @GetMapping("/charm/image")
    public ResponseEntity<?> getCharmImage(@RequestParam Long charm_id) {
        return charmService.getCharmImage(charm_id);
    }

    @GetMapping("/charm/madeBy")
    public ResponseEntity<?> getCreator(@RequestParam Long charm_id) {
        return charmService.getCreator(charm_id);
    }


    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(@ModelAttribute UserDto userDto, @RequestParam("file") MultipartFile file) {

        return charmService.createUser(userDto, file);
    }
}
