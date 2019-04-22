package com.ms.zg.book.quartz.job;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class SchedulerCustomizeFactoryBean implements SchedulerFactoryBeanCustomizer {
    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setOverwriteExistingJobs(true);
    }
}

