package com.sapienza.reverie.Controller;

import com.sapienza.reverie.Model.Charm;
import com.sapienza.reverie.Model.Comment;
import com.sapienza.reverie.Model.User;
import com.sapienza.reverie.Repository.CharmRepository;
import com.sapienza.reverie.Repository.CommentRepository;
import com.sapienza.reverie.Repository.UserRepository;
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
    CharmRepository charmRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    FileStorageService fileStorageService;




    /*TODO
        * authentication (sign up page + google sign in)
        * profile picture
        * qr code scanning and generation -> link to image
        * navigation improvements
        *
    * */

    @PostMapping("/charm/comment")
    public ResponseEntity<?> addComment(@RequestParam Long charm_id, @RequestParam String comment_content, @RequestParam Long user_id) {
        Optional<Charm> charmOptional = charmRepository.findById(charm_id);
        Optional<User> user = userRepository.findById(user_id);

        if (charmOptional.isEmpty() || user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Comment comment = new Comment();
        comment.setCharm(charmOptional.get());
        comment.setText(comment_content);
        comment.setUser(user.get());
        comment.setCreated_at(LocalDateTime.now());

        user.get().getComments().add(comment);
        charmOptional.get().getComments().add(comment);

        charmRepository.save(charmOptional.get());
        userRepository.save(user.get());
        commentRepository.save(comment);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/charm/comment")
    public ResponseEntity<?> getComments(@RequestParam Long charm_id) {
        Optional<Charm> charmOptional = charmRepository.findById(charm_id);
        if (charmOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Comment> comments = charmOptional.get().getComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/charm/comment/recent")
    public ResponseEntity<?> getMostRecentlyCommentedCharms(@RequestParam Long charm_id) {
        List<Charm> charms = commentRepository.findMostRecentlyCommentedCharms(LocalDateTime.now());
        return new ResponseEntity<>(charms, HttpStatus.OK);

    }

    @GetMapping("/charm/random")
    public ResponseEntity<?> getDashboardCharms(@RequestParam Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        List<Charm> collectedCharms = user.get().getCollected_charms();
         Collections.shuffle(collectedCharms);

        List<Charm> random10 = collectedCharms.stream()
            .limit(10)
            .toList();

        return new ResponseEntity<>(Mapper.toCharmDtoList(random10), HttpStatus.OK);
    }



    @GetMapping("/charm/created")
    public ResponseEntity<?> getAllCharmsCreated(@RequestParam Long user_id) {
        Optional<List<Charm>> charms = userRepository.findAllCreated(user_id);
        if (charms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(charms);
    }

    @GetMapping("/charm/collected")
    public ResponseEntity<?> getAllCharmsCollected(@RequestParam Long user_id) {
        Optional<List<Charm>> charms = userRepository.findAllCollected(user_id);
        if (charms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(charms);
    }

    @GetMapping("/charm")
    public ResponseEntity<?> getCharmById(@RequestParam Long charm_id) {
        Optional<Charm> charmOptional = charmRepository.findById(charm_id);
        if (charmOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(charmOptional.get(), HttpStatus.OK);
    }


    @PostMapping(value = "/charms", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createCharm(@ModelAttribute CharmDto charmDto, @RequestParam("user_id") Long user_id, @RequestParam("file") MultipartFile file) {
        Optional<User> user = userRepository.findById(user_id);

        if (user.isEmpty()) {
            // Return 404 if user not found
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User with id " + user_id + " not found");
        }
        String imageUrl = fileStorageService.storeAndGetUrl(file);

        Charm charm = new Charm();
        charm.setDescription(charmDto.getDescription());
        charm.setCreated_at(LocalDateTime.now());
        charm.setCreator(user.get());
        charm.setPictureUrl(imageUrl);
        Charm savedCharm = charmRepository.save(charm);

        return ResponseEntity
                .ok()
                .body(savedCharm);
    }

    @PostMapping("/charms/collect")
    public ResponseEntity<?> addCharmToUserCollection(@RequestBody Long charm_id, @RequestParam Long user_id) {
        Optional<User> user = userRepository.findById(user_id);

        if (user.isEmpty()) {
            // Return 404 if user not found
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User with id " + user_id + " not found");
        }

        Optional<Charm> charm = charmRepository.findById(charm_id);
        if(charm.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Charm with id " + charm_id + " not found");
        }

        if (charm.get().getCollectors().contains(user.get())){
            return ResponseEntity.badRequest().body("Charm already collected");
        }

        if(user.get().getCreated_charms().contains(charm.get())){
            return ResponseEntity.badRequest().body("You cannot collect a charm you created");
        }

        charm.get().getCollectors().add(user.get());
        Charm savedCharm = charmRepository.save(charm.get());

        return ResponseEntity
                .ok()
                .body(savedCharm);
    }

    @GetMapping("/user/profilePicture")
    public ResponseEntity<?> getUserProfilePicture(@RequestParam Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User with id " + user_id + " not found");
        }
        return ResponseEntity.ok(user.get().getProfilePictureUrl());
    }

     @GetMapping("/charm/image")
    public ResponseEntity<?> getCharmImage(@RequestParam Long charm_id) {
        Optional<Charm> charmOptional = charmRepository.findById(charm_id);
        if (charmOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Charm with id " + charm_id + " not found");
        }
        return ResponseEntity.ok(charmOptional.get().getPictureUrl());
    }


    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(@ModelAttribute UserDto userDto, @RequestParam("file") MultipartFile file) {

        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()){
            return ResponseEntity.badRequest().body("User with email " + userDto.getEmail() + " already exists");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setProfilePictureUrl(fileStorageService.storeAndGetUrl(file));
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
