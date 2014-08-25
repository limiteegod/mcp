/**
 * 
 */
package com.mcp.order.inter.util;

import org.apache.http.Header;

import javax.servlet.http.Cookie;

/**
 * @author ming.li
 *
 */
public class CookieParam {
	
	/**
	 * web请求进来的参数
	 */
	private Cookie[] cookies;
	
	/**
	 * 请求返回的时候的Cookies参数
	 */
	private Header[] headers;

	public Header[] getHeaders() {
		return headers;
	}

	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}

	public Cookie[] getCookies() {
		return cookies;
	}

	public void setCookies(Cookie[] cookies) {
		this.cookies = cookies;
	}
	
	public String getCookieString()
	{
		String cookieString = "";
		if(this.cookies != null)
		{
			for(int i = 0; i < cookies.length; i++)
			{
				if(i > 0)
				{
					cookieString += ";";
				}
				cookieString += cookies[i].getName() + "=" +  cookies[i].getValue();
			}
		}
		return cookieString;
	}
	
	public void headersToCookies()
	{
		if(this.headers != null)
		{
			this.cookies = new Cookie[this.headers.length];
			for(int i = 0; i < this.headers.length; i++)
			{
				String nameAndValue = headers[i].getValue().split(";")[0];
				String[] nameAndValueArray = nameAndValue.split("=");
				cookies[i] = new Cookie(nameAndValueArray[0], nameAndValueArray[1]);
                cookies[i].setPath("/");
			}
		}
	}
}
