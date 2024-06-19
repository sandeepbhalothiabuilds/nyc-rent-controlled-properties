package com.fannieMae.nyc.properties.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nyc_rcu_listings_address")
public class NycRcuListingsAddress {
    @Id
    @GeneratedValue(generator = "nyc_rcu_listings_address_seq")
    @SequenceGenerator(name = "nyc_rcu_listings_address_seq", sequenceName = "nyc_rcu_listings_address_seq", allocationSize = 1)
    @Column(name = "nyc_rcu_listings_address_id")
    private Long nycRcuListingsAddressId;

    @Column(name = "ucbbl")
    private String ucbbl;

    @Column(name = "zip_building_block_lot")
    private String zipBuildingBlockLot;

    @Column(name = "zip")
    private String zip;

    @Column(name = "bldgno")
    private String bldgno;

    @Column(name = "street")
    private String street;

    @Column(name = "stsufx")
    private String stsufx;

    @Column(name = "city")
    private String city;

    @Column(name = "county")
    private String county;

    @Column(name = "block")
    private String block;

    @Column(name = "lot")
    private String lot;

    @Column(name = "borough")
    private String borough;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private Date lastUpdatedDate;

}
