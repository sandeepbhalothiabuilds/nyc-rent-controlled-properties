package com.fannieMae.nyc.properties.service.impl;

import com.fannieMae.nyc.properties.entity.NyRentStabilizedProperty;
import com.fannieMae.nyc.properties.entity.Table;
import com.fannieMae.nyc.properties.entity.TableRow;
import com.fannieMae.nyc.properties.repository.NycRcuListingsRepository;
import com.fannieMae.nyc.properties.service.NycRcuListingsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class NycRcuListingsServiceImpl implements NycRcuListingsService {

    @Autowired
    private NycRcuListingsRepository nycRcuListingsRepository;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void persistNycRcuRecord(List<Table> tables, Map<String, String> boroughAndIdMap) {
        String boroughName = boroughAndIdMap.keySet().iterator().next();
        String boroughId = boroughAndIdMap.get(boroughName);
        nycRcuListingsRepository.deleteRecordsByBorough(boroughName);

        CompletableFuture.runAsync(() -> tables.parallelStream().forEach(table -> {
            try {
                insertRcuRecords(table, boroughName, boroughId);
            } catch (JsonProcessingException e) {
                System.out.println("Error Occurred while saving-: " + e);
            }
        }));

    }

    private void insertRcuRecords(Table table, String boroughName, String boroughId) throws JsonProcessingException {
        List<NyRentStabilizedProperty> nyRentStabilizedPropertyList = new ArrayList<>();
        NyRentStabilizedProperty nyRentStabilizedProperty;
        boolean headerRow = true;
        for (TableRow tableRow : table.getRows()) {
            if (!headerRow) {
                nyRentStabilizedProperty = new NyRentStabilizedProperty();
                String blockNumber = buildBlockNumber(tableRow);
                String lotNumber = buildLotNumber(tableRow);
                nyRentStabilizedProperty.setUcbblNumber(buildUcbblNumber(tableRow, boroughId, blockNumber, lotNumber));
                nyRentStabilizedProperty.setJsonRawData(convertRowDataToJson(table.getRows().get(0), tableRow));
                nyRentStabilizedProperty.setBorough(boroughName);
                nyRentStabilizedProperty.setLot(lotNumber);
                nyRentStabilizedProperty.setBlock(blockNumber);
                nyRentStabilizedProperty.setStatus1(tableRow.getCells().size() >= 10 ? tableRow.getCells().get(9).getContent() : null);
                nyRentStabilizedProperty.setStatus2(tableRow.getCells().size() >= 11 ? tableRow.getCells().get(10).getContent() : null);
                nyRentStabilizedProperty.setStatus3(tableRow.getCells().size() >= 12 ? tableRow.getCells().get(11).getContent() : null);
                nyRentStabilizedProperty.setCreatedBy("postgres");
                nyRentStabilizedProperty.setCreatedAt(LocalDateTime.now());
                nyRentStabilizedProperty.setUpdatedBy("postgres");
                nyRentStabilizedProperty.setUpdatedAt(LocalDateTime.now());
                nyRentStabilizedPropertyList.add(nyRentStabilizedProperty);
            }
            headerRow = false;

        }
        nycRcuListingsRepository.saveAll(nyRentStabilizedPropertyList);
        System.out.println("Page Records Saved for Page# " + table.getPageIdx());
    }

    private String buildLotNumber(TableRow tableRow) {
        String lotNumber = "0000";
        if (tableRow.getCells().size() >= 14) {
            lotNumber = tableRow.getCells().get(13).getContent();
            lotNumber = String.format("%04d", Integer.parseInt(lotNumber));
        }
        return lotNumber;
    }

    private String buildBlockNumber(TableRow tableRow) {
        String blockNumber = "0000";
        if (tableRow.getCells().size() >= 13) {
            blockNumber = tableRow.getCells().get(12).getContent();
            blockNumber = String.format("%04d", Integer.parseInt(blockNumber));
        }
        return blockNumber;
    }

    private Long buildUcbblNumber(TableRow tableRow, String boroughId, String blockNumber, String lotNumber) {
        return Long.parseLong(boroughId + blockNumber + lotNumber);
    }

    private String convertRowDataToJson(TableRow headerRow, TableRow dataRow) throws JsonProcessingException {
        Map<String, String> dataMap = new HashMap<>();

        for (int i = 0; i < dataRow.getCells().size(); i++) {
            dataMap.put(headerRow.getCells().get(i).getContent(), dataRow.getCells().get(i).getContent());
        }
        return mapper.writeValueAsString(dataMap);

    }
}
