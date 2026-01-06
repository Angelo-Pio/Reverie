package com.sapienza.reverie.Service;

import com.sapienza.reverie.Model.Charm;
import com.sapienza.reverie.Model.Comment;
import com.sapienza.reverie.Model.User;
import com.sapienza.reverie.Repository.CharmRepository;
import com.sapienza.reverie.Repository.CommentRepository;
import com.sapienza.reverie.Repository.UserRepository;
import com.sapienza.reverie.dto.*;
import com.sapienza.reverie.security.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CharmService {

    @Autowired
    private CharmRepository charmRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private PasswordManager passwordManager;





    public ResponseEntity<?> addComment(Long charm_id, String comment_content, Long user_id) {

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

        //user.get().getComments().add(comment);
        //charmOptional.get().getComments().add(comment);

        charmRepository.save(charmOptional.get());
        userRepository.save(user.get());
        commentRepository.save(comment);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<?> getComments(Long charm_id) {
        Optional<Charm> charms = charmRepository.findById(charm_id);
        if (charms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<UserCommentDto> ret = new LinkedList<>();
        for (Comment comment: charms.get().getComments()) {
            User user = comment.getUser();
            UserCommentDto userCommentDto = new UserCommentDto();
            userCommentDto.setCommentDto(Mapper.toCommentDto(comment));
            userCommentDto.setUserDto(Mapper.toUserDto(user));
            ret.add(userCommentDto);
        }

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    public ResponseEntity<?> getMostRecentlyCommentedCharms(Long user_id) {
        List<Charm> charms = commentRepository.findMostRecentlyCommentedCharms(LocalDateTime.now(), user_id);
        List<CharmWithUserDto> ret_charms = new LinkedList<>();
        for (Charm charm : charms) {
            
            Comment comment = charm.getComments().getFirst();
            User user = charm.getComments().getFirst().getUser();
            ret_charms.add(Mapper.toCharmWithUserDto(charm,user,comment));
        }
        ResponseEntity<List<CharmWithUserDto>> responseEntity = new ResponseEntity<>(ret_charms, HttpStatus.OK);
        System.out.println(responseEntity);
        return responseEntity;
    }

    public ResponseEntity<?> getDashboardCharms(Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        List<Charm> collectedCharms = user.get().getCollected_charms();

        List<Charm> random10 = collectedCharms.stream()
            .limit(5)
            .toList();

        return new ResponseEntity<>(Mapper.toCharmDtoList(random10), HttpStatus.OK);
    }

    public ResponseEntity<?> getAllCharmsCreated(Long user_id) {
        Optional<List<Charm>> charms = userRepository.findAllCreated(user_id);
        if (charms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Mapper.toCharmDtoList(charms.get()));
    }

    public ResponseEntity<?> getAllCharmsCollected( Long user_id) {
        Optional<List<Charm>> charms = userRepository.findAllCollected(user_id);
        if (charms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Mapper.toCharmDtoList(charms.get()));
    }

    public ResponseEntity<?> getCharmById( Long charm_id) {
        Optional<Charm> charmOptional = charmRepository.findById(charm_id);
        if (charmOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(Mapper.toCharmDto(charmOptional.get()), HttpStatus.OK);
    }


    public ResponseEntity<?> createCharm(@ModelAttribute CharmDto charmDto, Long user_id,  MultipartFile file) {
        Optional<User> user = userRepository.findById(user_id);

        if (user.isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User with id " + user_id + " not found");
        }
        String imageUrl = fileStorageService.storeAndGetUrl(file);


        Charm charm = new Charm();
        charm.setDescription(charmDto.getDescription());
        charm.setCreated_at(charmDto.getCreated_at());
        charm.setCreator(user.get());
        charm.setPictureUrl(imageUrl);
        Charm savedCharm = charmRepository.save(charm);


        return ResponseEntity
                .ok()
                .body(Mapper.toCharmDto(savedCharm));
    }

    public ResponseEntity<?> addCharmToUserCollection(Long charm_id, Long user_id, String collected_in) {
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
        charm.get().setCollected_in(collected_in);
        user.get().getCollected_charms().add(charm.get());
        Charm savedCharm = charmRepository.save(charm.get());
        userRepository.save(user.get());

        return ResponseEntity
                .ok()
                .body(Mapper.toCharmDto(savedCharm));
    }

    public ResponseEntity<?> getUserProfilePicture( Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User with id " + user_id + " not found");
        }
        return ResponseEntity.ok(user.get().getProfilePictureUrl());
    }

    public ResponseEntity<?> getCharmImage( Long charm_id) {
        Optional<Charm> charmOptional = charmRepository.findById(charm_id);
        if (charmOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Charm with id " + charm_id + " not found");
        }
        return ResponseEntity.ok(charmOptional.get().getPictureUrl());
    }


    public ResponseEntity<?> createUser(@ModelAttribute UserDto userDto, MultipartFile file) {

        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()){

            return ResponseEntity.badRequest().body("User with email " + userDto.getEmail() + " already exists");
        }

        if(userDto.getPassword().length() < 8){
            return ResponseEntity.badRequest().body("Password should be at least 8 characters long");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        user.setPassword(passwordManager.hashPassword(userDto.getPassword()));
        user.setProfilePictureUrl(fileStorageService.storeAndGetUrl(file));
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getAllCharms(Long user_id) {
        Optional<List<Charm>> created= userRepository.findAllCreated(user_id);
        Optional<List<Charm>> collected = userRepository.findAllCollected(user_id);

        if (created.isEmpty() &&  collected.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<Charm> charms = created.get();
        charms.addAll(collected.get());
        return ResponseEntity.ok(Mapper.toCharmDtoList(charms));
    }

    public ResponseEntity<?> downloadCharm(Long charmId) {
        Optional<Charm> charmOptional = charmRepository.findById(charmId);
        if (charmOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Resource charm_image = fileStorageService.getCharmImage(charmOptional.get().getPictureUrl());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + charmOptional.get().getPictureUrl() + "\"")
                .contentType(MediaTypeFactory.getMediaType(charmOptional.get().getPictureUrl()).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(charm_image);
    }

    public ResponseEntity<?> getCreator(Long charmId) {
        Optional<Charm> charmOptional = charmRepository.findById(charmId);
        if (charmOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        User creator = charmOptional.get().getCreator();
        return ResponseEntity.ok(Mapper.toUserDto(creator));
    }

    public UserDto createGoogleUser(User newUser) {

        userRepository.save(newUser);
        return  Mapper.toUserDto(newUser);

    }
}
