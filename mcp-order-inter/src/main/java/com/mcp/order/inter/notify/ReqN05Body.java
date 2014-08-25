/**
 * 
 */
package com.mcp.order.inter.notify;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.model.ts.TOrder;


/**
 * @author ming.li
 *
 */
public class ReqN05Body extends ReqBody {
	
	private TOrder order;

	public TOrder getOrder() {
		return order;
	}

	public void setOrder(TOrder order) {
		this.order = order;
	}

	@Override
	public void validate() throws CoreException {
		
	}
}
