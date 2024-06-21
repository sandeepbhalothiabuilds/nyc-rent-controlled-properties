package com.fannieMae.nyc.properties.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
    String splitBy = ",";  

    public void extractExcelData(File file){
        try {  

            BufferedReader br = new BufferedReader(new FileReader(file));  
            FileWriter myWriter = new FileWriter("properties.json");
            ArrayList<NycStblzdPropertyData> propertiesList = new ArrayList<>();
            while ((line = br.readLine()) != null) {  // returns a Boolean value
                NycStblzdPropertyData pro = new NycStblzdPropertyData();
                String[] property = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);// use comma as separator

                pro.setUcbbl(Integer.parseInt(property[1]));
                pro.setUnitTotal(Integer.parseInt(property[56]));
                pro.setYearBuilt(Integer.parseInt(property[57]));
                pro.setUnitRes(Integer.parseInt(property[55]));
                pro.setLon(Integer.parseInt(property[59]));
                pro.setLat(Integer.parseInt(property[60]));
                pro.setNumBldgs(Integer.parseInt(property[50]));
                pro.setNumFloors(Integer.parseInt(property[54]));

                propertiesList.add(pro);
    
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(property);

                myWriter.write(jsonString+"\n");
                System.out.print(pro + "\n");

            }

            saveProperties(propertiesList);
            myWriter.close();
            
            }   catch (IOException e)   
        {  
            e.printStackTrace(); 
        }
    }

    public void saveProperties(ArrayList<NycStblzdPropertyData> properties){
        for (NycStblzdPropertyData pro : properties){
            nycStblzdPropertyDataRepository.save(pro);
        }

    }
    
}
