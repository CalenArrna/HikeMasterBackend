package org.backend.Service;

import org.backend.Controllers.ImageController;
import org.backend.DTOs.ImageErrorDTO;
import org.backend.DTOs.ImageSuccessDTO;
import org.backend.DTOs.ResponseDTO;
import org.backend.Model.HikeRoute;
import org.backend.Model.PictureURL;
import org.backend.Model.Pictures;
import org.backend.Repository.HikeRouteRepository;
import org.backend.Repository.ImageRepository;
import org.backend.Repository.URLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageService {
    @Autowired
    URLRepository urlRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    HikeRouteRepository hikeRouteRepository;
    Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

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

    public List<PictureURL> getAllNotApprovedImage(){
        List<PictureURL> urlList = urlRepository.findAll();
        List<Pictures> pictures = imageRepository.findAll();

        List<Long> notApprovedPictureIdList= pictures.stream()
                .filter(pictures1 -> !pictures1.getApprove())
                .map(Pictures::getPicturesId).collect(Collectors.toList());

      return urlList.stream()
                .filter(pictureURL1 -> notApprovedPictureIdList.contains(pictureURL1.getPictureId()))
                .collect(Collectors.toList());
    }
    public ResponseDTO imageUpload(Long hikeRouteId,List<MultipartFile> files) throws IOException {
        Optional<HikeRoute> hikeRoute = hikeRouteRepository.findById(hikeRouteId);
        if (hikeRoute.isPresent()) {
            for (MultipartFile file : files) {
                Pictures img = new Pictures(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));
                imageRepository.save(img);
                img.setHikeRoute(hikeRoute.get());
                PictureURL pictureURL = new PictureURL();
                URL https = new URL(" https://hikemasterprog.herokuapp.com/image/get/" + img.getPicturesId());
                pictureURL.setPictureUrl(https);
                pictureURL.setPictureId(img.getPicturesId());
                hikeRoute.get().getPictureUrlList().add(pictureURL);
                pictureURL.setHikeRoute(hikeRoute.get());
                urlRepository.save(pictureURL);
            }
            return new ImageSuccessDTO();
        }


        return new ImageErrorDTO();

    }
    public ResponseEntity<byte[]> getImageByImageId(Long picturesId ){
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        LOGGER.debug("pictureId: {}", picturesId);
        final Optional<Pictures> retrievedImage = imageRepository.findById(picturesId);
        LOGGER.debug("image found");
        byte[] picByte = new byte[0];
        if(retrievedImage.isPresent()){
          picByte  = retrievedImage.get().getPicByte();
        }
        return new ResponseEntity<>(decompressBytes(picByte), headers, HttpStatus.CREATED);

    }
}
