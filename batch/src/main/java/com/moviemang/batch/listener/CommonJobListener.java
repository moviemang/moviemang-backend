package com.moviemang.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * 공통 JobListener
 */
@Slf4j
public class CommonJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job Id => {}", jobExecution.getJobId());
        log.info("Job StartTime => {}", jobExecution.getStartTime());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Job EndTime => {}", jobExecution.getEndTime());
        if(jobExecution.getStatus().equals(BatchStatus.FAILED)){
            log.error("배치 작업에 실패하였습니다. Job Id => {}", jobExecution.getJobId());
        }
    }
}
