package com.security.loginregistration.file;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/files")
public class FileController {

    @GetMapping("/ch")
    public String getFilePath(@RequestParam("filename") String filename) throws IOException {
        Resource resource = new ClassPathResource("static/mmdb/" + filename);
        return resource.getFile().getAbsolutePath();
    }

    @GetMapping("/check")
    public String check() {
        return "hello world";
    }
}
