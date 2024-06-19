package com.fannieMae.nyc.properties.service.impl;

import com.fannieMae.nyc.properties.service.FilesStorageService;
import com.fannieMae.nyc.properties.service.PDFParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

  @Autowired
  PDFParser pdfParser;

  @Override
  public void save(MultipartFile file) {
    try {
      File file1 = convertToFile(file, file.getOriginalFilename());
      pdfParser.extractTables(file1);

    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }

      throw new RuntimeException(e.getMessage());
    }
  }

  private File convertToFile(MultipartFile file, String originalFilename) throws IOException {
    File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + originalFilename);
    file.transferTo(convFile.toPath());
    return convFile;
  }

}
