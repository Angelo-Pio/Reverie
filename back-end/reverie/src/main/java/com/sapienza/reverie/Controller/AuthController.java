package com.sapienza.reverie.Controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.sapienza.reverie.Service.GoogleTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/user")
    public class AuthController {



    @Autowired
    private GoogleTokenVerifier tokenVerifier;

        @Value("${google.client-id}")
        private String googleClientId;

        @PostMapping("/login/google")
        public ResponseEntity<?> loginWithGoogle(@RequestBody String idTokenString) {
        try {
            GoogleIdToken.Payload payload = tokenVerifier.verify(idTokenString.replace("\"", ""));

            // Use the payload to get user information
            String userId = payload.getSubject();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

            // TODO:
            // 1. Check if a user with this email or userId exists in your database.
            // 2. If not, create a new user account.
            // 3. Create a session or generate a JWT for your own application's authentication.

            // For now, returning a success message
            return ResponseEntity.ok("User authenticated successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }
    }
    