package com.fannieMae.nyc.properties.service;

import com.fannieMae.nyc.properties.entity.Table;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface NycRcuListingsService {
    public default void persistNycRcuRecord(List<Table> tableRow, Map<String, String> boroughAndIdMap) {
    }

}
