package com.mcp.order.inter.util;


import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.MD5Util;
import com.mcp.core.util.cons.SystemUserType;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.util.DateTimeUtil;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Date;

public class InterUtil {

    /**
     * 获得消息的头部
     * @param userId
     * @param userType
     * @param channelCode
     * @param cmd
     * @return
     */
    public static Head getHead(String userId, int userType, String channelCode, String cmd, String digestType) {
        Head head = new Head();
        head.setId(CoreUtil.getUUID());
        head.setUserId(userId);
        head.setUserType(userType);
        head.setChannelCode(channelCode);
        head.setCmd(cmd);
        head.setVer(Constants.GATEWAY_VERSION);
        Date now = new Date();
        String dateStr = DateTimeUtil.getDateString(now);
        head.setTimestamp(dateStr);
        head.setDigestType(digestType);
        return head;
    }

    /**
     * 加密成消息
     * @param head
     * @param body
     * @param key
     * @return
     */
    public static McpGtMsg getGtMsg(Head head, String body, String key)
    {
        McpGtMsg mcpGtMsg = new McpGtMsg();
        String secretBody = null;
        if(head.getUserType() == SystemUserType.GUEST.getCode())
        {
            secretBody = body;
        }
        else
        {
            secretBody = DigestFactory.generate(head, body, key);
        }
        mcpGtMsg.setHead(head);
        mcpGtMsg.setBody(secretBody);
        return mcpGtMsg;
    }

    /**
     * 加密成字符串消息
     * @param head
     * @param body
     * @param key
     * @return
     */
    public static String getMsg(Head head, String body, String key) throws CoreException
    {
        McpGtMsg mcpGtMsg = getGtMsg(head, body, key);
        ObjectMapper om = new ObjectMapper();
        String msg = null;
        try {
            msg = om.writeValueAsString(mcpGtMsg);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CoreException(ErrCode.E0003);
        }
        return msg;
    }
	
	/**
	 * 获得请求的消息
	 * @param userId
	 * @param channelCode
	 * @param body
	 * @param cmd
	 * @param dateStr
	 * @param digest
	 * @param digestType
	 * @return
	 * @throws Exception
	 */
	public static String getCReqMessage(String userId, String channelCode, String body, String cmd,
			String dateStr, String digest, String digestType) throws Exception
	{
		return getCReqMessage(CoreUtil.getUUID(), userId, channelCode, body, cmd, dateStr, digest, digestType);
	}

    /**
     * 获得请求的消息
     * @param hId
     * @param userId
     * @param channelCode
     * @param body
     * @param cmd
     * @param dateStr
     * @param digest
     * @param digestType
     * @return
     * @throws Exception
     */
    public static String getCReqMessage(String hId, String userId, String channelCode, String body, String cmd,
                                        String dateStr, String digest, String digestType) throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        Head h = new Head();
        h.setId(hId);
        h.setChannelCode(channelCode);
        h.setCmd(cmd);
        h.setTimestamp(dateStr);
        h.setVer(Constants.GATEWAY_VERSION);
        h.setDigest(digest);
        h.setDigestType(digestType);
        McpGtMsg req = new McpGtMsg();
        req.setHead(h);
        req.setBody(body);
        String message = om.writeValueAsString(req);
        return message;
    }
	
	/**
	 * 获得使用MD5加密的信息
	 * @param userId
	 * @param channelCode
	 * @param body
	 * @param cmd
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getMd5Message(String userId, String channelCode, String body, String cmd,
			String key) throws Exception
	{
		return getMd5Message(CoreUtil.getUUID(), userId, channelCode, body, cmd, key);
	}

    /**
     * 获得使用MD5加密的信息
     * @param userId
     * @param channelCode
     * @param body
     * @param cmd
     * @param key
     * @return
     * @throws Exception
     */
    public static String getMd5Message(String hid, String userId, String channelCode, String body, String cmd,
                                       String key) throws Exception
    {
        Date now = new Date();
        String dateStr = DateTimeUtil.getDateString(now);
        String digestType = Constants.GATEWAY_DIGEST_TYPE_MD5;
        String digest = MD5Util.MD5(body + DateTimeUtil.getDateString(now) + key);
        return getCReqMessage(hid, userId, channelCode, body, cmd,
                dateStr, digest, digestType);
    }
}
