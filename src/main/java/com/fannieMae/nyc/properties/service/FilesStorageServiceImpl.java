package com.fannieMae.nyc.properties.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
  @Autowired
  ExtractTextSimple extractTextSimple;

  @Autowired
  PDFParser pdfParser;

  private final Path root = Paths.get("uploads");

  @Override
  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  @Override
  public void save(MultipartFile file) {
    try {
      //extractTextSimple.readPdfFile(file);
      //Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
      File file1 = convertToFile(file, file.getOriginalFilename());
      String filePath = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf(".pdf"));

      String[] args = new String[10];
        args[0] = "-in";
        args[1] = "C:\\Users\\sandeep.kumar7\\IdeaProjects\\NYCProperties\\_Docs\\2022-DHCR-Bronx.pdf";
        args[2] = "-out";
        args[3] = "C:\\Users\\sandeep.kumar7\\IdeaProjects\\nyc-rent-controlled-properties\\_Docs\\result\\"+filePath+".html";
        args[4] = "-el";
        args[5] = "0,-1";

      pdfParser.extractTables(args, file1);

    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }

      throw new RuntimeException(e.getMessage());
    }
  }

  private File convertToFile(MultipartFile file, String originalFilename) throws IOException {
    File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + originalFilename);
    file.transferTo(convFile);
    return convFile;
  }

  @Override
  public Resource load(String filename) {
    try {
      Path file = root.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public boolean delete(String filename) {
    try {
      Path file = root.resolve(filename);
      return Files.deleteIfExists(file);
    } catch (IOException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Could not load the files!");
    }
  }

}
