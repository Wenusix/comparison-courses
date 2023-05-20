package pl.martyna.bakula.coursesscraper;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@ConditionalOnProperty(value = "scheduler.enable", havingValue = "true")
@Component
class SchedulerJoiner {
    List<SchedulerExecution> schedulerExecution;

    public SchedulerJoiner(List<SchedulerExecution> schedulerExecution) {
        this.schedulerExecution = schedulerExecution;
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void schedulerJoiner(){
        for(SchedulerExecution element : schedulerExecution){
            element.execute();
        }
    }
}
