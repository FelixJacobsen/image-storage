package feljac.tech.imagestorage.controller;

import feljac.tech.imagestorage.service.ImageService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile multipartFile, String uriAsString) {
        String imageID = service.uploadImage(multipartFile);
        URI uri = getLocationOfCreatedImage(uriAsString, imageID);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.ALL_VALUE)
    public FileSystemResource downloadImage(@PathVariable String id) {
        return service.downloadImage(id);
    }

    private URI getLocationOfCreatedImage(String uriAsString, String imageID) {
        UriComponentsBuilder uriComponentsBuilder;
        if (uriAsString == null) {
            uriComponentsBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        } else {
            uriComponentsBuilder = UriComponentsBuilder.fromUriString(uriAsString);
        }
        return uriComponentsBuilder.path("/{id}").buildAndExpand(imageID).toUri();
    }
}
