package com.fannieMae.nyc.properties.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
            while ((line = br.readLine()) != null) {  // returns a Boolean value
                NycStblzdPropertyData pro = new NycStblzdPropertyData();
                String[] property = line.split(",", 60);    // use comma as separator

                //apach poi csv parsing (cell matching)

                // pro.setUcbbl(property[1]);
                // pro.setUnitTotal(property[56]);
                // pro.setYearBuilt(property[57]);
                // pro.setUnitRes(property[55]);
                // pro.setLon(property[58]);
                // pro.setLat(property[59]);
                // pro.setNumBldgs(property[53]);
                // pro.setNumFloors(property[54]);
                
    
                    ObjectMapper mapper = new ObjectMapper();
    
                    String jsonString = mapper.writeValueAsString(property);


                    //NycStblzdPropertyDataRepository.save(property);
                    myWriter.write(jsonString+"\n");
                    System.out.print(property[57] + "\n");

            }
            myWriter.close();
            
            }   catch (IOException e)   
        {  
            e.printStackTrace(); 
        }
    }
    
}
