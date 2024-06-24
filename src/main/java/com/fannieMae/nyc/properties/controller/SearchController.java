package com.fannieMae.nyc.properties.controller;

import com.fannieMae.nyc.properties.model.PropertyDetails;
import com.fannieMae.nyc.properties.service.impl.NYRentStbLzdPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class SearchController {
    @Autowired
    private NYRentStbLzdPropertyService nyRentStbLzdPropertyService;

    @GetMapping("/details/{offset}")
    public List<PropertyDetails> getPropertyDetails(@PathVariable int offset) {
        return nyRentStbLzdPropertyService.getPropertyDetails(offset);
    }

    @GetMapping("/details/")
    public List<PropertyDetails> getPropertyDetails() {
        return nyRentStbLzdPropertyService.getPropertyDetails();
    }
    @GetMapping("/detailsCount")
    public long getPropertyDetailsCount() {
        return nyRentStbLzdPropertyService.getPropertyDetailsCount();
    }


    @GetMapping("/criteria")
    public List<PropertyDetails> getProperties(@RequestParam(required = false) String zipcode, @RequestParam(required = false) String borough, @RequestParam(required = true) int offset) {
        return nyRentStbLzdPropertyService.findAllByCriteria(zipcode, borough, offset);
    }

    @GetMapping("/criteriaCount")
    public Long getPropertiesCount(@RequestParam(required = false) String zipcode, @RequestParam(required = false) String borough) {
        return nyRentStbLzdPropertyService.countByCriteria(zipcode, borough);
    }
}
