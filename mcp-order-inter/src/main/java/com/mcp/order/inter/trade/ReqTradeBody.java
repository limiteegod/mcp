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
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author ming.li
 *
 */
abstract class ReqTradeBody extends ReqBody {
	
	private static Logger log = Logger.getLogger(ReqTradeBody.class);
	
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
		List<ReqTicket> ticketList = this.order.getTickets();
		int size = ticketList.size();
		if(size == 0)
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		int allTicketsAmount = 0;
		for(int i = 0; i < size; i++)
		{
			ReqTicket ticket = ticketList.get(i);
			PlayType pt = LotteryContext.getInstance().getPlayTypeByCode(gameCode + ticket.getPlayTypeCode());
			if(pt == null || pt.getStatus() == ConstantValues.PlayType_Status_Stop.getCode())
			{
				throw new CoreException(ErrCode.E2031, ErrCode.codeToMsg(ErrCode.E2031));
			}
			BetType bt = LotteryContext.getInstance().getBetTypeByCode(gameCode + ticket.getPlayTypeCode() + ticket.getBetTypeCode());
			if(bt == null || bt.getStatus() == ConstantValues.BetType_Status_Stop.getCode())
			{
				throw new CoreException(ErrCode.E2032, ErrCode.codeToMsg(ErrCode.E2032));
			}
			String number = ticket.getNumbers();
			if(StringUtil.isEmpty(number))
			{
				throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
			}
			if(number.split(";").length > 5)
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
			}
			int count = bt.getValidator().validator(number);
			if(game.getType() == ConstantValues.Game_Type_Jingcai.getCode())
			{
				
			}
			if(count <= 0)
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
			}
			log.info("注数为：" + count);
			//订单和票的倍数必须一致
			if(ticket.getMultiple() != order.getMultiple())
			{
				throw new CoreException(ErrCode.E2051, ErrCode.codeToMsg(ErrCode.E2051));
			}
			/**
			 * 校验金额是否相符
			 */
			if(pt.getPrice()*count*ticket.getMultiple() != ticket.getAmount() || ticket.getAmount() <= 0)
			{
				throw new CoreException(ErrCode.E2025, ErrCode.codeToMsg(ErrCode.E2025));
			}
			allTicketsAmount += ticket.getAmount();
		}
		if(allTicketsAmount != this.order.getAmount() || this.order.getAmount() <= 0)
		{
			throw new CoreException(ErrCode.E2025, ErrCode.codeToMsg(ErrCode.E2025));
		}
	}
	
}
