package feljac.tech.imagestorage.repository;

import feljac.tech.imagestorage.entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends MongoRepository<Image, String> {
}
