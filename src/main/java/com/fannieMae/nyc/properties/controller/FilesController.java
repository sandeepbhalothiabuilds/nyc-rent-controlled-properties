package com.fannieMae.nyc.properties.controller;

import com.fannieMae.nyc.properties.message.ResponseMessage;
import com.fannieMae.nyc.properties.model.NycRentControlledPropertiesResponse;
import com.fannieMae.nyc.properties.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class FilesController {

    @Autowired
    FilesStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.save(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/health")
    ResponseEntity<ResponseMessage> health() {
        String message = "***** Health Check **** ";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }

    @GetMapping("/getProperties/{borough}")
    public List<NycRentControlledPropertiesResponse> getProperties(@PathVariable String borough) {
        try {
            return storageService.getProperties(borough);

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}