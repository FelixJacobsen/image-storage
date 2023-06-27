package feljac.tech.imagestorage.repository;

import feljac.tech.imagestorage.ImageManipulation;
import feljac.tech.imagestorage.service.ImageService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class FileSystemRepository {

    private final ImageService imageService;

    public FileSystemRepository(ImageService imageService) {
        this.imageService = imageService;
    }

    public void uploadImage(MultipartFile file, Path path, int targetSize) throws IOException {
        Files.createDirectories(path.getParent());
        byte[] bytes = file.getBytes();
        Files.write(path, ImageManipulation.resize(bytes, targetSize, file.getContentType()));
    }

    public FileSystemResource findInFileSystem(String path) {
        try {
            return new FileSystemResource(Paths.get(path));
        } catch (InvalidPathException e) {
            throw new RuntimeException(e);
        }

    }
}
