package dungnt.ptit.myspringsocial.quartz.config;

import org.quartz.SchedulerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.util.Properties;

@Configuration
public class QuartzConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SchedulerFactoryBean customSchedulerFactoryBean1() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        Properties properties = new Properties();
        AutowiringSpringBeanJobFactory springBeanJobFactory = new AutowiringSpringBeanJobFactory();

        properties.setProperty("org.quartz.threadPool.threadNamePrefix", "my-custom-scheduler1_Worker");
        properties.setProperty(SchedulerFactoryBean.PROP_THREAD_COUNT, "10");

        springBeanJobFactory.setApplicationContext(applicationContext);
        factory.setQuartzProperties(properties);
        factory.setJobFactory(springBeanJobFactory);
        return factory;
    }


}
