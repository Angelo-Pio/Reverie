package com.sapienza.reverie.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    // Defines the root location for storing uploaded files (e.g., "uploads/")
    private final Path rootLocation = Paths.get("uploads");

    public FileStorageService() {
        try {
            Files.createDirectories(rootLocation);
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    public Resource getCharmImage(String charm_filename){
        try {
            // 1. Resolve the full path of the file
            Path imagePath = this.rootLocation.resolve(charm_filename).normalize();

            // 2. Create a UrlResource from the path
            Resource resource = new UrlResource(imagePath.toUri());

            // 3. Check if the resource exists and is readable
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                // Throw an exception if the file doesn't exist or can't be read
                throw new RuntimeException("Could not read the file: " + charm_filename);
            }
        } catch (MalformedURLException e) {
            // This exception is thrown if the path URI is invalid
            throw new RuntimeException("Error while creating resource from path: " + e.getMessage());
        }
    }

    /**
     * Stores a file and returns its web-accessible URL.
     */
    public String storeAndGetUrl(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            String originalFilename = file.getOriginalFilename();
            String uniqueFilename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));


            // Copy the file to the target location (e.g., uploads/unique_filename.jpg)
            Files.copy(file.getInputStream(), this.rootLocation.resolve(uniqueFilename));

            // Generate the file's public URL
            // e.g., http://your-server-address.com/files/unique_filename.jpg
            /*return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/") // This path must be configured to serve static files
                    .path(uniqueFilename)
                    .toUriString();*/
            return uniqueFilename;

        } catch (Exception e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }
}