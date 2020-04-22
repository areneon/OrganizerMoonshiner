package com.ptak.Moonshiner.Production.API;

import com.ptak.Moonshiner.Production.Model.Production;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/production")
public class ProductionController {

    @RequestMapping("/{productionId}")
    public Production getProduction(@PathVariable("productionId") int productionId) {
        Production production = new Production(1, 2.5, LocalDate.now());

        return production;
    }


    @RequestMapping("/all")
    public List<Production> getAll() {
        Production production = new Production(1, 2.5, LocalDate.now());
        Production production2 = new Production(2, 3.5, LocalDate.now().plusDays(2));
        return Arrays.asList(production,production2);

    }
}
