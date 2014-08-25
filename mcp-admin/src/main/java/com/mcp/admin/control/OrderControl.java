package com.mcp.admin.control;

import com.mcp.admin.util.MainInterUtil;
import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.StringUtil;
import com.mcp.core.util.cons.GameType;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by hadoop on 2014/5/24.
 */
@Controller
@RequestMapping("/pages")
public class OrderControl {

    private static Logger log = Logger.getLogger(OrderControl.class);

    @RequestMapping("/admin/order/normalQuery.htm")
    public ModelAndView detailInfo(@RequestParam(value = "gameCode", required = false) String gameCode, @RequestParam(value = "termCode", required = false) String termCode, @RequestParam(value = "frameId", required = false) String frameId, @RequestParam(value = "schemeId", required = false) String schemeId, ModelMap modelMap, HttpServletRequest request) throws Exception {
        modelMap.put("gameCode", gameCode);
        modelMap.put("termCode", termCode);
        modelMap.put("frameId", frameId);
        modelMap.put("schemeId", schemeId);

        return new ModelAndView("/admin/order/normalQuery", modelMap);
    }

}
