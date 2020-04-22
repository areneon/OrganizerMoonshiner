package com.ptak.Moonshiner.Production.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Production {
    private int id;
    private double liters;
    private LocalDate date;


    public Production() {
    }

}
