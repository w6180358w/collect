package com.black.collect.boot;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.black.collect.schedule.FetchScheduler;
import com.black.collect.schedule.Scheduler;
import com.black.collect.schedule.VerifyScheduler;

/**
 * Created by hcdeng on 2017/6/30.
 */
@SpringBootApplication(scanBasePackages={"com.black.collect.api"})
public class ApplicationBoot {
    private static final List<Scheduler> schedules = Arrays.asList(
            new FetchScheduler(30, TimeUnit.MINUTES),
            new VerifyScheduler(10, TimeUnit.MINUTES)
    );

    public static void main(String[] args) {
        SpringApplication.run(ApplicationBoot.class, args);

        for(Scheduler schedule : schedules)
            schedule.schedule();
    }
}
