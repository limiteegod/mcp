package com.mcp.order.mongo.common;

public class MgConstants {
	
	//竞彩算奖使用的自增id
	public static final String MG_JC_CHECK_TICKET_ID = "jc_ticket_seq";

    //通知使用的自增id
    public static final String MG_NOTIFY_ID = "notify_seq";

    //出票系统使用的自增id
    public static final String MG_PRINT_ID = "print_seq";

    //记录用户登录信息
    public static final String MG_CUS_LOGIN = "cus_login";

    //记录用户登录信息
    public static final String MG_CUS_UNIQUEID = "cus_uniqueid";
	
	//出票成功的票据
	public static final String TERM_SUCCESS_TICKET = "term_success_ticket_";
	
	//算奖的票据
	public static final String TERM_DRAW_TICKET = "term_draw_ticket_";
	
	//中奖的票据
	public static final String TERM_HIT_TICKET = "term_hit_ticket_";
		
	//未中奖的票据
	public static final String TERM_NOT_HIT_TICKET = "term_not_hit_ticket_";
	
	//出票失败的票据
	public static final String TERM_FAILURE_TICKET = "term_failure_ticket_";
	
	//已退款的票据
	public static final String TERM_REFUNDED_TICKET = "term_refunded_ticket_";
	
	//已退款的订单
	public static final String TERM_REFUNDED_ORDER = "term_refunded_order_";
	
	//中大奖的订单
	public static final String TERM_BIG_HIT_ORDER = "term_big_hit_order_";
	
	//中小奖的订单
	public static final String TERM_LITTLE_HIT_ORDER = "term_little_hit_order_";
	
	//已经返奖的订单
	public static final String TERM_PRIZEd_ORDER = "term_prized_order_";

	//未中奖的订单
	public static final String TERM_NOT_HIT_ORDER = "term_not_hit_order_";
	
	//期次报表
	public static final String TERM_REPORT = "term_report";
	
	//渠道通知配置，所有的渠道
	public static final String NOTIFY_CHANNEL_ALL = "notify_channel_all";
	
	//通知队列
	public static final String NOTIFY_QUEEN = "notify_queen_";

    //出票队列
    public static final String PRINT_QUEEN = "print_queen_";
	
	//等待支付的订单或者方案
	public static final String WAIT_PAY = "wait_pay";
	
	//竞彩期次的赔率
	public static final String TERM_JODDS = "term_jodds";

    //期次封存信息
    public static final String TERM_SEAL_INFO = "term_seal_info";
}
