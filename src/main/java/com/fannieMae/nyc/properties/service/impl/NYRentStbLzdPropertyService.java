package com.fannieMae.nyc.properties.service.impl;

import com.fannieMae.nyc.properties.entity.NyRentStabilizedProperty;
import com.fannieMae.nyc.properties.entity.NyRentStabilizedPropertyAddress;
import com.fannieMae.nyc.properties.entity.NycStblzdPropertyData;
import com.fannieMae.nyc.properties.model.PropertyDetails;
import com.fannieMae.nyc.properties.repository.NycRcuListingsAddressRepository;
import com.fannieMae.nyc.properties.repository.PropertyDetailsRepository;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class NYRentStbLzdPropertyService {

    @Autowired
    private NycRcuListingsAddressRepository addrRepository;

    @PersistenceContext
    EntityManager entityManager;

   
    public List<PropertyDetails> getPropertyDetails(int offset) {
        Pageable pagable = PageRequest.of(offset, offset+50);
        return addrRepository.getAllProperties(pagable);
    }

    public List<PropertyDetails> getPropertyDetails() {
        return addrRepository.getAllProperties();
    }

    public Long getPropertyDetailsCount() {
        return addrRepository.countAllAddresses();
    }

    public List<PropertyDetails> findAllByCriteria(String zipcode, String borough){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PropertyDetails> query = criteriaBuilder.createQuery(PropertyDetails.class);
        Root<NyRentStabilizedPropertyAddress> addressRoot = query.from(NyRentStabilizedPropertyAddress.class);
        Root<NyRentStabilizedProperty> dataRoot = query.from(NyRentStabilizedProperty.class);
        Root<NycStblzdPropertyData> unitsRoot = query.from(NycStblzdPropertyData.class);


        Predicate joinCondition1 = criteriaBuilder.equal(addressRoot.get("ucbblNumber"), dataRoot.get("ucbblNumber"));
        query.where(joinCondition1);
        // Join with custom condition: ucbblNumber = ucbbl
        Predicate joinCondition = criteriaBuilder.equal(addressRoot.get("ucbblNumber"), unitsRoot.get("ucbblNumber"));
        query.where(joinCondition);
        if (zipcode != null) {
            Predicate zipcodeCondition = criteriaBuilder.like(addressRoot.get("zip"), "%"+zipcode+"%");
            query.where(zipcodeCondition);
        }
        if (borough != null) {
            Predicate boroughCondition = criteriaBuilder.equal(addressRoot.get("borough"), borough);
            query.where(boroughCondition);
        }
        query.select(criteriaBuilder.construct(
                PropertyDetails.class,
                dataRoot,
                unitsRoot,
                addressRoot

        ));
        return entityManager.createQuery(query).getResultList();
    }


}
