package com.mcp.admin.control;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by hadoop on 2014/5/24.
 */
@Controller
@RequestMapping("/pages")
public class AdminControl {

    private static Logger log = Logger.getLogger(AdminControl.class);

    @RequestMapping("/admin/top.htm")
    public ModelAndView top(ModelMap modelMap, HttpSession session){
        String userId = (String)session.getAttribute("userId");
        modelMap.put("userId", userId);
        return new ModelAndView("/admin/top", modelMap);
    }

}
