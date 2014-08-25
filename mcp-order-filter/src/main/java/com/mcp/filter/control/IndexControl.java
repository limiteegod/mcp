package com.mcp.filter.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/pages")
public class IndexControl {
	
	
	@RequestMapping("/login.htm")
	public ModelAndView login(ModelMap modelMap){
		return new ModelAndView("/login", modelMap);
	}
}
