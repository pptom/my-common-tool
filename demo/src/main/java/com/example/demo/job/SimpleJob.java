package com.example.demo.job;

import com.example.demo.service.TestService;
import com.example.demo.util.SpringUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author ptomjie
 * @since 2022-11-29 22:08
 */
public class SimpleJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SpringUtil.getBean(TestService.class).test();
    }
}
