package com.example.MoneyManagement.Controller;

import com.example.MoneyManagement.Dtos.UploadResponseDto;
import com.example.MoneyManagement.Service.CloudinaryService;
import lombok.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

private final CloudinaryService cloudinaryService;

@PostMapping(value = "/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<UploadResponseDto> uploadImage(@RequestParam("file")MultipartFile multipartFile){
    String imageUrl = cloudinaryService.uploadImage(multipartFile);

    UploadResponseDto responseDto = UploadResponseDto
            .builder()
            .fileName(multipartFile.getOriginalFilename())
            .imageUrl(imageUrl)
            .message("Image uploaded successfully")
            .build();

    return ResponseEntity.ok(responseDto);
}

}
