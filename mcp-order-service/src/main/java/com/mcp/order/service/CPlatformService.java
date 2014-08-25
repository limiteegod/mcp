/**
 * 
 */
package com.mcp.order.service;

import com.mcp.order.dao.admin.CPlatformDao;
import com.mcp.order.model.admin.CPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ming.li
 *
 */
@Service("cPlatformService")
public class CPlatformService {
	
	@Autowired
	private CPlatformDao cPlatformDao;
	
	public CPlatform save(CPlatform cPlatform)
	{
		return this.cPlatformDao.save(cPlatform);
	}
}
