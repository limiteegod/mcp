/**
 * 
 */
package com.mcp.order.batch.prize;

import com.mcp.order.model.admin.Station;
import com.mcp.order.model.ts.TOrder;

/**
 * @author ming.li
 * 渠道返奖接口
 */
public interface CPrizeInterface {
	
	/**
	 * 渠道，对某个订单返奖
	 * @param channel 渠道
	 * @param order 订单
	 * @throws Exception
	 */
	public void prize(Station station, TOrder order) throws Exception;
	
}
