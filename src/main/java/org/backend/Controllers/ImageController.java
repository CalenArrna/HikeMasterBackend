package org.backend.Controllers;


import org.backend.DTOs.ImageErrorDTO;
import org.backend.DTOs.ImageSuccessDTO;
import org.backend.DTOs.ResponseDTO;
import org.backend.Model.HikeRoute;
import org.backend.Model.Pictures;
import org.backend.Repository.HikeRouteRepository;
import org.backend.Repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ImageController {
    Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    HikeRouteRepository hikeRouteRepository;

    @PostMapping("/image/{hikeRouteId}/upload")
    public ResponseDTO uploadImage(@PathVariable(value = "hikeRouteId") Long hikeRouteId, @RequestBody MultipartFile file) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        Pictures img = new Pictures(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));
        Optional<HikeRoute> hikeRoute = hikeRouteRepository.findById(hikeRouteId);
        imageRepository.save(img);
        if(hikeRoute.isPresent()){
            img.setHikeRoute(hikeRoute.get());
            URL https=new URL(" https://hikemasterprog.herokuapp.com/image/get/"+img.getPicturesId());
            hikeRoute.get().getPicturesList().add(https);
            return new ImageSuccessDTO(img);
        }

        return new ImageErrorDTO();

    }

    @GetMapping(path = {"/image/get/{imageId}"})
    public ResponseEntity<byte[]> getImage(@PathVariable(name = "imageId") Long picturesId) throws IOException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        LOGGER.debug("pictureId: {}", picturesId);
        final Optional<Pictures> retrievedImage = imageRepository.findById(picturesId);
        LOGGER.debug("image found");
        Pictures pictures = new Pictures(retrievedImage.get().getName(), retrievedImage.get().getType(),
                decompressBytes(retrievedImage.get().getPicByte()));
        return new ResponseEntity<byte[]>(decompressBytes(retrievedImage.get().getPicByte()), headers, HttpStatus.CREATED);


    }


    public byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException ignored) {

        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();


    }

    public byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        LOGGER.debug("decompress is open");

        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            LOGGER.debug("decompress is ready");
            outputStream.close();
        } catch (IOException | DataFormatException ignored) {

        }
        return outputStream.toByteArray();
    }


}
