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

                if((!"borough".equals(property[0])) && (property[50] != "")){
                    pro.setUcbbl(property[1]);
                    pro.setUnitTotal(Long.valueOf(property[56]));
                    pro.setYearBuilt(Long.valueOf(property[57]));
                    pro.setUnitRes(Long.valueOf(property[55]));
                    pro.setLon(Float.valueOf(property[59]));
                    pro.setLat(Float.valueOf(property[60]));
                    pro.setNumBldgs(Long.valueOf(property[50]));
                    pro.setNumFloors(Long.valueOf(property[54]));

    
                    propertiesList.add(pro);
        
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(property);
                    pro.setContent(jsonString);
    
                    myWriter.write(jsonString+"\n");
                    System.out.print(pro + "\n");

                }

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
