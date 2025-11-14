package com.sapienza.reverie.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class GoogleTokenVerifier {

    // Use the same Web Client ID from your Google API Console that you used in the Android app
    private static final String GOOGLE_CLIENT_ID = "220913039576-2ue0inhmdus26r3rd0jsb0mn3a56cqhc.apps.googleusercontent.com";

    public GoogleIdToken.Payload verify(String idTokenString) throws Exception {
        GoogleIdTokenVerifier verifier =
                new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            return idToken.getPayload();
        } else {
            throw new Exception("Invalid ID token.");
        }
    }
}