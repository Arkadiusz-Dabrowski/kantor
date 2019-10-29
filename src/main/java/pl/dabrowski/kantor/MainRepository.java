package pl.dabrowski.kantor;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.dabrowski.kantor.entity.RatePerYear;



@Repository
public interface MainRepository extends MongoRepository<RatePerYear,Integer> {


    boolean existsRatePerYearByYear(String year);

}
