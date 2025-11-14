package com.sapienza.reverie.Controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.sapienza.reverie.Model.User;
import com.sapienza.reverie.Repository.UserRepository;
import com.sapienza.reverie.Service.CharmService;
import com.sapienza.reverie.Service.GoogleTokenVerifier;
import com.sapienza.reverie.Service.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.Optional;

@RestController
@RequestMapping("/reverie/api/user")
public class AuthController {

    @Autowired
    UserRepository userRepository;


    @Autowired
    private GoogleTokenVerifier tokenVerifier;

    @Value("${google.client-id}")
    private String googleClientId;

    @Autowired
    private CharmService charmService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (password.equals(user.getPassword())) {
                System.out.println("Wrong password");
                return new ResponseEntity<>(Mapper.toUserDto(user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            System.out.println("No user found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping("/login/google")
    public ResponseEntity<?> loginWithGoogle(@RequestParam String token) {

        try {
            GoogleIdToken.Payload payload = tokenVerifier.verify(token.replace("\"", ""));


            String userId = payload.getSubject();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");


            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                return new ResponseEntity<>(Mapper.toUserDto(user.get()), HttpStatus.OK);
            } else {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setPassword("");
                newUser.setUsername(name);
                newUser.setProfilePictureUrl("google_url:" + pictureUrl);

                return ResponseEntity.ok(charmService.createGoogleUser(newUser));

            }

        } catch (GeneralSecurityException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: Invalid token: " + e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }
}
    