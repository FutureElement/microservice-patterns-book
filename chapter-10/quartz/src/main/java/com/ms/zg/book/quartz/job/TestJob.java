package com.ms.zg.book.quartz.job;

import org.quartz.JobExecutionContext;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TestJob extends QuartzJobBean {
    @Override
    protected void executeInternal(@Nullable JobExecutionContext context) {
        System.out.println("test Job2: " + LocalDateTime.now().toString());
    }
}

