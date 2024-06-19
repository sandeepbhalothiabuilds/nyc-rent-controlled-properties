package com.fannieMae.nyc.properties.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nyc_rcu_listings", schema = "NYC-RCU")
public class NycRcuListings {

    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nyc_rcu_listings_seq")
    @SequenceGenerator(name = "nyc_rcu_listings_seq", sequenceName = "\"nyc_rcu_listings_seq\"", allocationSize = 1, schema = "NYC-RCU")
    @Column(name = "nyc_rcu_listings_id")
    private Long nycRcuListingsId;

    @Column(name = "ucbbl")
    private Long ucbbl;

    @Column(name = "zip_building_block_lot")
    private String zipBuildingBlockLot;

    @Column(name = "status1")
    private String status1;

    @Column(name = "status2")
    private String status2;

    @Column(name = "status3")
    private String status3;

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

    @JdbcTypeCode(SqlTypes.JSON)
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content")
    private String content;

}
