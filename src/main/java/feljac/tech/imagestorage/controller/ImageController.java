package feljac.tech.imagestorage.controller;

import feljac.tech.imagestorage.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
        Long imageID = service.uploadImage(multipartFile);
        URI uri = getLocationOfCreatedImage(uriAsString, imageID);
        return ResponseEntity.created(uri).build();
    }

    private URI getLocationOfCreatedImage(String uriAsString, Long imageID) {
        UriComponentsBuilder uriComponentsBuilder;
        if (uriAsString == null) {
            uriComponentsBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        } else {
            uriComponentsBuilder = UriComponentsBuilder.fromUriString(uriAsString);
        }
        return uriComponentsBuilder.path("/{id}").buildAndExpand(imageID).toUri();
    }
}
