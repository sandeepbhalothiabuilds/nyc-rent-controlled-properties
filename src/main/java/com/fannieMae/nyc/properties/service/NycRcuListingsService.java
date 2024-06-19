package com.fannieMae.nyc.properties.service;

import com.fannieMae.nyc.properties.entity.TableRow;
import org.springframework.stereotype.Service;

@Service
public interface NycRcuListingsService {
    public default void persistNycRcuRecord(TableRow tableRow) {
    }
}
