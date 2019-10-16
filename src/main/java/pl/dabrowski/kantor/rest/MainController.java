package pl.dabrowski.kantor.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dabrowski.kantor.entity.RatePerYear;
import pl.dabrowski.kantor.service.MainService;
import java.util.List;


@RestController
@CrossOrigin("http://localhost:4200")
public class MainController {

    @Autowired
    MainService service;

    @GetMapping("/readValues")
    public List<RatePerYear> getAll(){
        return service.getAllValues();
    }
}