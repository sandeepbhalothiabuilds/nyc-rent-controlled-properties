package com.fannieMae.nyc.properties.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fannieMae.nyc.properties.entity.NycStblzdPropertyData;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NycStblzdPropertyDataRepository extends JpaRepository<NycStblzdPropertyData, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM NycStblzdPropertyData WHERE rentStabilizedPropertyUnitsId > 0")
    void deleteAllRecords();
}
