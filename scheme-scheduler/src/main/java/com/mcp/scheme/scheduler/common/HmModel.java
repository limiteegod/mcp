package com.mcp.scheme.scheduler.common;

import com.mcp.core.util.CoreUtil;
import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;

/**
 * Created by ming.li on 2014/4/14.
 */
public class HmModel {

    private static Logger log = Logger.getLogger(HmModel.class);

    /**
     * spring的上下文
     */
    private ApplicationContext context;

    public HmModel()
    {
        this.context = SchemeContext.getInstance().getSpringContext();
    }

    /**
     * 获得job的公有的参数
     */
    private JobParametersBuilder getParamBuilder()
    {
        //准备参数
        JobParametersBuilder jobPara = new JobParametersBuilder();
        jobPara.addString("key", CoreUtil.getUUID());
        return jobPara;
    }

    /**
     * 取消过期方案
     * @throws Exception
     */
    public void cancel() throws Exception
    {
        log.info("扫描合买方案表,取消过期方案.........................");
        Job job = (Job)context.getBean("hmCancelJob");
        JobLauncher launcher = (JobLauncher)context.getBean("syncJobLauncher");
        launcher.run(job, getParamBuilder().toJobParameters());
    }

    /**
     * 对等待返奖的合买方案，进行返奖
     * @throws Exception
     */
    public void prize() throws Exception
    {
        log.info("扫描合买方案表,对等待返奖的方案进行返奖.........................");
        Job job = (Job)context.getBean("hmPrizeJob");
        JobLauncher launcher = (JobLauncher)context.getBean("syncJobLauncher");
        launcher.run(job, getParamBuilder().toJobParameters());
    }

}
