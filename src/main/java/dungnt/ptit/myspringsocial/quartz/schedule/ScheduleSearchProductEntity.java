package dungnt.ptit.myspringsocial.quartz.schedule;

import dungnt.ptit.myspringsocial.quartz.job.JobSearchProductEntity;
import dungnt.ptit.myspringsocial.repository.ProductRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class ScheduleSearchProductEntity {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @PostConstruct
    public void schedulerStart() throws SchedulerException {
        final Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();
        Date afterFiveSeconds = Date.from(LocalDateTime.now().plusSeconds(5).atZone(ZoneId.systemDefault()).toInstant());

        JobDetail jobDetail1 = JobBuilder.newJob(JobSearchProductEntity.class).usingJobData("param", "value1").build();
        Trigger trigger1 = TriggerBuilder.newTrigger().startAt(afterFiveSeconds).build();
        scheduler.scheduleJob(jobDetail1, trigger1);
    }
}
