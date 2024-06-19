/**
 * Copyright (C) 2016, GIAYBAC
 * <p>
 * Released under the MIT license
 */
package com.fannieMae.nyc.properties.service;

import com.fannieMae.nyc.properties.entity.Table;
import com.fannieMae.nyc.properties.entity.TableRow;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.primitives.Ints;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;

@Service
public class PDFParser {

    public void extractTables(File file) {
        try {
            String filePath = file.getName().substring(0, file.getName().indexOf(".pdf"));
            String out = System.getProperty("user.dir")+"\\_Docs\\result\\"+filePath+".html";

            PDFTableExtractor extractor = (new PDFTableExtractor())
                    .setSource(file);

            List<Integer> exceptLineIdxes = Arrays.asList(0, -1);

            extractor.exceptLine(Ints.toArray(exceptLineIdxes));

            List<Table> tables = extractor.extract();

            Writer writer = new OutputStreamWriter(new FileOutputStream(out), "UTF-8");
            List<Map<String, String>> finalJson = new ArrayList<>();
            try {
                for (Table table : tables) {
                    writer.write("Page: " + (table.getPageIdx() + 1) + "\n");
                    writer.write(table.toHtml());
                    addDataToJson(table, finalJson);

                }
                System.out.println(finalJson);
            } finally {
                try {
                    writer.close();
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void addDataToJson(Table table, List<Map<String, String>> finalJson) {
        Map<String, String> dataMap = new HashMap<>();
        boolean headerRow = true;
        for (TableRow tableRow : table.getRows()) {
            if (!headerRow) {
                for (int i = 1; i < tableRow.getCells().size(); i++) {
                    dataMap.put(table.getRows().get(0).getCells().get(i - 1).getContent(), tableRow.getCells().get(i - 1).getContent());
                }
                finalJson.add(dataMap);
                dataMap = new HashMap<>();
            }
            headerRow = false;
        }
    }
}
