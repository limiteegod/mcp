package com.mcp.order.gateway.util;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.admin.StationGame;
import com.mcp.order.service.StationGameService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StationGameUtil {

	
	public static List<String> checkStationGame(StationGameService stationGameService, Station station, String outStationId, String gameCode, Date endTime)
	{
		List<String> idList = new ArrayList<String>();
		String stationId;
		//如果是投注站的公共渠道，则stationId为订单的投注站的id，而从渠道来的票，stationId则为渠道的id
		if(station.getStationType() == ConstantValues.Station_StationType_CHANNEL.getCode())
		{
			stationId = station.getId();
		}
		else if(station.getStationType() == ConstantValues.Station_StationType_DEFAULT.getCode())
		{
			stationId = outStationId;
		}
		else
		{	
			//不支持的渠道类型
			throw new CoreException(ErrCode.E2030, ErrCode.codeToMsg(ErrCode.E2030));
		}
		
		//渠道是否销售此游戏
		StationGame channelGame = stationGameService.findOneByStationIdAndGameCodeAndStatus(stationId, gameCode, ConstantValues.StationGame_Status_Open.getCode());
		if(channelGame == null)
		{
			throw new CoreException(ErrCode.E2030, ErrCode.codeToMsg(ErrCode.E2030));
		}
		
		//投注站是否已经提前停售
        Date now = new Date();
        int minute = channelGame.getEarlyStopBufferSimplex();
        int second = minute * 60 * 1000;
        if (endTime.getTime() - second - now.getTime() <= 0) {
            throw new CoreException(ErrCode.E2037, ErrCode.codeToMsg(ErrCode.E2037));
        }
		
		//要转到的目标投注站的id
		String printStationId = channelGame.getRelayToId();
		//确保转到的投注站也支持此游戏
		if(!printStationId.equals(stationId))
		{
			//如果发生了转票操作，目标投注站不支持此游戏或者目标投注站对此游戏进行了转票，则不支持
			StationGame printChannelGame = stationGameService.findOneByStationIdAndGameCodeAndStatus(printStationId, gameCode, ConstantValues.StationGame_Status_Open.getCode());
			if(printChannelGame == null || !printStationId.equals(printChannelGame.getRelayToId()))
			{
				throw new CoreException(ErrCode.E2030, ErrCode.codeToMsg(ErrCode.E2030));
			}
		}
		idList.add(stationId);
		idList.add(printStationId);
		return idList;
	}
}
