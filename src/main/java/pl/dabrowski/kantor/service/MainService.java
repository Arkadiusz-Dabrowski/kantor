package pl.dabrowski.kantor.service;

import org.springframework.stereotype.Service;
import pl.dabrowski.kantor.GetJsons;
import pl.dabrowski.kantor.MainRepository;
import pl.dabrowski.kantor.entity.JsonValues;
import pl.dabrowski.kantor.entity.Rate;
import pl.dabrowski.kantor.entity.RatePerYear;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class MainService {

    MainRepository repository;

    ExecutorService executorService = Executors.newFixedThreadPool(3);




    MainService(MainRepository repository)  {
        this.repository = repository;
    }

    public void execute(){
        if(!repository.existsRatePerYearByYear("2018")){
            Instant a = Instant.now();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i = 2010; i<2020; i++){
                        String year = String.valueOf(i);
                        Double averagePerYear = calculateAvarage(year);
                        RatePerYear ratePerYear = new RatePerYear(i, averagePerYear);
                        repository.save(ratePerYear);
                    }
                }
            });


        Instant b = Instant.now();
        System.out.println(Duration.between(a,b).toNanos());
        }else {
               String year = String.valueOf(2019);
            Double averagePerYear = calculateAvarage(year);
            RatePerYear ratePerYear = new RatePerYear(2019, averagePerYear);
            repository.save(ratePerYear);
        }
    }


        private Double calculateAvarage(String year) {

            List<Double> mids = getMid(year);
           Double average =  mids.stream().mapToDouble(a -> a).average().getAsDouble();
            return average;
        }

        public List<RatePerYear> getAllValues () {
            return repository.findAll();
        }

    public List<Double> getMid(String year) {
        List<Double> mid = new ArrayList();
        Retrofit retroFit = new Retrofit.Builder().baseUrl("http://api.nbp.pl").addConverterFactory(GsonConverterFactory.create()).build();
        GetJsons getJsons = retroFit.create(GetJsons.class);
        Call<JsonValues> call;
        if(year.compareTo("2019") != 0)
             call = getJsons.list(year);
         else
             call = getJsons.list2(year, LocalDate.now().toString());

        Response<JsonValues> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Rate> rates = response.body().getRates();
        if(rates.get(0).getNo().compareTo("001/A/NBP/2019") == 0) {
            if(repository.existsRatePerYearByYear("Obecna wartość"))
                repository.deleteById(0);

            repository.save(new RatePerYear(0,"Obecna wartość", rates.get(rates.size()-1).getMid()));
        }

        rates.stream().forEach(rate ->
            mid.add(rate.getMid())
        );

        return mid;
        }
    }




