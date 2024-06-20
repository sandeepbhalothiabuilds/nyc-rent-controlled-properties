package com.fannieMae.nyc.properties.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fannieMae.nyc.properties.entity.NycPropertyData;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CSVParser {

    String line = "";  
    String splitBy = ",";  

    public void extractExcelData(File file){
        try {  

            BufferedReader br = new BufferedReader(new FileReader(file));  
            FileWriter myWriter = new FileWriter("properties.json");
            while ((line = br.readLine()) != null) {  // returns a Boolean value
                NycPropertyData pro = new NycPropertyData();
                String[] property = line.split(splitBy);    // use comma as separator

                
                
                if(property.length > 55){

                    // pro.setPropertyId(property[1]);
    
                    ObjectMapper mapper = new ObjectMapper();
    
                    String jsonString = mapper.writeValueAsString(pro);
                    myWriter.write(jsonString+"\n");
                }
            }
            myWriter.close();
            
            }   catch (IOException e)   
        {  
            e.printStackTrace(); 
        }
    }
    
}
