/**
 * 
 */
package com.mcp.core.util;

import org.apache.commons.net.util.Base64;

import java.io.*;

public class SerializeTool {

	/**
	 * 对象转换成序列化之后的字符串
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String objectToString(Object obj) {
		String objBody = null;
		ByteArrayOutputStream baops = null;
		ObjectOutputStream oos = null;
		baops = new ByteArrayOutputStream();
		try {
			oos = new ObjectOutputStream(baops);
			oos.writeObject(obj);
			byte[] bytes = baops.toByteArray();
			objBody = Base64.encodeBase64String(bytes);
			oos.close();
			baops.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objBody;
	}

	/**
	 * 字符串转换成对象
	 * @param objBody
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T getObjectFromString(
			String objBody, Class<T> clazz)
	{
		if(StringUtil.isEmpty(objBody))
			return null;
		ObjectInputStream ois = null;
		T obj = null;
		try {
			byte[] bytes = Base64.decodeBase64(objBody);
			ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
			obj = (T)ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}