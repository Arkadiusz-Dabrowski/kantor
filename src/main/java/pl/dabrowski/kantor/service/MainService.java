package pl.dabrowski.kantor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.dabrowski.kantor.MainRepository;
import pl.dabrowski.kantor.entity.JsonValues;
import pl.dabrowski.kantor.entity.RatePerYear;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Service
public class MainService {

    MainRepository repository;

    private String url = "http://api.nbp.pl/api/exchangerates/rates/a/eur/2010-01-01/2010-12-31?format=json";


    MainService(MainRepository repository) {
        this.repository = repository;
    }

    private String getJsonsForYear(String year) throws IOException {
    String newUrl = (Integer.valueOf(year) == 2019) ? url.replace("2010-01-01", "2019-01-01").replace("2010-12-31", LocalDate.now().toString()) : url.replace("2010", year);
            URL url = new URL(newUrl);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String inputLine = "";
        String jsons = "";
        while ((inputLine = in.readLine()) != null)
            jsons = inputLine;
        in.close();
        jsons = jsons.substring(jsons.indexOf("["));
        return jsons;
    }

    private List<JsonValues> convertJsonsToClass(int n) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<JsonValues> values;
        values = mapper.readValue(getJsonsForYear(String.valueOf(n)), new TypeReference<List<JsonValues>>(){});
        return values;
    }

    private double averageValueFromJsons(int n) throws IOException {
        double averageValue  = 0.0;
        for (JsonValues j: convertJsonsToClass(n)
        ) {
            averageValue += Double.parseDouble(j.getMid());
        }
        return averageValue/convertJsonsToClass(n).size();
    }

    private RatePerYear jsonListToRatePerYear(int n) {
        RatePerYear ratePerYear = null;
        try {
            ratePerYear = new RatePerYear(n, String.valueOf(n), averageValueFromJsons(n));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ratePerYear;
    }

    private void saveRates(int n)  {
        if(n != 2019 && !repository.existsRatePerYearByYear(n))
            repository.save(jsonListToRatePerYear(n));
        if(n ==2019)
            repository.save(jsonListToRatePerYear(n));

    }

    public void readValuesForYear()  {
        for (int i = 2010; i < 2020; i++) {
            saveRates(i);
        }
    }

        public List<RatePerYear> getAllValues(){
            return repository.findAll();
        }
    }



