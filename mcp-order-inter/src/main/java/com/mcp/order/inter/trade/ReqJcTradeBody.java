/**
 * 
 */
package com.mcp.order.inter.trade;

import com.mcp.core.util.StringUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.entity.BetType;
import com.mcp.order.model.entity.PlayType;
import com.mcp.order.model.ts.Game;

/**
 * @author ming.li
 *
 */
abstract class ReqJcTradeBody extends ReqBody {
	
	private ReqOrder order;
	
	public ReqOrder getOrder() {
		return order;
	}

	public void setOrder(ReqOrder order) {
		this.order = order;
	}

	/**
	 * 支付方式，默认使用投注站帐户支付
	 */
	private int payType = ConstantValues.TOrder_PayType_Cash.getCode();

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}
	
	/**
	 * 支付方式详细
	 */
	private String payTypeDes;

	public String getPayTypeDes() {
		return payTypeDes;
	}

	public void setPayTypeDes(String payTypeDes) {
		this.payTypeDes = payTypeDes;
	}

	@Override
	public void validate() throws CoreException {
		String gameCode = this.order.getGameCode();
		Game game = LotteryContext.getInstance().getGameByCode(gameCode);
		if(game == null)
		{
			throw new CoreException(ErrCode.E2007, ErrCode.codeToMsg(ErrCode.E2007));
		}
		if(game.getStatus() == ConstantValues.Game_Status_Stop.getCode())
		{
			throw new CoreException(ErrCode.E2030, ErrCode.codeToMsg(ErrCode.E2030));
		}
		PlayType pt = LotteryContext.getInstance().getPlayTypeByCode(gameCode + order.getPlayType());
		if(pt == null || pt.getStatus() == ConstantValues.PlayType_Status_Stop.getCode())
		{
			throw new CoreException(ErrCode.E2031, ErrCode.codeToMsg(ErrCode.E2031));
		}
		String betType = order.getBetType();
		String[] betTypeArray = betType.split(",");
		
		int count = 0;
		for(int i = 0; i < betTypeArray.length; i++)
		{
			String dBetType = betTypeArray[i];
			
			BetType bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + order.getPlayType() + dBetType);
			if(bt == null)
			{
				bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + order.getPlayType() + "00");
			}
			if(bt.getStatus() == ConstantValues.BetType_Status_Stop.getCode())
			{
				throw new CoreException(ErrCode.E2032, ErrCode.codeToMsg(ErrCode.E2032));
			}
			String number = order.getNumber();
			if(StringUtil.isEmpty(number))
			{
				throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
			}
			int m = Integer.parseInt(dBetType.substring(0, 1));
			int n = Integer.parseInt(dBetType.substring(1));
			count += bt.getJcValidator().validator(number, m, n);
		}
		/**
		 * 校验金额是否相符
		 */
		if(pt.getPrice()*count*order.getMultiple() != order.getAmount() || order.getAmount() <= 0)
		{
			throw new CoreException(ErrCode.E2025, ErrCode.codeToMsg(ErrCode.E2025));
		}
	}
	
}
