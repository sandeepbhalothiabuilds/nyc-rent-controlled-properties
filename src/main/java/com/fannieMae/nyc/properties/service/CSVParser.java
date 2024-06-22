package com.fannieMae.nyc.properties.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fannieMae.nyc.properties.entity.NycStblzdPropertyData;
import com.fannieMae.nyc.properties.repository.NycStblzdPropertyDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CSVParser {

    @Autowired
    NycStblzdPropertyDataRepository nycStblzdPropertyDataRepository;

    String line = "";

    public void extractExcelData(File file) {
        try {

            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<NycStblzdPropertyData> propertiesList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                try {
                    NycStblzdPropertyData pro = new NycStblzdPropertyData();
                    String[] property = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);// use comma as separator

                    if ((!"borough".equals(property[0])) && (!property[50].isEmpty())) {
                        pro.setBorough(property[0]);
                        pro.setUcbblNumber(property[1]);
                        pro.setUc(property[42]);
                        pro.setEst(property[43]);
                        pro.setDhcr(property[44]);
                        pro.setAbat(property[45]);
                        pro.setCd(property[46]);
                        pro.setCt(property[47]);
                        pro.setCb(property[48]);
                        pro.setCouncil(property[49]);
                        pro.setZip(property[50]);
                        pro.setUnitAddress(property[51]);
                        pro.setOwnerName(property[52]);
                        pro.setNumberOfBuildings(property[53]);
                        pro.setNumOfFloors(property[54]);
                        pro.setUnitRes(property[55]);
                        pro.setUnitTotal(property[56]);
                        pro.setYearBuilt(Long.valueOf(property[57]));
                        pro.setCondoNumber(property[58]);
                        pro.setLongitude(property[59]);
                        pro.setLatitude(property[60]);
                        propertiesList.add(pro);

                        ObjectMapper mapper = new ObjectMapper();
                        String jsonString = mapper.writeValueAsString(property);
                        pro.setContent(jsonString);
                    }
                } catch (Exception e) {
                    System.out.println("Error Occurred when building data to save in data in rent_stblzd_property_units table: " + e);
                }
            }
            CompletableFuture.runAsync(() -> saveProperties(propertiesList));
        } catch (IOException e) {
            System.out.println("Error while calling Async for git hub data: " + e);
        }
    }

    public void saveProperties(List<NycStblzdPropertyData> propertiesList) {
        try {
            nycStblzdPropertyDataRepository.deleteAllRecords();
            List<List<NycStblzdPropertyData>> listOfPropertiesList = Lists.partition(propertiesList, 500);
            listOfPropertiesList.parallelStream().forEach(list -> nycStblzdPropertyDataRepository.saveAll(list));
        } catch (Exception e) {
            System.out.println("Error while saving the github data: " + e);
        }
    }
}
