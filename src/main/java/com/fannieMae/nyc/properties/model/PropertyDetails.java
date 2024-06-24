package com.fannieMae.nyc.properties.model;

import com.fannieMae.nyc.properties.entity.NyRentStabilizedProperty;
import com.fannieMae.nyc.properties.entity.NyRentStabilizedPropertyAddress;
import com.fannieMae.nyc.properties.entity.NycStblzdPropertyData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data  // This annotation combines @Getter, @Setter, @EqualsAndHashCode, @ToString
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDetails {
    private NyRentStabilizedProperty property;
    private NycStblzdPropertyData units;
    private NyRentStabilizedPropertyAddress addresses;
}
