package com.mcp.order.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * User：邓玉明 Date：Nov 25, 2010 Time：11:13:27 AM 0开头是系统错误 1开头是账户错误 2开头是投注 3开头是查询
 */

public class ErrCode {
	private static Map<String, String> map = new HashMap<String, String>();
	private static Map<String, String> alarm = new HashMap<String, String>();
	private static Map<String, Integer> level = new HashMap<String, Integer>();

	/**
	 * 成功
	 */
	public static final String E0000 = "0000";
	/**
	 * 校验签名失败
	 */
	public static final String E0001 = "0001";
	/**
	 * schema验证失败
	 */
	public static final String E0002 = "0002";
	/**
	 * 参数错误
	 */
	public static final String E0003 = "0003"; //fadsfa
	
	/**
	 * 失败
	 */
	public static final String E0999 = "0999";

	/**
	 * 账户未登录
	 */
	public static final String E1001 = "1001";
	/**
	 * 余额不足
	 */
	public static final String E1002 = "1002";
	/**
	 * 账户不存在
	 */
	public static final String E1003 = "1003";
	/**
	 * 账户密码错误
	 */
	public static final String E1004 = "1004";
	/**
	 * 账户信息已存在
	 */
	public static final String E1005 = "1005";

	/**
	 * 电话信息有重复
	 */
	public static final String E1017 = "1017";

	/**
	 * 邮箱信息有重复
	 */
	public static final String E1018 = "1018";

	/**
	 * 身份证号有重复
	 */
	public static final String E1019 = "1019";
	
	/**
	 * 不支持的资金周转方式
	 */
	public static final String E1021 = "1021";
	
	/**
	 * 资金周转失败
	 */
	public static final String E1022 = "1022";

	/**
	 * 密码和用户信息不符
	 */
	public static final String E1006 = "1006";
	/**
	 * 账户余额不足
	 */
	public static final String E1007 = "1007";
	/**
	 * 用户未激活
	 */
	public static final String E1008 = "1008";
	/**
	 * 认证记录不存在
	 */
	public static final String E1009 = "1009";
	/**
	 * 认证信息已使用
	 */
	public static final String E1010 = "1010";
	/**
	 * 认证信息超过30分钟
	 */
	public static final String E1011 = "1011";
	/**
	 * 认证信息不正确
	 */
	public static final String E1012 = "1012";
	/**
	 * 用户未登录
	 */
	public static final String E1013 = "1013";
	/**
	 * 用户被锁定投注
	 */
	public static final String E1014 = "1014";
	/**
	 * 扣款失败
	 */
	public static final String E1015 = "1015";

	/**
	 * 原密码错误
	 */
	public static final String E1016 = "1016";
	
	/**
	 * 系统版本不一致
	 */
	public static final String E1023 = "1023";
	
	/**
	 * 权限不够
	 */
	public static final String E1024 = "1024";
	
	/**
	 * 文件操作失败
	 */
	public static final String E1025 = "1025";
	
	/**
	 * 不支持的方案类型
	 */
	public static final String E1026 = "1026";
	
	/**
	 * 不支持的支付方式
	 */
	public static final String E1027 = "1027";
	
	/**
	 * 未绑定银行卡
	 */
	public static final String E1028 = "1028";
	
	/**
	 * 订单已经失效
	 */
	public static final String E1029 = "1029";
	
	/**
	 * 金额不能小于1
	 */
	public static final String E2001 = "2001";
	/**
	 * 期次已截期
	 */
	public static final String E2002 = "2002";
	/**
	 * 期次不存在
	 */
	public static final String E2003 = "2003";
	/**
	 * 期次不是预约期
	 */
	public static final String E2004 = "2004";
	/**
	 * 订单不存在
	 */
	public static final String E2005 = "2005";
	/**
	 * 格式不正确
	 */
	public static final String E2006 = "2006";
	/**
	 * 彩种不存在
	 */
	public static final String E2007 = "2007";
	/**
	 * 不是当前期
	 */
	public static final String E2008 = "2008";
	/**
	 * 无订单
	 */
	public static final String E2009 = "2009";
	/**
	 * 暂无期次
	 */
	public static final String E2010 = "2010";
	/**
	 * 期次过期
	 */
	public static final String E2011 = "2011";
	/**
	 * 方案已满
	 */
	public static final String E2012 = "2012";
	/**
	 * 撤单失败
	 */
	public static final String E2013 = "2013";
	/**
	 * 撤资失败
	 */
	public static final String E2014 = "2014";
	/**
	 * 无法撤资和撤单
	 */
	public static final String E2015 = "2015";
	/**
	 * 已经是撤资状态
	 */
	public static final String E2016 = "2016";
	/**
	 * 方案号重复
	 */
	public static final String E2017 = "2017";
	/**
	 * 方案不存在
	 */
	public static final String E2018 = "2018";
	/**
	 * 认购份数超过剩余份数
	 */
	public static final String E2019 = "2019";
	/**
	 * 认购份数不能小于等于0
	 */
	public static final String E2020 = "2020";
	/**
	 * 方案金额不能大于设定的金额上限
	 */
	public static final String E2021 = "2021";
	/**
	 * 方案金额不能大于设定的金额下限
	 */
	public static final String E2022 = "2022";
	/**
	 * 方案号码已上传
	 */
	public static final String E2023 = "2023";
	/**
	 * 合作商不存在
	 */
	public static final String E2024 = "2024";
	/**
	 * 实际金额与传入金额不符
	 */
	public static final String E2025 = "2025";

	/**
	 * 未选择投注号码
	 */
	public static final String E2026 = "2026";

	/**
	 * 未选择投注期次
	 */
	public static final String E2027 = "2027";

	/**
	 * 购买方式不正确
	 */
	public static final String E2028 = "2028";

	/**
	 * 暂停销售
	 */
	public static final String E2029 = "2029";
	
	/**
	 * 游戏不可用
	 */
	public static final String E2030 = "2030";
	
	/**
	 * 不支持的玩法
	 */
	public static final String E2031 = "2031";
	
	/**
	 * 不支持的投注方式
	 */
	public static final String E2032 = "2032";
	
	/**
	 * 号码格式不正确
	 */
	public static final String E2033 = "2033";
	
	/**
	 * 渠道不可用
	 */
	public static final String E2034 = "2034";
	
	/**
	 * 投注站不存在
	 */
	public static final String E2035 = "2035";
	
	/**
	 * 目标投注站不支持此游戏
	 */
	public static final String E2036 = "2036";
	
	/**
	 * 目标投注站该期次停售
	 */
	public static final String E2037 = "2037";
	
	/**
	 * 投注站繁忙
	 */
	public static final String E2038 = "2038";
	
	/**
	 * 终端机未注册
	 */
	public static final String E2039 = "2039";
	
	/**
	 * 回执失败，此票已经成功打印过
	 */
	public static final String E2040 = "2040";
	
	/**
	 * 预购期次超过系统预售期次
	 */
	public static final String E2041 = "2041";
	
	/**
	 * 订单未处于等待支付状态
	 */
	public static final String E2042 = "2042";
	
	/**
	 * 非法的资金操作
	 */
	public static final String E2043 = "2043";
	
	/**
	 * 充值失败
	 */
	public static final String E2044 = "2044";
	
	/**
	 * 渠道未与投注站建立关系
	 */
	public static final String E2045 = "2045";
	
	/**
	 * 玩法已经停售
	 */
	public static final String E2046 = "2046";
	
	/**
	 * 未绑定投注站
	 */
	public static final String E2047 = "2047";
	
	/**
	 * 追号必须大于2期
	 */
	public static final String E2048 = "2048";
	
	/**
	 * 倍数必须大于0
	 */
	public static final String E2049 = "2049";
	
	/**
	 * 期次号重复
	 */
	public static final String E2050 = "2050";
	
	/**
	 * 倍数信息不一致
	 */
	public static final String E2051 = "2051";

    /**
     * 缺少外部订单号
     */
    public static final String E2052 = "2052";

    /**
     * 竞彩必须有赔率
     */
    public static final String E2053 = "2053";

    /**
     * 无效的操作
     */
    public static final String E2054 = "2054";

    /**
     * 事务id出错
     */
    public static final String E2055 = "2055";

    /**
     * 记录不存在
     */
    public static final String E2056 = "2056";

    /**
     * 处于不允许的状态
     */
    public static final String E2057 = "2057";

    /**
     * JSON格式转换出错
     */
    public static final String E2058 = "2058";

	/**
	 * 投注失败
	 */
	public static final String E2999 = "2999";

	/**
	 * 历史开奖不存在
	 */
	public static final String E1301 = "1301";

	/**
	 * 记录已存在
	 */
	public static final String E1302 = "1302";

	/**
	 * 提款金额最小为50元!
	 */
	public static final String E1020 = "1020";
	
	/**
	 * 不支持的系统服务
	 */
	public static final String E0004 = "0004";

	static {
		init();
	}

	public static void init() {
		map.put(E0000, "系统处理成功");
		map.put(E0001, "校验签名失败");
		map.put(E0002, "schema验证失败");
		map.put(E0003, "参数错误");
		map.put(E0004, "不支持的系统服务");
		
		map.put(E1001, "账户未登录");
		map.put(E1002, "余额不足");
		map.put(E1003, "账户不存在");
		map.put(E1004, "账户密码错误");
		map.put(E1005, "账户信息已存在");
		map.put(E1006, "旧密码有误");
		map.put(E1007, "账户余额不足");
		map.put(E1008, "用户未激活");
		map.put(E1009, "认证记录不存在");
		map.put(E1010, "认证信息已使用");
		map.put(E1011, "认证信息超过30分钟");
		map.put(E1012, "认证信息不正确");
		map.put(E1013, "用户未登录");
		map.put(E1014, "用户被锁定投注");
		map.put(E1015, "扣款失败");
		map.put(E1017, "电话信息有重复");
		map.put(E1018, "邮箱信息有重复");
		map.put(E1019, "身份证号有重复");
		map.put(E1020, "提款金额最小为50元!");
		map.put(E1021, "不支持的资金周转方式");
		map.put(E1022, "资金周转失败");
		map.put(E1023, "系统版本不一致");
		map.put(E1024, "权限不够");
		map.put(E1025, "文件操作失败");
		map.put(E1026, "不支持的方案类型");
		map.put(E1027, "不支持的支付方式");
		map.put(E1028, "未绑定银行卡");
		map.put(E1029, "订单已经失效");
		
		map.put(E1301, "历史开奖不存在");
		map.put(E1302, "记录已存在");
		
		map.put(E2001, "金额不能为负");
		map.put(E2002, "期次已截期");
		map.put(E2003, "期次不存在");
		map.put(E2004, "不是预约期");
		map.put(E2005, "订单不存在");
		map.put(E2006, "格式不正确");
		map.put(E2007, "彩种不存在");
		map.put(E2008, "不是当前期");
		map.put(E2999, "投注失败");
		map.put(E2009, "无订单");
		map.put(E2010, "暂无期次");
		map.put(E2011, "期次过期");
		map.put(E2012, "方案已满");
		map.put(E2013, "撤单失败");
		map.put(E2014, "撤资失败");
		map.put(E2015, "无法撤资或撤单");
		map.put(E2016, "已经是撤资状态");
		map.put(E2017, "方案号重复");
		map.put(E2018, "方案不存在");
		map.put(E2019, "认购份数超过剩余份数");
		map.put(E2020, "认购份数不能小于等于0");
		map.put(E2021, "方案金额不能大于设定的金额上限");
		map.put(E2022, "方案金额不能大于设定的金额下限");
		map.put(E2023, "方案号码已上传");
		map.put(E2024, "合作商不存在");
		map.put(E2025, "实际金额与传入金额不符");
		map.put(E2026, "未选择投注号码");
		map.put(E2027, "未选择投注期次");
		map.put(E2028, "购买方式不正确");
		map.put(E2029, "暂停销售");
		map.put(E2030, "游戏不可用");
		map.put(E2031, "不支持的玩法");
		map.put(E2032, "不支持的投注方式");
		map.put(E2033, "号码格式不正确");
		map.put(E2034, "渠道不可用");
		map.put(E2035, "投注站不存在");
		map.put(E2036, "目标投注站不支持此游戏");
		map.put(E2037, "目标投注站该期次停售");
		map.put(E2038, "投注站繁忙");
		map.put(E2039, "终端机未注册");
		map.put(E2040, "回执失败，此票已经成功打印过");
		map.put(E2041, "预购期次超过系统预售期次");
		map.put(E2042, "订单未处于等待支付状态");
		map.put(E2043, "非法的资金操作");
		map.put(E2044, "充值失败");
		map.put(E2045, "渠道未与投注站建立关系");
		map.put(E2046, "玩法已经停售");
		map.put(E2047, "未绑定投注站");
		map.put(E2048, "追号必须大于2期");
		map.put(E2049, "倍数必须大于0");
		map.put(E2050, "期次号重复");
		map.put(E2051, "倍数信息不一致");
        map.put(E2052, "缺少外部订单号");
        map.put(E2053, "竞彩必须有赔率");
        map.put(E2054, "无效的操作");
        map.put(E2055, "事务id出错");
        map.put(E2056, "记录不存在");
        map.put(E2057, "处于不允许的状态");
        map.put(E2058, "JSON格式转换出错");

		map.put(E0999, "系统未知异常");

		alarm.put("", "");

		level.put("", 0);

	}

	public static String codeToMsg(String code) {
		if (map.containsKey(code)) {
			return map.get(code);
		}
		throw new RuntimeException(E0999);
	}

	public static String codeToAlarm(String code) {
		if (alarm.containsKey(code)) {
			return alarm.get(code);
		}
		return null;
	}

	public static Integer codeToLevel(String code) {
		if (level.containsKey(code)) {
			return level.get(code);
		}
		return -1;
	}
}
