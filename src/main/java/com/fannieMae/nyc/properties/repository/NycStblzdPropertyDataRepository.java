package com.fannieMae.nyc.properties.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fannieMae.nyc.properties.entity.NycStblzdPropertyData;

@Repository
public interface NycStblzdPropertyDataRepository extends JpaRepository<NycStblzdPropertyData, Long> {
}
