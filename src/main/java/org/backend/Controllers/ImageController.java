package org.backend.Controllers;


import org.backend.DTOs.ResponseDTO;
import org.backend.Model.PictureURL;
import org.backend.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ImageController {

    private PictureURL pictureURL;
    private final ImageService imageService;

    @Autowired
    public ImageController(PictureURL pictureURL, ImageService imageService) {
        this.pictureURL = pictureURL;
        this.imageService = imageService;
    }

    @PostMapping("/image/{hikeRouteId}/upload")
    public ResponseDTO uploadImage(@PathVariable(value = "hikeRouteId") Long hikeRouteId, @RequestBody List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            System.out.println("Original Image Byte Size - " + file.getBytes().length);
        }

        return imageService.imageUpload(hikeRouteId, files);
    }

    @GetMapping(path = {"/image/get/{imageId}"})
    public ResponseEntity<byte[]> getImage(@PathVariable(name = "imageId") Long picturesId) {
        return imageService.getImageByImageId(picturesId);
    }

    @GetMapping(value = "/images")
    public List<PictureURL> getAllImageURL() {
        return imageService.getAllNotApprovedImage();

    }


}
