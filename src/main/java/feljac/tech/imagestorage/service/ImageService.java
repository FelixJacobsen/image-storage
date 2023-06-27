package feljac.tech.imagestorage.service;

import feljac.tech.imagestorage.entity.Image;
import feljac.tech.imagestorage.repository.FileSystemRepository;
import feljac.tech.imagestorage.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class ImageService {

    private static final int TARGET_IMAGE_SIZE = 200;
    private final ImageRepository imageRepository;
    private final FileSystemRepository fileSystemRepository;

    public ImageService(ImageRepository imageRepository, FileSystemRepository fileSystemRepository) {
        this.imageRepository = imageRepository;
        this.fileSystemRepository = fileSystemRepository;
    }

    public Long uploadImage(MultipartFile multipartFile) {
        String pathToImage = setImagePath() + new Date().getTime() + multipartFile.getOriginalFilename();
        Path path = Paths.get(pathToImage);
        try {
            fileSystemRepository.uploadImage(multipartFile, path, TARGET_IMAGE_SIZE);
            return saveImageInformation(multipartFile, pathToImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Long saveImageInformation(MultipartFile multipartFile, String pathToImage) {
        Image image = new Image();
        String imageName = multipartFile.getOriginalFilename();
        image.setName(imageName);
        image.setPath(pathToImage);
        return imageRepository.save(image).getId();
    }

    private String setImagePath() {
        String folderStructure = StringUtils.cleanPath(Paths.get(".").toAbsolutePath().toString());
        return folderStructure + "/src/main/resources/static/images";
    }
}
