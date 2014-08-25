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
public class TermControl {

    private static Logger log = Logger.getLogger(TermControl.class);

    @RequestMapping("/admin/term/detailInfo.htm")
    public ModelAndView detailInfo(@RequestParam(value = "gameCode", required = false) String gameCode, @RequestParam(value = "termCode", required = false) String termCode, @RequestParam(value = "frameId", required = false) String frameId, ModelMap modelMap, HttpServletRequest request) throws Exception {
        modelMap.put("gameCode", gameCode);
        modelMap.put("termCode", termCode);
        modelMap.put("frameId", frameId);
        if(!StringUtil.isEmpty(gameCode) && !StringUtil.isEmpty(termCode))
        {
            //期次信息
            ObjectMapper om = new ObjectMapper();
            ObjectNode bodyNode = om.createObjectNode();
            bodyNode.put("uniqueId", CoreUtil.getUUID());
            bodyNode.put("gameCode", gameCode);
            bodyNode.put("termCode", termCode);
            String body = om.writeValueAsString(bodyNode);

            String repBody = MainInterUtil.getData(request, "AD04", body);
            modelMap.put("termData", om.readTree(repBody));

            //报表信息
            bodyNode = om.createObjectNode();
            bodyNode.put("uniqueId", CoreUtil.getUUID());
            bodyNode.put("gameCode", gameCode);
            bodyNode.put("termCode", termCode);
            bodyNode.put("size", 10000);
            body = om.writeValueAsString(bodyNode);

            repBody = MainInterUtil.getData(request, "AD03", body);
            modelMap.put("termInfoData", om.readTree(repBody));
        }
        return new ModelAndView("/admin/term/detailInfo", modelMap);
    }


    @RequestMapping("/admin/term/onSale.htm")
    public ModelAndView onSale(@RequestParam(value = "gameType", required = false) String gameType, ModelMap modelMap, HttpSession session){
        GameType toGameType = GameType.NORMAL;
        if(!StringUtil.isEmpty(gameType))
        {
            toGameType = GameType.values()[Integer.parseInt(gameType)];
        }

        modelMap.put("gameType", toGameType.ordinal());
        return new ModelAndView("/admin/term/onSale", modelMap);
    }
}
