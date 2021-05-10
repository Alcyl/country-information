package com.mario.countrycatalog.rescources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mario.countrycatalog.models.Corona.CoronaInformation;
import com.mario.countrycatalog.models.Corona.CountryLiveStatus;
import com.mario.countrycatalog.models.Corona.CountryLiveWithDateRange;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

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

        return coronaInformation;
    }

    @RequestMapping("/")
    public String index(Model model) {
        CoronaInformation coronaInformation = restTemplate.getForObject(urlCorona + "summary", CoronaInformation.class);
        model.addAttribute("coronaInformation", coronaInformation);

        return "index";
    }

    @RequestMapping("/search/{countryName}")
    public String searchCountry(@PathVariable(value = "countryName") String countryName, Model model) {
        CountryLiveStatus[] coronaInformationByCountry = restTemplate.getForObject(urlCorona + "live/country/" + countryName, CountryLiveStatus[].class);
        model.addAttribute("countryName", coronaInformationByCountry);

        Stream<CountryLiveStatus> countryLiveStatusStream = Arrays.stream(coronaInformationByCountry).filter(distinctByKey(CountryLiveStatus::getProvince));
        countryLiveStatusStream.forEach((CountryLiveStatus element) -> {
            System.out.println(element.getProvince());
        });

        return "search";
    }


    /**
     * GET Information by date and country. Time is ignored 00:00:00Z
     * TODO: rethink monthly view
     * By Country Live
     * https://api.covid19api.com/country/south-africa/status/confirmed/live?from=2020-03-01T00:00:00Z&to=2020-04-01T00:00:00Z
     * status: confirmed, recovered, deaths
     */
    @RequestMapping("/search/{countryName}/{statusName}/{from}/{to}")
    public String getCountryStatusByRange(
            @PathVariable(value = "countryName") String countryName,
            @PathVariable(value = "statusName") String statusName,
            @PathVariable(value = "from") String from,
            @PathVariable(value = "to") String to,
            Model model) {
        CountryLiveWithDateRange[] countryLiveWithDateRange = restTemplate.getForObject(urlCorona + "/country/" +
                countryName + "/status/" + statusName + "/live?from=" + from + "&to=" + to, CountryLiveWithDateRange[].class);
        model.addAttribute("countryLiveWithDateRange", countryLiveWithDateRange);

        List<Integer> filteredList = new ArrayList<Integer>();
        for (CountryLiveWithDateRange element : countryLiveWithDateRange) {
            int lastDayOfMonth = element.getDate().getActualMaximum(Calendar.DATE);
            int day = element.getDate().get(Calendar.DAY_OF_MONTH);
            if(lastDayOfMonth == day) {
                filteredList.add(element.getCases());
            }
        }
        model.addAttribute("coronaEndOfMonth", filteredList);

        return "search";
    }


    // get only distinct entries
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

//    TODO: GET Live By Country All Status
    //TODO: GET Live By Country And Status After Date
}
