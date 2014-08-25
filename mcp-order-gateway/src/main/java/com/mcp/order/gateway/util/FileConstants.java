/**
 * 
 */
package com.mcp.order.gateway.util;

import java.io.File;

/**
 * @author ming.li
 *
 */
public class FileConstants {
	
	public static final String TICKET_PIC_FOLDER = "/home/issue/pic";
	
	public static final String PROMOTION_PIC_FOLDER = "/home/promotion/pic";
	
	/**
	 * 获取票的文件地址
	 * @param gameCode
	 * @param termCode
	 * @param ticketId
	 * @return
	 */
	public static String getTicketFilePath(String gameCode, String termCode, String ticketId)
	{
		String folder = TICKET_PIC_FOLDER + "/" + gameCode + "/" + termCode;
		File f = new File(folder);
		if(!f.exists())
		{
			f.mkdirs();
		}
		return folder + "/" + ticketId + ".png";
	}
}
