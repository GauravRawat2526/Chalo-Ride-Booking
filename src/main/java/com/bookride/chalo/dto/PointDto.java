package com.bookride.chalo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointDto {

    private double[] coordinates;

    public PointDto(double[] coordinates) {
        this.coordinates = coordinates;
    }

    private String type ="Point";
}
