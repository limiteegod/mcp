/**
 * 
 */
package com.mcp.order.inter.notify;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.TOrder;


/**
 * @author ming.li
 *
 */
public class RepN02Body extends RepBody {
	
	private TOrder order;

	public TOrder getOrder() {
		return order;
	}

	public void setOrder(TOrder order) {
		this.order = order;
	}
}
