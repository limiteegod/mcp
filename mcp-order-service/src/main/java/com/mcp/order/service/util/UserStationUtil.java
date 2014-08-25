package com.mcp.order.service.util;

import com.mcp.core.util.StringUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.model.admin.Station;

public class UserStationUtil {
	
	/**
	 * 获得用户要操作的stationId，一般渠道用户的id为head的channelCode对应的station的id，投注站则需要客户端指定
	 * @param stationService
	 * @param stationCode
	 * @param stationId
	 * @return
	 */
	public static String getStationId(Station station, String stationCode, String stationId)
	{
		String id = stationId;

		//如果是投注站的公共渠道，则stationId为订单的投注站的id，而从渠道来的票，stationId则为渠道的id
		if(station.getStationType() == ConstantValues.Station_StationType_CHANNEL.getCode())
		{
			id = station.getId();
		}
		if(StringUtil.isEmpty(id))
		{
			throw new CoreException(ErrCode.E2035, ErrCode.codeToMsg(ErrCode.E2035));
		}
		return id;
	}
}
