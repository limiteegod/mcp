/**
 * 
 */
package com.mcp.filter.util;

import com.mcp.filter.model.FilterStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ming.li
 *
 */
public class MessageUtil {
	
	/**
	 * 通过json字符串获得map对象
	 * @param json
	 * @return
	 */
	public static Map<String, String> getMapFromJsonString(String json)
	{
		Map<String, String> keyValueMap = new HashMap<String, String>();
		FilterStack fs = new FilterStack();
		char[] charArray = json.toCharArray();
		char lastChar = '\0';
		boolean flag = false;	//0-key,1-value,默认在key或者value的外部
		int count = 0;
		String key = null;
		StringBuffer sb = new StringBuffer();	//存储key or value
		for(int i = 0; i < charArray.length; i++)
		{
			char c = charArray[i];
			if(c == '{' || c == '[')
			{
				char h = fs.head();
				if(h == '\0')
				{
					fs.put(c);
					continue;
				}
				if(h != '\"')
				{
					if(fs.len() == 1)
					{
						flag = true;
					}
					fs.put(c);
				}
			}
			if(c == '}' || c == ']')
			{
				char h = fs.head();
				if(h != '\"')
				{
					fs.pop();
					if(fs.len() == 0)	//解析结束
					{
						break;
					}
					if(fs.len() == 1)	//head或者body的value中
					{
						flag = false;	//key or value已经结束啦
						if(count%2 == 0)
						{
							sb.append(c);
							key = sb.toString();
							sb = new StringBuffer();
						}
						else
						{
							sb.append(c);
							keyValueMap.put(key, sb.toString());
							sb = new StringBuffer();
						}
						count++;
					}
				}
			}
			if(c == '\"' && lastChar != '\\')
			{
				char h = fs.head();
				if(h == '\"')	//处于
				{
					fs.pop();
					if(fs.len() == 2)
					{
						flag = true;
					}
				}
				else
				{
					fs.put(c);
					if(fs.len() == 2)
					{
						flag = true;
					}
				}
				if(fs.len() == 1)
				{
					flag = false;
					if(count%2 == 0)
					{
						sb.deleteCharAt(0);
						key = sb.toString();
						sb = new StringBuffer();
					}
					else
					{
						sb.deleteCharAt(0);
						keyValueMap.put(key, sb.toString());
						sb = new StringBuffer();
					}
					count++;
				}
			}
			lastChar = c;
			if(flag)
			{
				sb.append(c);
			}
		}
		return keyValueMap;
	}
}
