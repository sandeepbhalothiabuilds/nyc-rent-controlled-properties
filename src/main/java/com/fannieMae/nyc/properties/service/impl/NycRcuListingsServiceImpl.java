package com.fannieMae.nyc.properties.service.impl;

import com.fannieMae.nyc.properties.entity.NycRcuListings;
import com.fannieMae.nyc.properties.entity.TableRow;
import com.fannieMae.nyc.properties.repository.NycRcuListingsRepository;
import com.fannieMae.nyc.properties.service.NycRcuListingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NycRcuListingsServiceImpl implements NycRcuListingsService {

    @Autowired
    private NycRcuListingsRepository nycRcuListingsRepository;

    /**
     * @param tableRow
     */
    @Override
    public void persistNycRcuRecord(TableRow tableRow) {
        NycRcuListings nycRcuListings = new NycRcuListings();
        nycRcuListings.setStatus1(tableRow.getCells().get(9).getContent());
        nycRcuListings.setStatus1(tableRow.getCells().get(10).getContent());
        nycRcuListings.setStatus2(tableRow.getCells().get(11).getContent());
        nycRcuListings.setBlock(tableRow.getCells().get(12).getContent());
        nycRcuListings.setLot(tableRow.getCells().get(13).getContent());
        //nycRcuListings.setUcbbl(1234L);
        nycRcuListingsRepository.save(nycRcuListings);

    }
}
