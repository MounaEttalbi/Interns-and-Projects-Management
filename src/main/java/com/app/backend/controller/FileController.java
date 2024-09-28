package com.app.backend.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class FileController {

   private final Path rootLocation = Paths.get("uploads");
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path file = rootLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());
            
            if (resource.exists() || resource.isReadable()) {
            	 // Déterminer le type MIME basé sur le fichier
                String contentType = Files.probeContentType(file);

                // Définir un type MIME par défaut si la détermination échoue
                if (contentType == null) {
                    contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
                }

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE,contentType) // Ajustez le type MIME si nécessaire
                        .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate") // Désactiver la mise en cache
                        .header(HttpHeaders.PRAGMA, "no-cache")
                        .header(HttpHeaders.EXPIRES, "0")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}