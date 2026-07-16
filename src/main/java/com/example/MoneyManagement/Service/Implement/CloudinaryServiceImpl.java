package com.example.MoneyManagement.Service.Implement;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.MoneyManagement.Service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;
    @Override
    public String uploadImage(MultipartFile file) {
        validateFile(file);
        try {
            log.info("Uploading image '{}' to Cloudinary.", file.getOriginalFilename());
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.emptyMap()
            );
            String imageUrl = (String) uploadResult.get("secure_url");
            log.info("Image uploaded successfully.");
            return imageUrl;
        } catch (IOException exception) {
            log.error("Failed to upload image to Cloudinary.", exception);
            throw new RuntimeException(
                    "Unable to upload image to Cloudinary.",
                    exception
            );
        }
    }
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file must not be null or empty.");
        }
    }

}