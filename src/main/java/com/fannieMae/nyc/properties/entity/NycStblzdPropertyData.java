package com.fannieMae.nyc.properties.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rent_stblzd_property_units_seq")
    @SequenceGenerator(name = "rent_stblzd_property_units_seq", sequenceName = "\"rent_stblzd_property_units_seq\"", allocationSize = 1, schema = "NYC-RCU")
    @Column(name = "rent_stblzd_property_units_id")
    private Long rentStabilizedPropertyUnitsId;

    @Column(name = "ucbbl_number")
    private String ucbbl;

    @Column(name = "units_total")
    private String unitTotal;

    @Column(name = "year_built")
    private Long yearBuilt;

    @Column(name = "units_res")
    private String unitRes;

    @Column(name = "longitude")
    private String lon;

    @Column(name = "latitude")
    private String lat;

    @Column(name = "number_of_buildings")
    private String numBldgs;

    @Column(name = "number_of_floors")
    private String numFloors;

    @JdbcTypeCode(SqlTypes.JSON)
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "json_raw_data")
    private String content;
}
