package com.sapienza.reverie.Controller;

import com.sapienza.reverie.Model.Charm;
import com.sapienza.reverie.Model.User;
import com.sapienza.reverie.Repository.CharmRepository;
import com.sapienza.reverie.Repository.UserRepository;
import com.sapienza.reverie.Service.FileStorageService;
import com.sapienza.reverie.dto.CharmDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    FileStorageService fileStorageService;

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
        charm.setCreator(user.get());
        charm.setPictureUrl(imageUrl);
        Charm savedCharm = charmRepository.save(charm);

        return ResponseEntity
                .ok()
                .body(savedCharm);
    }

    @PostMapping("/charms/collect")
    public ResponseEntity<?> addCharmToUserCollection(@RequestBody CharmDto charmDto, @RequestParam Long user_id) {
        Optional<User> user = userRepository.findById(user_id);

        if (user.isEmpty()) {
            // Return 404 if user not found
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User with id " + user_id + " not found");
        }

        Optional<Charm> charm = charmRepository.findById(charmDto.getId());
        if(charm.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Charm with id " + charmDto.getId() + " not found");
        }

        if (charm.get().getCollectors().contains(user.get())){
            return ResponseEntity.badRequest().body("Charm already collected");
        }
        charm.get().getCollectors().add(user.get());
        Charm savedCharm = charmRepository.save(charm.get());

        return ResponseEntity
                .ok()
                .body(savedCharm);
    }


}
