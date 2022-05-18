package com.moviemang.batch.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class TagScheduler {
    private Job job;
    private JobLauncher jobLauncher;

    @Scheduled(fixedDelay = 5*1000L)
    public void setJob(Job job) {
        try {
            jobLauncher.run(job,
                    new JobParametersBuilder().addString("datetime", LocalDateTime.now().toString()).toJobParameters()
            );
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    };
}
