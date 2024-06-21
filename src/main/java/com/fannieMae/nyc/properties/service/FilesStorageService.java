package com.fannieMae.nyc.properties.service;

import com.fannieMae.nyc.properties.model.NycRentControlledPropertiesResponse;
import org.springframework.web.multipart.MultipartFile;


public interface FilesStorageService {
    public void save(MultipartFile file);

    NycRentControlledPropertiesResponse getProperties();
}
