package com.mcp.order.common;/*
 * User: yeeson he
 * Date: 13-10-22
 * Time: 上午11:56
 */

import java.util.HashMap;
import java.util.Map;

public enum ConstantValues {
    Area_Level_Default(0, "未指定", "Area_Level") {
    },Area_Level_Nation(1, "国家", "Area_Level") {
    },Area_Level_Province(2, "省市", "Area_Level") {
    },Area_Level_City(3, "地市", "Area_Level") {
    },Area_Type_Default(0, "未指定", "Area_Type") {
    },Area_Type_TC(1, "体彩", "Area_Type") {
    },Area_Type_FC(2, "福彩", "Area_Type") {
    },Area_Status_Default(0, "未开展", "Area_Status") {
    },Area_Status_Discuss(1, "洽谈中", "Area_Status") {
    },Area_Status_Open(2, "开展中", "Area_Status") {
    },Area_Status_Stop(3, "业务停止", "Area_Status") {
    },AreaGames_Status_Opening(0, "可用", "AreaGames_Status") {
    },AreaGames_Status_Stop(1, "不可用", "AreaGames_Status") {
    },Channel_Type_Default(0, "未指定", "Channel_Type") {
    },Channel_Type_Self(1, "自有渠道", "Channel_Type") {
    },Channel_Type_Other(2, "外部渠道", "Channel_Type") {
    },Channel_Status_Open(0, "已开通", "Channel_Status") {
    },Channel_Status_Stop(1, "未开通", "Channel_Status") {
    },ChannelGame_Status_Open(0, "可用", "ChannelGame_Status") {
    },ChannelGame_Status_Stop(1, "不可用", "ChannelGame_Status") {
    },ClientFileVersion_Status_Open(0, "可用", "ClientFileVersion_Status") {
    },ClientFileVersion_Status_Stop(1, "不可用", "ClientFileVersion_Status") {
    },ClientVersion_ClientType_Android(0, "安卓客户端", "ClientVersion_ClientType") {
    },ClientVersion_ClientType_Ios(1, "苹果客户端", "ClientVersion_ClientType") {
    },ClientVersion_ClientType_Html5(2, "手机网站", "ClientVersion_ClientType") {
    },ClientVersion_ClientType_Web(3, "网站", "ClientVersion_ClientType") {
    },ClientVersion_ClientType_Others(4, "其它", "ClientVersion_ClientType") {
    },ClientVersion_Status_Open(0, "可用", "ClientVersion_Status") {
    },ClientVersion_Status_Stop(1, "不可用", "ClientVersion_Status") {
    },Conservator_Status_Open(0, "可用", "Conservator_Status") {
    },Conservator_Status_Stop(1, "不可用", "Conservator_Status") {
    },CPlatform_Status_Open(0, "可用", "CPlatform_Status") {
    },CPlatform_Status_Stop(1, "不可用", "CPlatform_Status") {
    },Manager_Status_Open(0, "可用", "Manager_Status") {
    },Manager_Status_Stop(1, "不可用", "Manager_Status") {
    },ManagerRole_Status_Open(0, "可用", "ManagerRole_Status") {
    },ManagerRole_Status_Stop(1, "不可用", "ManagerRole_Status") {
    },ManagerRolePrivilege_Status_Open(0, "可用", "ManagerRolePrivilege_Status") {
    },ManagerRolePrivilege_Status_Stop(1, "不可用", "ManagerRolePrivilege_Status") {
    },Station_StationType_Common(0, "普通投注站", "Station_StationType"){
    },Station_StationType_Center(1, "出票中心", "Station_StationType"){
    },Station_StationType_CHANNEL(2, "销售渠道", "Station_StationType"){
    },Station_StationType_DEFAULT(3, "自有渠道", "Station_StationType"){
    },Station_StationType_SYSTEM(4, "外部系统", "Station_StationType"){
    },Station_Status_Open(0, "可用", "Station_Status"){
    },Station_Status_Stop(1, "不可用", "Station_Status"){
    },StationGame_Status_Open(0, "业务进行中", "StationGame_Status"){
    },StationGame_Status_Stop(1, "业务停止", "StationGame_Status"){
    },Terminal_Status_Open(0, "业务进行中", "Terminal_Status"){
    },Terminal_Status_Stop(1, "业务停止", "Terminal_Status"){
    },TerminalVersion_Type_Default(0, "未指定", "TerminalVersion_Type"){
    },TerminalVersion_Type_FC(1, "福彩终端", "TerminalVersion_Type"){
    },TerminalVersion_Type_TC(2, "体彩终端", "TerminalVersion_Type"){
    },TerminalVersion_Status_Open(0, "可用", "TerminalVersion_Status"){
    },TerminalVersion_Status_Stop(1, "不可用", "TerminalVersion_Status"){
    },Customer_Status_Open(0, "可用", "Customer_Status"){
    },Customer_Status_Stop(1, "不可用", "Customer_Status"){
    },CustomerAccount_Status_Open(0, "可用", "CustomerAccount_Status"){
    },CustomerAccount_Status_Stop(1, "不可用", "CustomerAccount_Status"){
    },CustomerPresent_Status_Undue(0, "未到期", "CustomerPresent_Status"){
    },CustomerPresent_Status_Available(1, "可用使用", "CustomerPresent_Status"){
    },CustomerPresent_Status_Expired(2, "过期", "CustomerPresent_Status"){
    },CustomerPresent_Status_Used(3, "已经使用", "CustomerPresent_Status"){
    },CustomerPresent_PresentType_Cash(0, "现金劵", "CustomerPresent_PresentType"){
    },CustomerPresent_PresentType_Discount(1, "折扣券", "CustomerPresent_PresentType"){
    },CustomerPresent_ProviderType_Platform (0, "平台", "CustomerPresent_ProviderType"){
    },CustomerPresent_ProviderType_Station(1, "投注站", "CustomerPresent_ProviderType"){
    },Game_Status_Open(1, "可用", "Game_Status"){
    },Game_Status_Stop(0, "不可用", "Game_Status"){
    },Game_Type_Normal(1, "普通", "Game_Type"){
    },Game_Type_Gaopin(2, "高频", "Game_Type"){
    },Game_Type_Jingcai(3, "竞彩", "Game_Type"){
    },GameGrade_Status_Open(0, "可用", "GameGrade_Status"){
    },GameGrade_Status_Stop(1, "不可用", "GameGrade_Status"){
    },Payment_PayType_Pay(0, "支付", "Payment_PayType"){
    },Payment_PayType_Back(1, "返奖", "Payment_PayType"){
    },Payment_cardType_Bank(0, "银行", "Payment_cardType"){
    },Payment_cardType_Company(1, "第三方支付", "Payment_cardType"){
    },Payment_Status_Open(0, "可用", "Payment_Status"){
    },Payment_Status_Stop(1, "不可用", "Payment_Status"){
    },TOrder_PayType_Cash(1, "现金支付", "TOrder_PayType"){
    },TOrder_PayType_Company(0, "第三方支付", "TOrder_PayType"){
    },TOrder_Type_Customer(0, "彩民", "TOrder_Type"){
    },TOrder_Type_Channel(1, "渠道", "TOrder_Type"){
    },Payment_Type_NONE(-1, "无", "Payment_Type"){
    },Payment_Type_Alipay(1, "支付宝", "Payment_Type"){
    },Payment_Type_Bank(2, "银联", "Payment_Type"){
    },TScheme_Type_Default(1, "无方案", "TScheme_Type"){
    },TScheme_Type_Follow(2, "追号", "TScheme_Type"){
    },TScheme_Type_Dt306(3, "定投306", "TScheme_Type"){
    },TScheme_Type_HeMai(4, "合买", "TScheme_Type"){
    },TSchemeHm_Type_Normal(1, "标准", "TSchemeHm_Type"){
    },TSchemeHm_Type_Gift(2, "赠送", "TSchemeHm_Type"){
    },TSchemeHm_Type_Sys_Gift(3, "系统赠送", "TSchemeHm_Type"){
    },TScheme_WinStop_Continue(0, "中奖后继续", "TScheme_WinStop"){
    },TScheme_WinStop_Stop(1, "中奖后停止", "TScheme_WinStop"){
    },TTicket_Type_Both(0, "双票均有", "TTicket_Type"){
    },TTicket_Type_Electron(1, "只有电子票", "TTicket_Type"){
    },TTicket_Type_Real(2, "只有实体票", "TTicket_Type"){
    },BetType_Status_Stop(0, "不可用", "BetType_Status"){
    },BetType_Status_Open(1, "可用", "BetType_Status"){
    },PlayType_Status_Stop(0, "不可用", "PlayType_Status"){
    },PlayType_Status_Open(1, "可用", "PlayType_Status"){
    },SaleReport_Scope_Order(2, "订单", "SaleReport_Scope"){
    },SaleReport_Scope_Undefined(1, "未定义", "SaleReport_Scope"){
    },Jc_Odds_PlayType_01(1, "01", "Jc_Odds_PlayType"){
    },Jc_Odds_PlayType_02(2, "02", "Jc_Odds_PlayType"){
    },Jc_Odds_PlayType_03(3, "03", "Jc_Odds_PlayType"){
    },Jc_Odds_PlayType_04(4, "04", "Jc_Odds_PlayType"){
    },Jc_Odds_PlayType_05(5, "05", "Jc_Odds_PlayType"){
    },Jc_Odds_PlayType_11(11, "11", "Jc_Odds_PlayType"){
    },Jc_Odds_PlayType_12(12, "12", "Jc_Odds_PlayType"){
    },Jc_Odds_PlayType_13(13, "13", "Jc_Odds_PlayType"){
    },Jc_Odds_PlayType_14(14, "14", "Jc_Odds_PlayType"){
    },Jc_Odds_PlayType_15(15, "15", "Jc_Odds_PlayType"){
    },MoneyLog_Status_FINISHED(0, "已完成", "MoneyLog_Status"){
    },MoneyLog_Status_HANDLING(1, "处理中", "MoneyLog_Status"){ 	
    },Recharge_Channel_Hand(1, "手工充值", "Recharge_Channel"){ 	
    },Recharge_Channel_Prize(2, "奖金账户转入", "Recharge_Channel"){ 	
    },Recharge_Channel_Alipay(3, "支付宝充值", "Recharge_Channel"){ 	
    },Recharge_Channel_UnionPay(4, "银联充值", "Recharge_Channel"){ 	
    },Recharge_Channel_BOC(5, "中国银行直充", "Recharge_Channel"){ 	
    },Recharge_Channel_CCB(6, "建设银行直充", "Recharge_Channel"){
    },Recharge_Channel_CMBC(7, "民生银行直充", "Recharge_Channel"){ 
    },Recharge_Channel_CEB(8, "光大银行直充", "Recharge_Channel"){
    },Recharge_Channel_XB(9, "寻宝中奖", "Recharge_Channel"){
    },NOTIFY_TYPE_TERM(1, "期次通知", "NOTIFY_TYPE"){ 	
    },NOTIFY_TYPE_ORDER(2, "订单通知", "NOTIFY_TYPE"){ 	
    },NOTIFY_STATUS_UNUSED(1, "未处理", "NOTIFY_STATUS"){ 	
    },NOTIFY_STATUS_USED(2, "已经处理", "NOTIFY_STATUS"){
    },NOTIFY_CHANNEL_STATUS_UNUSED(-1, "停止", "NOTIFY_CHANNEL_STATUS"){
    },NOTIFY_CHANNEL_STATUS_WAITING(0, "等待运行", "NOTIFY_CHANNEL_STATUS"){
    },NOTIFY_CHANNEL_STATUS_RUNNING(1, "正在运行", "NOTIFY_CHANNEL_STATUS"){
    },COUPON_TYPE_CHANNEL(1, "渠道优惠卷", "COUPON_TYPE"){
    },COUPON_TYPE_PLATFORM(1, "平台优惠卷", "COUPON_TYPE"){
    },COUPON_STATUS_UNAVAILABLE(1, "未生效", "COUPON_STATUS"){
    },COUPON_STATUS_AVAILABLE(2, "已生效", "COUPON_STATUS"){
    },COUPON_STATUS_EXPIRED(3, "已过期", "COUPON_STATUS"){
    },COUPON_STATUS_USED(4, "已使用", "COUPON_STATUS"){
    };


    private int code;
    private String value;
    private String group;

    private ConstantValues(int code, String value, String group) {
        this.code = code;
        this.value = value;
        this.group = group;
    }

    private static Map<String, Map<Integer, ConstantValues>> valuesMap = new HashMap<String,Map<Integer, ConstantValues>>();

    /**
     * 之所以放到static代码块，是因为只需初始化一次
     */
    static {
    	for (ConstantValues s : ConstantValues.values()) {
            Map<Integer, ConstantValues> map;
            String group = s.getGroup();
            if (valuesMap.containsKey(group)) {
                map = valuesMap.get(group);
            } else {
                map = new HashMap<Integer, ConstantValues>();
                valuesMap.put(group, map);
            }
            map.put(s.getCode(), s);
        }
    }
    
    public static Map<String, Map<Integer, ConstantValues>> getValuesMap() {
        return valuesMap;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}