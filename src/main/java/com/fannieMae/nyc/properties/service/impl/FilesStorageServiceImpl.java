package com.fannieMae.nyc.properties.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fannieMae.nyc.properties.service.CSVParser;
import com.fannieMae.nyc.properties.service.FilesStorageService;
import com.fannieMae.nyc.properties.service.PDFParser;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

  @Autowired
  PDFParser pdfParser;

  @Autowired
  CSVParser csvParser;

  @Override
  public void save(MultipartFile file) {

  if(file.getOriginalFilename().contains(".pdf")){
    try {
      File file1 = convertToFile(file, file.getOriginalFilename());
      pdfParser.extractTables(file1);

    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }

      throw new RuntimeException(e.getMessage());
    }
  } else if(file.getOriginalFilename().contains(".csv")) {

    try {
      File file1 = convertToFile(file, file.getOriginalFilename());
      csvParser.extractExcelData(file1);
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }

      throw new RuntimeException(e.getMessage());
    }
  };
  
  }

  private File convertToFile(MultipartFile file, String originalFilename) throws IOException {
    File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + originalFilename);
    file.transferTo(convFile.toPath());
    return convFile;
  }

}
