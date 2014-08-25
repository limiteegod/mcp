/**
 * 
 */
package com.mcp.order.batch.check;

import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;

/**
 * @author ming.li
 *
 */
public interface Check {
	
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException;

}
