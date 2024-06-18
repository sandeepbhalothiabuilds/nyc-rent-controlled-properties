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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PDFParser {

    public void extractTables(String[] args, File file) {
        /*args[0] = "-in";
        args[1] = "C:\\Users\\sandeep.kumar7\\IdeaProjects\\NYCProperties\\_Docs\\2022-DHCR-Bronx.pdf";
        args[2] = "-out";
        args[3] = "C:\\Users\\sandeep.kumar7\\IdeaProjects\\NYCProperties\\_Docs\\result\\2022-DHCR-Bronx.html";
        args[4] = "-el";
        args[5] = "0,-1";*/
        try {
            List<Integer[]> exceptLines = getExceptLines(args);
           // String in = getIn(args);
            String out = getOut(args);

            PDFTableExtractor extractor = (new PDFTableExtractor())
                    .setSource(file);

            List<Integer> exceptLineIdxes = new ArrayList<>();
            Multimap<Integer, Integer> exceptLineInPages = LinkedListMultimap.create();
            for (Integer[] exceptLine : exceptLines) {
                if (exceptLine.length == 1) {
                    exceptLineIdxes.add(exceptLine[0]);
                } else if (exceptLine.length == 2) {
                    int lineIdx = exceptLine[0];
                    int pageIdx = exceptLine[1];
                    exceptLineInPages.put(pageIdx, lineIdx);
                }
            }
            if (!exceptLineIdxes.isEmpty()) {
                extractor.exceptLine(Ints.toArray(exceptLineIdxes));
            }
            if (!exceptLineInPages.isEmpty()) {
                for (int pageIdx : exceptLineInPages.keySet()) {
                    extractor.exceptLine(pageIdx, Ints.toArray(exceptLineInPages.get(pageIdx)));
                }
            }
            //begin parsing pdf file
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

    private static List<Integer[]> getExceptLines(String[] args) {
        List<Integer[]> retVal = new ArrayList<>();
        String exceptLinesInString = getArg(args, "el");
        if (exceptLinesInString == null) {
            return retVal;
        }
        String[] exceptLineStrings = exceptLinesInString.split(",");
        for (String exceptLineString : exceptLineStrings) {
                try {
                    int lineIdx = Integer.parseInt(exceptLineString.trim());
                    retVal.add(new Integer[]{lineIdx});
                } catch (Exception e) {
                    throw new RuntimeException("Invalid except lines argument (-el): " + exceptLinesInString, e);
                }

        }
        return retVal;
    }

    private static String getOut(String[] args) {
        String retVal = getArg(args, "out", null);
        if (retVal == null) {
            throw new RuntimeException("Missing output location");
        }
        return retVal;
    }

    private static String getIn(String[] args) {
        String retVal = getArg(args, "in", null);
        if (retVal == null) {
            throw new RuntimeException("Missing input file");
        }
        return retVal;
    }

    private static String getArg(String[] args, String name, String defaultValue) {
        int argIdx = -1;
        for (int idx = 0; idx < args.length; idx++) {
            if (("-" + name).equals(args[idx])) {
                argIdx = idx;
                break;
            }
        }
        if (argIdx == -1) {
            return defaultValue;
        } else if (argIdx < args.length - 1) {
            return args[argIdx + 1].trim();
        } else {
            throw new RuntimeException("Missing argument value. Argument name: " + name);
        }
    }

    private static String getArg(String[] args, String name) {
        return getArg(args, name, null);
    }
}
