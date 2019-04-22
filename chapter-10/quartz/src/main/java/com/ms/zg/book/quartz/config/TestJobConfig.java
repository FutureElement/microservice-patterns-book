package com.ms.zg.book.quartz.config;

import com.ms.zg.book.quartz.job.TestJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class TestJobConfig {
    @Bean
    public CronTriggerFactoryBean testJobTrigger(JobDetail testJobDetail,
                                                 @Value("${testJob.cron}") String cronExpression) {
        CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
        triggerFactoryBean.setCronExpression(cronExpression);
        triggerFactoryBean.setJobDetail(testJobDetail);
        return triggerFactoryBean;
    }

    @Bean
    public JobDetailFactoryBean testJobDetail() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(TestJob.class);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }
}

