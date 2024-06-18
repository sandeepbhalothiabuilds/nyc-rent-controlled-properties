package com.fannieMae.nyc.properties.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * This is a simple text extraction example to get started. For more advance usage, see the
 * ExtractTextByArea and the DrawPrintTextLocations examples in this subproject, as well as the
 * ExtractText tool in the tools subproject.
 *
 * @author Tilman Hausherr
 */
@Service
public class ExtractTextSimple {

    public void readPdfFile(MultipartFile file) throws IOException {

        File file1 = convertToFile(file, file.getOriginalFilename());
        String csvFilePath = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf(".pdf")) + ".csv";
        PDDocument document = PDDocument.load(new File("2022-DHCR-Staten-Island.pdf"));
        PDFTextStripper pdfStripper = new PDFTextStripper();

        // Extract text from PDF
        String text = pdfStripper.getText(document);
        System.out.println("Content: " + text);

        // Assume each line in the PDF corresponds to a row in the CSV
        String[] lines = text.split("\r\n|\r|\n");
        boolean setHeaderFlag = true;
        // Write lines to CSV
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            for (String line : lines) {
                String[] cells = line.split("\t");
                if (line.contains("ZIP") && setHeaderFlag) {
                    setHeaderFlag = false;
                    setHeaders(writer, line);
                } else if ((cells[0].matches("[0-9]+") && cells[0].length() == 5)) {
                    writer.writeNext(cells);
                }
                // Split each line into cells based on a delimiter (adjust as needed)
                // or use a more appropriate delimiter

            }
        }

        document.close();
        System.out.println("PDF has been converted to CSV successfully.");

    }

    private void setHeaders(CSVWriter writer, String line) {
        String[] cells = line.split(" "); // or use a more appropriate delimiter
        writer.writeNext(cells);
    }

    private File convertToFile(MultipartFile file, String originalFilename) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + originalFilename);
        file.transferTo(convFile);
        return convFile;
    }

}

