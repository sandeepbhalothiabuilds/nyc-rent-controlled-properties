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
            while ((line = br.readLine()) != null) {  // returns a Boolean value
                NycStblzdPropertyData pro = new NycStblzdPropertyData();
                String[] property = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);// use comma as separator

                if ((!"borough".equals(property[0])) && (!property[50].isEmpty())) {
                    pro.setUcbblNumber(property[1]);
                    pro.setUnitTotal(property[56]);
                    pro.setYearBuilt(Long.valueOf(property[57]));
                    pro.setUnitRes(property[55]);
                    pro.setLongitude(property[59]);
                    pro.setLatitude(property[60]);
                    pro.setNumberOfBuildings(property[53]);
                    pro.setNumOfFloors(property[54]);

                    propertiesList.add(pro);

                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(property);
                    pro.setContent(jsonString);
                }
            }
            CompletableFuture.runAsync(() -> saveProperties(propertiesList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveProperties(List<NycStblzdPropertyData> propertiesList) {
        try {
            nycStblzdPropertyDataRepository.deleteAllRecords();
            List<List<NycStblzdPropertyData>> listOfPropertiesList = Lists.partition(propertiesList, 1000);
            listOfPropertiesList.parallelStream().forEach(list -> nycStblzdPropertyDataRepository.saveAll(list));
        } catch (Exception e) {
            System.out.println("Error while saving the github data: " + e);
        }
    }
}
