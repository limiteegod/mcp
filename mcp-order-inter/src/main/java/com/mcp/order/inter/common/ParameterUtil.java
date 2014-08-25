/**
 * 参数化用的工具类。
 */
package com.mcp.order.inter.common;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.MD5Util;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.CmdGroup;
import com.mcp.order.inter.RepBody;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.util.DateTimeUtil;

import java.util.Date;


/**
 * @author ming.li
 *
 */
public class ParameterUtil {
	
	public static String getHead(String channelCode, String cmd)
	{
		String head = "{\"ver\":\"" + Constants.GATEWAY_VERSION + "\",\"id\":\"" + CoreUtil.getUUID() + "\",\"timestamp\":\"" + DateTimeUtil.getDateString(new Date()) + "\",\"channelCode\":\"" + channelCode + "\", \"cmd\":\"" + cmd + "\"}";
		return head;
	}
	
	public static String getHead(String channelCode, String cmd, String time, String body, String secretKey)
	{
		String md5 = MD5Util.MD5(body + time + secretKey);
		String head = "{\"ver\":\"" + Constants.GATEWAY_VERSION + "\",\"id\":\"" + CoreUtil.getUUID() + "\",\"timestamp\":\"" + time + "\",\"channelCode\":\"" + channelCode + "\", \"cmd\":\"" + cmd + "\", \"digest\":\"" + md5 + "\", \"digestType\":\"md5\"}";
		return head;
	}
	
	/**
	 * 获得请求参数类所在的包
	 * @param cmd
	 * @return
	 */
	public static String getReqPkg(String cmd)
	{
		String code = cmd.substring(0, 1);
		CmdGroup cg = CmdContext.getInstance().getCmdGroupByCode(code);
		return cg.getPkg() + cg.getReq();
	}

    /**
     * 获得请求参数类所在的包
     * @param groupCode
     * @return
     */
    public static String getReqPkgByGroupCode(String groupCode)
    {
        CmdGroup cg = CmdContext.getInstance().getCmdGroupByCode(groupCode);
        return cg.getPkg() + cg.getReq();
    }
	
	/**
	 * 根据命令获取类名
	 * @return
	 */
	public static String getReqBodyClassNameByCmd(String cmd)
	{
		return "Req" + cmd + "Body";
	}
	
	/**
	 * 根据命令获取类名
	 * @return
	 */
	public static String getRepBodyClassNameByCmd(String cmd)
	{
		return "Rep" + cmd + "Body";
	}
	
	/**
	 * 获得请求的body
	 * @param cmd
	 * @return
	 */
	public static ReqBody getReqBody(String cmd)
	{
		String pkg = getReqPkg(cmd);
		try {
			ReqBody req = (ReqBody)Class.forName(pkg + "." + getReqBodyClassNameByCmd(cmd)).newInstance();
			return req;
		} catch (Exception e) {
			throw new RuntimeException("init failed");
		}
	}
	
	/**
	 * 获得请求的body
	 * @param cmd
	 * @return
	 */
	public static RepBody getRepBody(String cmd)
	{
		String pkg = getRepPkg(cmd);
		try {
			RepBody rep = (RepBody)Class.forName(pkg + "." + getRepBodyClassNameByCmd(cmd)).newInstance();
			return rep;
		} catch (Exception e) {
			throw new RuntimeException("init failed");
		}
	}
	
	/**
	 * 获得回复参数类所在的包
	 * @param cmd
	 * @return
	 */
	public static String getRepPkg(String cmd)
	{
		String code = cmd.substring(0, 1);
		CmdGroup cg = CmdContext.getInstance().getCmdGroupByCode(code);
		return cg.getPkg() + cg.getRep();
	}
}
