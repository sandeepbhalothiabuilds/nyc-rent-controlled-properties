package com.fannieMae.nyc.properties.repository;

import com.fannieMae.nyc.properties.entity.NycRcuListings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NycRcuListingsRepository extends JpaRepository<NycRcuListings, Long> {
}
