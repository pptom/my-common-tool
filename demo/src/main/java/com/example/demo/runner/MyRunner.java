package com.example.demo.runner;

import com.example.demo.model.QuartzBean;
import com.example.demo.util.QuartzUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author ptomjie
 * @since 2022-11-29 22:12
 */
@Component
public class MyRunner implements CommandLineRunner {

    //注入任务调度
    @Autowired
    private Scheduler scheduler;


    @Override
    public void run(String... args) throws Exception {
        try {
            QuartzBean quartzBean = new QuartzBean();
            //进行测试所以写死
            quartzBean.setJobClass("com.example.demo.job.SimpleJob");
            quartzBean.setJobName("test1");
            quartzBean.setCronExpression("*/10 * * * * ?");
            QuartzUtils.createScheduleJob(scheduler,quartzBean);
        } catch (Exception e) {
        }
    }
}
