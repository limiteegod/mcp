package com.mcp.scheduler.run;

import com.mcp.core.util.CoreUtil;
import com.mcp.scheduler.common.SchConstants;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.text.SimpleDateFormat;

/**
 * Created by CH on 2014/12/19.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new FileSystemXmlApplicationContext(new String[]{"classpath*:applicationContext-scheduler.xml"});
        //返奖
        Job job = (Job)context.getBean("pJob");
        JobLauncher launcher = (JobLauncher)context.getBean("syncJobLauncher");
        try {
            launcher.run(job, getParamBuilder().toJobParameters());
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }

    }
    /**
     * 获得job的公有的参数
     */
    public static JobParametersBuilder getParamBuilder() throws Exception
    {
        //准备参数
        JobParametersBuilder jobPara = new JobParametersBuilder();
        jobPara.addString("key", CoreUtil.getUUID());
        jobPara.addString("gameCode", "F01");
        jobPara.addString("gameType", "1");
        jobPara.addString("termId", "3a1b879830744419a5b38c546103020c");
        jobPara.addString("termCode", "2014147");
        jobPara.addString("nextTermCode", "2014148");
        SimpleDateFormat sig = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String  iFolder = SchConstants.getIssueFolder("F01", "2014147", 1, sig.parse("2014-12-18 19:55:00"));
        jobPara.addString("iFolder", iFolder);
        return jobPara;
    }

}
