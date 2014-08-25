package com.mcp.scheme.scheduler.quartz;

import com.mcp.scheme.scheduler.common.HmModel;
import org.apache.log4j.Logger;

public class QuartzJob {
	
	private static Logger log = Logger.getLogger(QuartzJob.class);
	
	public void cancleHm() throws Exception
	{
        new HmModel().cancel();
	}

	public void prizeHm() throws Exception
	{
        new HmModel().prize();
	}
}
