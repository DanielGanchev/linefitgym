package com.appfitgym.linefitgym.service.fetchServices;

import com.appfitgym.service.impl.fetchServices.CloudinaryServiceImpl;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CloudinaryServiceImplTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @InjectMocks
    private CloudinaryServiceImpl cloudinaryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(cloudinary.uploader()).thenReturn(uploader);
    }

    @Test
    void shouldUploadImage() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("file", "Hello, World!".getBytes());
        Map<String, Object> uploadResult = new HashMap<>();
        uploadResult.put("url", "http://example.com/image.jpg");

        when(uploader.upload(any(File.class), anyMap())).thenReturn(uploadResult);

        String result = cloudinaryService.uploadImage(multipartFile);

        assertEquals("http://example.com/image.jpg", result);
    }
}