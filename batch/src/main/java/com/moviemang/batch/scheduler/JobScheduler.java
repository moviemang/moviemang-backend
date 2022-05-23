package com.moviemang.batch.scheduler;

import com.moviemang.batch.playlist.PlaylistJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobScheduler {

	private final JobLauncher jobLauncher;
	private final PlaylistJob playlistJob;

	// 매일 새벽 00 시 01 분 00초에 시작
	@Scheduled(cron = "0 1 0 * * ?")
//	@Scheduled(cron = "0/30 * * * * ?")
	public void collectPrevLikes() {

		Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);
		try {
			JobExecution jobExecution = jobLauncher.run(playlistJob.executePlaylistJob(), jobParameters);

			while (jobExecution.isRunning()) {
				log.info("...");
			}

		} catch (JobExecutionAlreadyRunningException
                 | JobRestartException
                 | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException e) {
			e.getMessage();
		}
	}

}
