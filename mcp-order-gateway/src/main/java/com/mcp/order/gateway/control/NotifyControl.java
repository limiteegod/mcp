/**
 * 
 */
package com.mcp.order.gateway.control;

import com.mcp.core.util.CoreUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.JsonBody;
import com.mcp.order.gateway.annotation.JsonHead;
import com.mcp.order.gateway.annotation.McpStation;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.notify.*;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.jc.JOdds;
import com.mcp.order.model.ts.Game;
import com.mcp.order.model.ts.Term;
import com.mcp.order.model.ts.TermLog;
import com.mcp.order.mongo.service.MgOddsService;
import com.mcp.order.mongo.util.NotifyUtil;
import com.mcp.order.service.GameService;
import com.mcp.order.service.TermLogService;
import com.mcp.order.service.TermService;
import com.mcp.order.service.js.JOddsService;
import com.mcp.order.status.TermState;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author ming.li
 *
 */
@Controller
@RequestMapping("/notify")
public class NotifyControl {
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private TermLogService termLogService;
	
	@Autowired
	private JOddsService jOddsService;
	
	@Autowired
	private MgOddsService mgOddsService;
	
	private static Logger log = Logger.getLogger(NotifyControl.class);
	
	@RequestMapping(value = "/match.htm")
	public String match(@JsonHead("head") Head head, @McpStation Station station, @JsonBody(value="body", cmd="N03") ReqN03Body body,
			 ModelMap modelMap) throws Exception {
		RepN03Body repBody = new RepN03Body();
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 接受期次通知
	 * @param head
	 * @param body
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/termNotify.htm")
	public String termNotify(@JsonHead("head") Head head, @McpStation Station station, @JsonBody(value="body", cmd="N04") ReqN04Body body,
			 ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
		RepN04Body repBody = new RepN04Body();
		List<Term> termList = body.getTerms();
		for(Term t:termList)
		{
			if(t.getStatus() == TermState.NOT_ON_SALE.getCode())
			{	
				t.setId(CoreUtil.getUUID());
				t.setCreateTime(new Date());
				try {
					termService.save(t);

                    //发送新期次通知
                    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getSession().getServletContext());
                    NotifyUtil.sendN01(context, t);
				} 
				catch (Exception e)
				{
					log.error("保存新的期次信息出错:gameCode:" + t.getGameCode() + ",termCode:" + t.getCode());
					e.printStackTrace();
				}
			}
			else
			{
				Term term = termService.findOneByGameCodeAndCode(t.getGameCode(), t.getCode());
				if(term == null)
				{
					throw new CoreException(ErrCode.E2003, ErrCode.codeToMsg(ErrCode.E2003));
				}
				if(t.getStatus() == TermState.DRAW.getCode())
				{
					if(term.getStatus() < TermState.DRAW.getCode())
					{
						Game game = gameService.findOneByCode(term.getGameCode());
						termService.updateDrawInfo(t.getPrizeDesc(), t.getWinningNumber(), term.getId(), game.getType());
						
						TermLog tl = new TermLog();
						tl.setId(CoreUtil.getUUID());
						tl.setTermId(term.getId());
						tl.sethTime(new Date());
						tl.setUserId("SYSTEM");
						tl.setDescription("接受开奖通知,开奖号码为:" + t.getWinningNumber());
						
						termLogService.save(tl);
					}
					else
					{
						//如果期次已经跑到开奖（包括开奖）之后的状态，则不再接受通知
						log.info("期次已经手动开奖，数据库中开奖号码:" + term.getWinningNumber() + ",收到的开奖号码:" + t.getWinningNumber());
					}
				}
			}
		}
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	
	@RequestMapping(value = "/printNotify.htm")
	public String printNotify(@JsonHead("head") Head head, @JsonBody(value="body", cmd="N05") ReqN05Body body, 
			ModelMap modelMap) throws Exception {
		RepN05Body repBody = new RepN05Body();
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	
	@RequestMapping(value = "/receiptNotify.htm")
	public String receiptNotify(@JsonHead("head") Head head, @JsonBody(value="body", cmd="N06") ReqN06Body body, 
			ModelMap modelMap) throws Exception {
		RepN06Body repBody = new RepN06Body();
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 赔率通知接口
	 * @param head
	 * @param body
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/oddsNotify.htm")
	public String oddsNotify(@JsonHead("head") Head head, @McpStation Station station, @JsonBody(value="body", cmd="N07") ReqN07Body body,
			 ModelMap modelMap) throws Exception {
		RepN07Body repBody = new RepN07Body();
		
		List<JOdds> oddsList = body.getOddsList();
		for(JOdds odds:oddsList)
		{
			odds.setCreateTime(new Date());
			odds.setId(CoreUtil.getUUID());
			jOddsService.save(odds);
			
			this.mgOddsService.save(odds);
		}
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
}
