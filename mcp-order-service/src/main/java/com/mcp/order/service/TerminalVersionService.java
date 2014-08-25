package com.mcp.order.service;

import com.mcp.order.dao.TerminalVersionDao;
import com.mcp.order.model.admin.TerminalVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("terminalVersionService")
public class TerminalVersionService {
	
	@Autowired
	private TerminalVersionDao terminalVersionDao;
	
	public TerminalVersion save(TerminalVersion terminalVersion)
	{
		return this.terminalVersionDao.save(terminalVersion);
	}
	
}
