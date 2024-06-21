package com.fannieMae.nyc.properties.repository;

import com.fannieMae.nyc.properties.entity.NyRentStabilizedPropertyAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NycRcuListingsAddressRepository extends JpaRepository<NyRentStabilizedPropertyAddress, Long> {

    @Modifying
    @Transactional
    @Query(value="DELETE FROM NyRentStabilizedPropertyAddress WHERE borough = ?1")
    void deleteRecordsByBorough(String boroughName);
}
