package com.mcp.order.common;

/**
 * User: yeeson he
 * Date: 13-7-16
 * Time: 下午4:46
 * To be the best of me!
 */
public class Constants {

    /**
     * 一个小时的登录过期时间
     */
    public static final long LOGIN_EXPIRE_MILISECOND = 60*60*1000;

    //    全局的内部final常量。
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";   //系统中所用的时间的格式
    
    /**
     * 支付宝的通知的时间格式
     */
    public static final String ALIPAY_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";   //系统中所用的时间的格式

    public static final String SUCCESS = "0000";   //系统中所用的时间的格式

    public static final int USER_TYPE_CUSTOMER = 0;  // 用户类型：彩民
    public static final int USER_TYPE_STATION = 1;  //用户类型：投注站
    public static final int USER_TYPE_COMPANY = 2; //用户类型：公司
    public static final int USER_TYPE_INFO = 3;//用户类型：信息同步源
    public static final int USER_TYPE_CHANNEL = 4; //用户类型：渠道商

    public static final int CMD_Q01_TYPE_0 = 0;//期次状态查询类型：常规状态查询
    public static final int CMD_Q01_TYPE_1 = 1;//期次状态查询类型：获取所有可用游戏当前在售的期次信息
    public static final int CMD_Q01_TYPE_2 = 2;//期次状态查询类型：获取所有可用游戏最后一期已经开奖的期次信息
    public static final int CMD_Q01_TYPE_3 = 3;//期次状态查询类型：进行特定彩种的历史期次分页查询
    public static final int CMD_Q01_TYPE_4 = 4;//期次状态查询类型：获取指定游戏当前在售的期次信息
    
    public static final int TICKET_PRINT_RECEIPT_SUCCESS = 0;//票打印回执状态定义：打印成功。
    public static final int TICKET_PRINT_RECEIPT_FAILURE = 1;//票打印回执状态定义：打印失败。
    public static final int TICKET_PRINT_RECEIPT_RETURN = 2;//票打印回执状态定义：原票返回。
    
    /**
     * 网关接口版本。
     */
    public static final String GATEWAY_VERSION = "s.1.01";

    public static final String GATEWAY_DIGEST_TYPE_MD5 = "md5"; //网关加密摘要方式。

    public static final String GATEWAY_DIGEST_TYPE_DES = "des"; //网关加密摘要方式。

    public static final String GATEWAY_DIGEST_TYPE_3DES = "3des"; //网关加密摘要方式。

    /**
     * 站点操作定义，登录
     */
    public static final int STATION_OP_LOGIN = 0;

    /**
     * 站点操作定义，登出
     */
    public static final int STATION_OP_LOGOUT = 1;

    /**
     * 系统分隔符:（冒号）
     */
    public static final String SYSTEM_SEP_COLON = ":";

    /**
     * 一等奖
     */
    public static final int GRADE_LEVEL_FIRST = 1;

    /**
     * 二等奖
     */
    public static final int GRADE_LEVEL_SECOND = 2;

    /**
     * 三等奖
     */
    public static final int GRADE_LEVEL_THIRD = 3;

    /**
     * 四等奖
     */
    public static final int GRADE_LEVEL_FOURTH = 4;

    /**
     * 五等奖
     */
    public static final int GRADE_LEVEL_FIFTH = 5;

    /**
     * 六等奖
     */
    public static final int GRADE_LEVEL_SIXTH = 6;

    /**
     * 七等奖
     */
    public static final int GRADE_LEVEL_SEVENTH = 7;

    /**
     * 八等奖
     */
    public static final int GRADE_LEVEL_EIGHTH = 8;
    
    /**
     * 九等奖
     */
    public static final int GRADE_LEVEL_NINTH = 9;
    
    /**
     * 十等奖
     */
    public static final int GRADE_LEVEL_TENTH = 10;
    
    /**
     * 十一等奖
     */
    public static final int GRADE_LEVEL_ELEVENTH = 11;
    
    /**
     * 十二等奖
     */
    public static final int GRADE_LEVEL_TWELFTH = 12;
    
    /**
     * 十三等奖
     */
    public static final int GRADE_LEVEL_THIRTEENTH = 13;
    
    
    /**
     * 十四等奖
     */
    public static final int GRADE_LEVEL_FOURTEENTH = 14;
    
    
    /**
     * 十五等奖
     */
    public static final int GRADE_LEVEL_FIFTEENTH = 15;

    /**
     * 十六等奖
     */
    public static final int GRADE_LEVEL_SIXTEENTH = 16;

    /**
     * 十七等奖
     */
    public static final int GRADE_LEVEL_SEVENTEENTH = 17;

    /**
     * 十八等奖
     */
    public static final int GRADE_LEVEL_EIGHTEENTH = 18;

    /**
     * 十九等奖
     */
    public static final int GRADE_LEVEL_NINETEENTH = 19;

    /**
     * 二十等奖
     */
    public static final int GRADE_LEVEL_TWENTIETH = 20;

    /**
     * 二十一等奖
     */
    public static final int GRADE_LEVEL_TWENTY_FIRST = 21;

    /**
     * 用户通知的最大长度
     */
    public static final int CUSTOMER_NOTIFY_MAX_LENGTH = 150;

    /**
     * 自动返奖的分界线，>=则不自动返奖
     */
    public static final long DEFAULT_BIGBONUS_BORDER = 10000000;

    
    /**
     * Spring Batch批处理的数据文件的分隔符
     */
    public static final String SEP_DES = "~";

    /**
     *
     */
    public static final String SEP_STAR = "*";
    
    /**
     * 当条目中有冲突项时，batch使用的分割符
     */
    public static final String SEP_SMILE = "^";
    
    /**
     * Spring Batch批处理的数据文件的分隔符
     */
    public static final String SEP_COMMA = ",";
    
    
    public static final String JC_PLAYTYPE_HUNTOU = "06";

    /**
     * 交税界限，>=1000000
     */
    public static final long TAX_SEP = 1000000;

    /**
     * 交税比例，2000表示20%
     */
    public static final int TAX_PERCENTAGE = 2000;
}
