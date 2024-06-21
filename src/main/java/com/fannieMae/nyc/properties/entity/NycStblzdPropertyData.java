package com.fannieMae.nyc.properties.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rent_stblzd_property_units", schema = "NYC-RCU")
public class NycStblzdPropertyData {

    @Id
    @Column(name = "ucbbl_number")
    private String ucbbl;

    @Column(name = "units_total")
    private Long unitTotal;

    @Column(name = "year_built")
    private Long yearBuilt;

    @Column(name = "units_res")
    private Long unitRes;

    @Column(name = "longitude")
    private Float lon;

    @Column(name = "latitude")
    private Float lat;

    @Column(name = "number_of_buildings")
    private Long numBldgs;

    @Column(name = "number_of_floors")
    private Long numFloors;

    @JdbcTypeCode(SqlTypes.JSON)
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "json_raw_data")
    private String content;
}
