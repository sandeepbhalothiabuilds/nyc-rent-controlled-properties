package com.fannieMae.nyc.properties.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fannieMae.nyc.properties.entity.NycStblzdPropertyData;

public interface NycStblzdPropertyDataRepository extends JpaRepository<NycStblzdPropertyData, Long> {
    
}
