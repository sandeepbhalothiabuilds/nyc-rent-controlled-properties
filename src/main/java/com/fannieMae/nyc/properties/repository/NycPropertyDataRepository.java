package com.fannieMae.nyc.properties.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fannieMae.nyc.properties.entity.NycPropertyData;

public interface NycPropertyDataRepository extends JpaRepository<NycPropertyData, Long> {
    
}
