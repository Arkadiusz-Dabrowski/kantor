package pl.dabrowski.kantor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.dabrowski.kantor.service.MainService;

@EnableScheduling
@Configuration
public class Schedule {

    @Autowired
    MainService mainService;

    public Schedule(MainService mainService) {
        this.mainService = mainService;
    }

    @Scheduled(fixedRate=24*60*60*1000)
    public void doScheduledTask(){
        mainService.execute();
    }
}
