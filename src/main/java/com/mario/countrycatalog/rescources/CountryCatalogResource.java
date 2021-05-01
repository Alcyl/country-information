package com.mario.countrycatalog.rescources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mario.countrycatalog.models.Corona.CoronaInformation;
import com.mario.countrycatalog.models.Country.CountryInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

// @RestController
@Controller
public class CountryCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * API:
     * https://restcountries.eu/
     */
    public final String urlCountry = "https://restcountries.eu/rest/v2/";
    /**
     * API:
     * https://documenter.getpostman.com/view/10808728/SzS8rjbc#intro
     */
    public final String urlCorona = "https://api.covid19api.com/";

    @RequestMapping("/countries")
    @ResponseBody
    public List<CountryInformation> getAllCountries() {
        CountryInformation[] countries = restTemplate.getForObject(urlCountry + "all", CountryInformation[].class);

        assert countries != null;
        return Arrays.asList(countries);
    }

    // TODO: Get countryName from client inputfield
    @RequestMapping("/countries/{countryName}")
    @ResponseBody
    public CountryInformation[] getCountry(@PathVariable String countryName, Model model) {
        try {
            // ggf Model raus
            model.addAttribute("countryName", countryName);
            // Json to Java Object
            ObjectMapper objectMapper = new ObjectMapper();
            List<CountryInformation> cis = objectMapper.readValue(
                    new URL(urlCountry + "name/" + countryName),
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
        return restTemplate.getForObject(urlCountry + "name/" + countryName, CountryInformation[].class);
    }

    @RequestMapping("/corona/summary")
    @ResponseBody
    public CoronaInformation getAllCoronaInformations() {
        CoronaInformation coronaInformation = restTemplate.getForObject(urlCorona + "summary", CoronaInformation.class);
        assert coronaInformation != null;
        System.out.println(coronaInformation.getGlobal().getNewConfirmed());


        return coronaInformation;
    }

    @RequestMapping("/")
    public String test(Model model) {
        CoronaInformation coronaInformation = restTemplate.getForObject(urlCorona + "summary", CoronaInformation.class);
//        System.out.println(coronaInformation.getGlobal().getNewConfirmed());
        model.addAttribute("coronaInformation", coronaInformation);

        return "index";
    }

//    TODO: GET Live By Country All Status
    //TODO: GET Live By Country And Status After Date
}
