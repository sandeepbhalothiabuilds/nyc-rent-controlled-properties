package com.fannieMae.nyc.properties.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rent_stblzd_property_units", schema = "NYC-RCU")
public class NycStblzdPropertyData {

    @Id
    @Column(name = "ucbbl_number")
    private Integer ucbbl;

    @Column(name = "units_total")
    private Integer unitTotal;

    @Column(name = "year_built")
    private Integer yearBuilt;

    @Column(name = "units_res")
    private Integer unitRes;

    @Column(name = "longitude")
    private Integer lon;

    @Column(name = "latitude")
    private Integer lat;

    @Column(name = "number_of_buildings")
    private Integer numBldgs;

    @Column(name = "number_of_floors")
    private Integer numFloors;

    @JdbcTypeCode(SqlTypes.JSON)
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "json_raw_data")
    private String content;
}
