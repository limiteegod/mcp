/**
 * 
 */
package com.mcp.admin.control;

import com.mcp.admin.cons.SiteConfig;
import com.mcp.admin.util.MainInterUtil;
import com.mcp.core.util.cons.SystemUserType;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.ts.Game;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.mcp.order.inter.util.InterUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ming.li
 *
 */
@Controller
@RequestMapping("/main")
public class MainControl {
	
	private static Logger log = Logger.getLogger(MainControl.class);
	
	@RequestMapping(value = "/interface.htm")
	public String inter(@RequestParam("body") String body, @RequestParam("cmd") String cmd, ModelMap modelMap,
                        HttpServletRequest request) throws Exception {
        String repBody = MainInterUtil.getData(request, cmd, body);
		modelMap.put("response", repBody);
		return "plainJsonView";
	}

}
