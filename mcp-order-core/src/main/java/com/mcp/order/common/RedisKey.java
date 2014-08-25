/**
 * 
 */
package com.mcp.order.common;

/**
 * @author ming.li
 *
 */
public class RedisKey {
	
	/**
	 * 竞彩，算奖标志，为了保证竞彩每个游戏，同时只有一个游戏在进行算奖的操作
	 */
	public static final String JC_CHECK_FLAG = "JC:CHECK:FLAG";
	
	/**
	 * 游戏的预售期次队列，包括当前期
	 */
	public static final String GAME_TERM_SALE_QUEEN = "SALE:QUEEN";
	
	/**
	 * 竞彩在售期次列表
	 */
	public static final String JC_TERM_ON_SALE_QUEEN = "JC:TERM:ON:SALE:QUEEN";
	
	
	/**
	 * 用于统计实时交易额
	 */
	public static final String GAME_TERM_CHANNEL_SALE = "GAME:TERM:CHANNEL:SALE";
	
	/**
	 * 用于统计实时交易额
	 */
	public static final String GAME_TERM_SALE = "GAME:TERM:SALE";
	
	/**
	 * 用于统计实时交易额
	 */
	public static final String GAME_SALE = "GAME:SALE";
	
	/**
	 * 游戏的当前状态
	 */
	public static final String TERM_STATUS = "TERM:STATUS";
	
	/**
	 * 手机验证码
	 */
	public static final String PHONE_AUTHCODE = "PHONE:AUTHCODE";
	
	/**
	 * 游戏的当前算奖的进度
	 */
	public static final String TERM_PROGRESS_DRAW = "TERM:PROGRESS:DRAW";
	
	/**
	 * 游戏的当前停售的进度
	 */
	public static final String TERM_PROGRESS_CLOSE = "TERM:PROGRESS:CLOSE";
	
	/**
	 * 游戏的当前与各渠道同步的进度
	 */
	public static final String TERM_PROGRESS_SYCHRONIZE = "TERM:PROGRESS:SYCHRONIZE";
	
	/**
	 * 游戏的当前返奖的进度
	 */
	public static final String TERM_PROGRESS_PRIZE = "TERM:PROGRESS:PRIZE";
	
	/**
	 * 期次方案处理的进度
	 */
	public static final String TERM_PROGRESS_SCHEME = "TERM:PROGRESS:SCHEME";
	
	
	/**
	 * 通知的时间队列
	 */
	public static final String CUSTOMER_NOTIFY_TIME = "NOTIFY:TIME";
	
	/**
	 * 通知的内容队列
	 */
	public static final String CUSTOMER_NOTIFY_CONTENT = "NOTIFY:CONTENT";
	
	/**
	 * 
	 */
	public static final String SYSTEM_BALANCE = "SYSTEM:BANLANCE";
	
	/**
	 * 系统发送通知的渠道列表
	 */
	public static final String NOTIFY_CHANNEL_LIST = "NOTIFY:CHANNEL:LIST";
	
	/**
	 * 渠道的通知
	 */
	public static final String CHANNEL_NOTIFY = "CHANNEL:NOTIFY";
	
	/**
	 * 订单类型，在订单生成的时，记录订单类型，等待第三方支付完成
	 */
	public static final String ORDER_TYPE = "ORDER:TYPE";
	
	/**
	 * 渠道的通知地址
	 */
	public static final String CHANNEL_NOTIFY_URL = "CHANNEL:NOTIFY:URL";
	
	/**
	 * 游戏期次出票成功的票据列表
	 */
	public static final String GAME_TERM_TICKET_LIST = "GAME:TERM:TICKET:LIST";
	
	/**
	 * 游戏期次出票成功的订单列表
	 */
	public static final String GAME_TERM_ORDER_LIST = "GAME:TERM:ORDER:LIST";
	
	/**
	 * 游戏期次出票成功的订单列表，大奖
	 */
	public static final String GAME_TERM_ORDER_BIG_HIT_LIST = "GAME:TERM:ORDER:BIG:HIT:LIST";
	
	/**
	 * 游戏期次出票成功的订单列表，小奖
	 */
	public static final String GAME_TERM_ORDER_LITTLE_HIT_LIST = "GAME:TERM:ORDER:LITTLE:HIT:LIST";
	
	/**
	 * 游戏期次出票成功的订单列表，未中奖
	 */
	public static final String GAME_TERM_ORDER_NOT_HIT_LIST = "GAME:TERM:ORDER:NOT:HIT:LIST";
	
	/**
	 * 游戏期次出票成功的方案列表
	 */
	public static final String GAME_TERM_SCHEME_LIST = "GAME:TERM:SCHEME:LIST";
	
	/**
	 * 竞彩赔率信息
	 */
	public static final String JC_ODDS = "JC:ODDS";
	
	/**
	 * 通过gameCode获得游戏的预售期次队列，包括当前期
	 * @param gameCode
	 * @return
	 */
	public static String getGameTermSaleQueenByGameCode(String gameCode)
	{
		return gameCode + ":" + GAME_TERM_SALE_QUEEN;
	}
	
	/**
	 * 通过gameCode获得游戏的预售期次队列，包括当前期
	 * @param gameCode
	 * @return
	 */
	public static String getGameTermSaleQueenByGameCodeAndTermCode(String gameCode, String termCode)
	{
		return gameCode + ":" + termCode + ":" + GAME_TERM_SALE_QUEEN;
	}
	
	/**
	 * 获得指定奖期的状态
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getTermStatus(String gameCode, String termCode)
	{
		return TERM_STATUS + ":" + gameCode + ":" + termCode;
	}
	
	/**
	 * 获得指定奖期的算奖的所有渠道数目
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getTermProgressDrawAll(String gameCode, String termCode)
	{
		return TERM_PROGRESS_DRAW + ":" + gameCode + ":" + termCode + ":ALL";
	}
	
	/**
	 * 获得指定奖期的停售的所有渠道数目
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getTermProgressCloseAll(String gameCode, String termCode)
	{
		return TERM_PROGRESS_CLOSE + ":" + gameCode + ":" + termCode + ":ALL";
	}
	
	/**
	 * 获得指定奖期的参与同步的所有渠道数目
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getTermProgressSychronizeAll(String gameCode, String termCode)
	{
		return TERM_PROGRESS_SYCHRONIZE + ":" + gameCode + ":" + termCode + ":ALL";
	}
	
	/**
	 * 获得指定奖期的已经完成同步的所有渠道数目
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getTermProgressSychronizeFinished(String gameCode, String termCode)
	{
		return TERM_PROGRESS_SYCHRONIZE + ":" + gameCode + ":" + termCode + ":FINISHED";
	}
	
	/**
	 * 获得指定奖期的算奖的已经完成的渠道数目
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getTermProgressDrawFinished(String gameCode, String termCode)
	{
		return TERM_PROGRESS_DRAW + ":" + gameCode + ":" + termCode + ":FINISHED";
	}
	
	/**
	 * 获得指定奖期的停售的已经完成的渠道数目
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getTermProgressCloseFinished(String gameCode, String termCode)
	{
		return TERM_PROGRESS_CLOSE + ":" + gameCode + ":" + termCode + ":FINISHED";
	}
	
	/**
	 * 获得指定奖期的返奖的所有渠道数目
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getTermProgressPrizeAll(String gameCode, String termCode)
	{
		return TERM_PROGRESS_PRIZE + ":" + gameCode + ":" + termCode + ":ALL";
	}
	
	/**
	 * 获得指定奖期的返奖的已经完成的渠道数目
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getTermProgressPrizeFinished(String gameCode, String termCode)
	{
		return TERM_PROGRESS_PRIZE + ":" + gameCode + ":" + termCode + ":FINISHED";
	}
	
	/**
	 * 用户的通知的时间的key值
	 * @param customerId
	 * @return
	 */
	public static String getNotifyTimeKey(String customerId)
	{
		return CUSTOMER_NOTIFY_TIME + ":" + customerId;
	}
	
	/**
	 * 渠道通知的key
	 * @param channelCode
	 * @return
	 */
	public static String getChannelNotifyUrl(String channelCode)
	{
		return CHANNEL_NOTIFY_URL + ":" + channelCode;
	}
	
	/**
	 * 用户的通知的内容的key值
	 * @param customerId
	 * @return
	 */
	public static String getNotifyContentKey(String customerId)
	{
		return CUSTOMER_NOTIFY_CONTENT + ":" + customerId;
	}
	
	/**
	 * 获取系统的账户的key值
	 * @param accountId
	 * @return
	 */
	public static String getSystemBalanceKey(String accountId)
	{
		return SYSTEM_BALANCE + ":" + accountId;
	}
	
	/**
	 * 根据orderid获得订单的类型
	 * @return
	 */
	public static String getOrderType(String orderId)
	{
		return ORDER_TYPE + ":" + orderId;
	}
	
	/**
	 * 获得游戏中各渠道的实现交易额的键值
	 * @param gameCode
	 * @param termCode
	 * @param channelCode
	 * @return
	 */
	public static String getGameTermChannelSale(String gameCode, String termCode, String channelCode)
	{
		return GAME_TERM_CHANNEL_SALE + ":" + gameCode + ":" + termCode + ":" + channelCode;
	}
	
	/**
	 * 获得游戏中期次实现交易额的键值
	 * @param gameCode
	 * @param termCode
	 * @param channelCode
	 * @return
	 */
	public static String getGameTermSale(String gameCode, String termCode)
	{
		return GAME_TERM_SALE + ":" + gameCode + ":" + termCode;
	}
	
	/**
	 * 获得游戏中期次实现交易额的键值
	 * @param gameCode
	 * @param termCode
	 * @param channelCode
	 * @return
	 */
	public static String getGameSale(String gameCode)
	{
		return GAME_SALE + ":" + gameCode;
	}
	
	/**
	 * 算奖过程中，需要处理的方案总数目
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getTermProgressSchemeDrawAll(String gameCode, String termCode)
	{
		return TERM_PROGRESS_SCHEME + ":DRAW:ALL:" + gameCode + ":" + termCode;
	}
	
	/**
	 * 算奖过程中，已经处理的方案总数目
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getTermProgressSchemeDrawFinished(String gameCode, String termCode)
	{
		return TERM_PROGRESS_SCHEME + ":DRAW:FINISHED:" + gameCode + ":" + termCode;
	}
	
	/**
	 * 用户的通知的key值
	 * @param customerId
	 * @return
	 */
	public static String getCustomerNotifyValue(String orderId, long totalAmount)
	{
		return "订单(方案)<br/><font color=\"red\">" + orderId + "</font><br/>支付(" + totalAmount/100 + "元)成功";
	}
	
	/**
	 * 手机验证码
	 * @param phone
	 * @return
	 */
	public static String getPhoneAuthCode(String phone)
	{
		return PHONE_AUTHCODE + ":" + phone;
	}
	
	/**
	 * 获取游戏出票成功的票的队列的key
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getGameTermTicketList(String gameCode, String termCode, String channelCode)
	{
		return GAME_TERM_TICKET_LIST + ":" + gameCode + ":" + termCode + ":" + channelCode;
	}
	
	/**
	 * 获取竞彩游戏出票成功的票的队列的key，竞彩票据信息不分期次
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getJcGameTermTicketList(String gameCode, String channelCode)
	{
		return GAME_TERM_TICKET_LIST + ":" + gameCode + ":" + channelCode;
	}
	
	/**
	 * 获取游戏出票成功的票的队列的key
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getGameTermOrderList(String gameCode, String termCode, String channelCode)
	{
		return GAME_TERM_ORDER_LIST + ":" + gameCode + ":" + termCode + ":" + channelCode;
	}
	
	/**
	 * 获取游戏出票成功的票的队列的key，中大奖
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getGameTermOrderBigHitList(String gameCode, String termCode, String channelCode)
	{
		return GAME_TERM_ORDER_BIG_HIT_LIST + ":" + gameCode + ":" + termCode + ":" + channelCode;
	}
	
	/**
	 * 获取游戏出票成功的票的队列的key，中小奖
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getGameTermOrderLittleHitList(String gameCode, String termCode, String channelCode)
	{
		return GAME_TERM_ORDER_LITTLE_HIT_LIST + ":" + gameCode + ":" + termCode + ":" + channelCode;
	}
	
	
	/**
	 * 获取游戏出票成功的票的队列的key，未中奖
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getGameTermOrderNotHitList(String gameCode, String termCode, String channelCode)
	{
		return GAME_TERM_ORDER_NOT_HIT_LIST + ":" + gameCode + ":" + termCode + ":" + channelCode;
	}
	
	/**
	 * 获取游戏方案的队列的key
	 * @param gameCode
	 * @param termCode
	 * @return
	 */
	public static String getGameTermSchemeList(String gameCode, String termCode)
	{
		return GAME_TERM_SCHEME_LIST + ":" + gameCode + ":" + termCode;
	}
	
	/**
	 * 竞彩赔率信息键值
	 * @param gameCode
	 * @param matchCode
	 * @param oddsCode
	 * @param playTypeCode
	 * @return
	 */
	public static String getJcOddsInfo(String gameCode, String matchCode, String oddsCode, String playTypeCode)
	{
		return JC_ODDS + ":" + gameCode + ":" + matchCode + ":" + oddsCode + ":" + playTypeCode;
	}
	
	/**
	 * 竞彩在售期次列表
	 * @param gameCode
	 * @return
	 */
	public static String getJcTermOnSaleQueen(String gameCode)
	{
		return JC_TERM_ON_SALE_QUEEN + ":" + gameCode;
	}
	
	/**
	 * 竞彩游戏的算奖标志
	 * @param gameCode
	 * @return
	 */
	public static String getJcCheckFlag(String gameCode)
	{
		return JC_CHECK_FLAG + ":" + gameCode;
	}
}
