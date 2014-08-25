/**
 * 
 */
package com.mcp.order.service.common;

import com.mcp.order.model.ts.Term;
import com.mcp.order.model.ts.TermLog;
import com.mcp.order.service.TermLogService;
import com.mcp.order.service.TermService;
import com.mcp.order.status.StateCallback;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author ming.li
 *
 */
public class DefaultStateCallbackImpl implements StateCallback {
	
	private TermService termService;
	
	@Autowired
	private TermLogService termLogService;
	
	public DefaultStateCallbackImpl(TermService termService)
	{
		this.termService = termService;
	}

	/* (non-Javadoc)
	 * @see com.mcp.core.state.StateCallback#run()
	 */
	@Override
	public boolean run() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.mcp.core.state.StateCallback#update(int, java.util.Properties)
	 */
	@Override
	public void update(int state, Properties param) {
		String termId = param.getProperty("TERM");
		boolean updateDb = Boolean.valueOf(param.getProperty("updateDb"));
		if(updateDb)
        {
        	termService.updateStatusById(state, termId);
        }
		
		TermLog tl = new TermLog();
        tl.setId(UUID.randomUUID().toString().replace("-", ""));
        tl.setUserId(param.getProperty("USER"));
        tl.setTermId(param.getProperty("TERM"));
        tl.sethTime(new Date());
        tl.setDescription(param.getProperty("DESC"));
        tl.setHandleCode(Integer.parseInt(param.getProperty("HANDLER")));
        termLogService.save(tl);
		
		/**
		 * 更新奖期状态的时候，同步更新缓存
		 * 现只更新奖期状态。
		 */
		Term t = termService.findOne(termId);
		//redisHelp.set(RedisKey.getTermStatus(t.getGameCode(), t.getCode()), state + "");
		List<Term> tList = new ArrayList<Term>();
		tList.add(t);
	}

}
