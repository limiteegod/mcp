/**
 * 
 */
package com.mcp.filter.model;

/**
 * @author ming.li
 *
 */
public class FilterStack {
	
	private int len = 0;
	
	private char[] array = new char[1024];
	
	/**
	 * 入栈
	 * @param c
	 */
	public void put(char c)
	{
		array[len] = c;
		len++;
	}
	
	/**
	 * 出栈
	 * @return
	 */
	public char pop()
	{
		if(len == 0)
		{
			throw new RuntimeException("栈已经空了");
		}
		char c = array[len - 1];
		len--;
		return c;
	}
	
	/**
	 * 获得栈的最上层的字符
	 * @return
	 */
	public char head()
	{
		if(len == 0)
		{
			return '\0';
		}
		return array[len - 1];
	}
	
	/**
	 * 获得栈的大小
	 * @return
	 */
	public int len()
	{
		return len;
	}
}
