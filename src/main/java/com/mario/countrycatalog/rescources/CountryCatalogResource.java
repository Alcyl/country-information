package com.mario.countrycatalog.rescources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mario.countrycatalog.models.CountryInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@RestController
public class CountryCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    public final String url = "https://restcountries.eu/rest/v2/";

    @RequestMapping("/countries")
    public List<Object> getAllCountries() {
        Object[] countries = restTemplate.getForObject(url + "all", Object[].class);

        assert countries != null;
        return Arrays.asList(countries);
    }

    @RequestMapping("/countries/{countryName}")
    public Object getCountry(@PathVariable String countryName) {
        try {
            // Json to Java Object
            ObjectMapper objectMapper = new ObjectMapper();
            List<CountryInformation> cis = objectMapper.readValue(
                    new URL(url + "name/" + countryName),
                    new TypeReference<List<CountryInformation>>() {
            });

            // print borders from obj
            cis.forEach((CountryInformation element) -> {
                for (String border : element.getBorders()) {
                    System.out.println(border);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return restTemplate.getForObject(url + "name/" + countryName, Object.class);
    }
}
