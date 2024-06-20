package com.fannieMae.nyc.properties.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NycPropertyData {

    @Id
    private String propertyId;

    @Column(name = "")
    private String borough;

    @Column(name = "")
    private Long ucbbl;

    @Column(name = "")
    private String uc;

    @Column(name = "")
    private String est;

    @Column(name = "")
    private String dhcr;

    @Column(name = "")
    private String abat;

    @Column(name = "")
    private String cd;

    @Column(name = "")
    private String ct2010;

    @Column(name = "")
    private String cb010;

    @Column(name = "")
    private String council;

    @Column(name = "")
    private Long zip;

    @Column(name = "")
    private String address;

    @Column(name = "")
    private String ownerName;

    @Column(name = "")
    private Long numBldgs;

    @Column(name = "")
    private Long numFloors;

    @Column(name = "")
    private Long unitRes;

    @Column(name = "")
    private Long unitTotal;

    @Column(name = "")
    private Long yearBuilt;

    @Column(name = "")
    private Long condono;

    @Column(name = "")
    private Long lon;

    @Column(name = "")
    private Long lat;


}
