package com.ptak.Moonshiner.Production.API;

import com.ptak.Moonshiner.Production.Model.Production;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/production")
public class ProductionController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;

    @Value("${persistance.application.name}")
    private String persistanceApplicationName;

    @RequestMapping("/{productionId}")
    public Production getProduction(@PathVariable("productionId") Long productionId) {
        Production production = webClientBuilder.build()
                .get()
                .uri("http://" + persistanceApplicationName + "/moonshine/production/" + productionId)
                .retrieve()
                .bodyToMono(Production.class)
                .block();

        return production;
    }


    @RequestMapping("/all")
    public List<Production> getAll() {

        List<Production> productions = Arrays.asList(webClientBuilder.build()
                .get()
                .uri("http://" + persistanceApplicationName + "/moonshine/production/all")
                .retrieve()
                .bodyToMono(Production[].class)
                .block());

        return productions;

    }

}
