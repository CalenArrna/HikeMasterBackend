package org.backend.Controllers;

import org.backend.DTOs.ImageSuccessDTO;
import org.backend.DTOs.ResponseDTO;
import org.backend.Model.Pictures;
import org.backend.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @PostMapping("/image/upload")
    public ResponseDTO uplaodImage(@RequestBody MultipartFile file) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        Pictures img = new Pictures(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));
        imageRepository.save(img);
        return new ImageSuccessDTO(img);
    }

//    @GetMapping(path = {"/get/{imageName}"})
//    public Pictures getImage(@PathVariable Long picturesId) throws IOException {
//        final Optional<Pictures> retrievedImage = imageRepository.findById(picturesId);
//        return new Pictures(retrievedImage.get().getName(), retrievedImage.get().getType(),
//                decompressBytes(retrievedImage.get().getPicByte()));
//    }

    public static byte[] compressBytes(byte[] data) {
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

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException ignored) {

        }
        return outputStream.toByteArray();
    }


}
