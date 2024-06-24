package com.fannieMae.nyc.properties.repository;

import com.fannieMae.nyc.properties.entity.NyRentStabilizedProperty;
import com.fannieMae.nyc.properties.entity.NyRentStabilizedPropertyAddress;
import com.fannieMae.nyc.properties.model.PropertyDetails;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import java.util.List;

@Repository
public interface NycRcuListingsAddressRepository extends JpaRepository<NyRentStabilizedPropertyAddress, Long> {

    @Modifying
    @Transactional
    @Query(value="DELETE FROM NyRentStabilizedProperty WHERE borough = ?1")
    void deleteRecordsByBorough(String boroughName);

    List<NyRentStabilizedPropertyAddress> findByBorough(String borough);
    @Query("SELECT new com.fannieMae.nyc.properties.model.PropertyDetails(p, u, a) " +
            "FROM NyRentStabilizedPropertyAddress a " +
            "LEFT JOIN NyRentStabilizedProperty p ON p.ucbblNumber = a.ucbblNumber " +
            "LEFT JOIN NycStblzdPropertyData u ON a.ucbblNumber = u.ucbblNumber")
    List<PropertyDetails> getAllProperties(Pageable pageable);

    @Query("SELECT new com.fannieMae.nyc.properties.model.PropertyDetails(p, u, a) " +
            "FROM NyRentStabilizedPropertyAddress a " +
            "LEFT JOIN NyRentStabilizedProperty p ON p.ucbblNumber = a.ucbblNumber " +
            "LEFT JOIN NycStblzdPropertyData u ON a.ucbblNumber = u.ucbblNumber")
    List<PropertyDetails> getAllProperties();

    @Query("SELECT count(a) " +
            "FROM NyRentStabilizedPropertyAddress a " +
            "LEFT JOIN NyRentStabilizedProperty p ON p.ucbblNumber = a.ucbblNumber " +
            "LEFT JOIN NycStblzdPropertyData u ON a.ucbblNumber = u.ucbblNumber")
    long countAllAddresses();

}
