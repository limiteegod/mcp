/**
 * 
 */
package com.mcp.order.inter.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * @author ming.li
 *
 */
public class HttpClientUtil {
	
	public static final int GET = 0;
	
	public static final int POST = 1;
	
	/**
	 * 模拟http get请求
	 * @param host
	 * @param port
	 * @param path
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String request(String host, int port, String path, String message, int method, CookieParam param) throws Exception
	{
		URIBuilder rBuilder = new URIBuilder();
		rBuilder.setScheme("http").setHost(host).setPort(port).setPath(path);
		URI uri = rBuilder.build();
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpRequestBase request = null;
		if(method == HttpClientUtil.GET)
		{
			rBuilder.addParameter("message", message);
			request = new HttpGet(uri);
		}
		else
		{
			HttpPost post = new HttpPost(uri);
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("message", message));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			post.setEntity(entity);
			request = post;
		}
        //添加timeout属性
        RequestConfig.Builder rcBuilder = RequestConfig.copy(RequestConfig.DEFAULT);
        rcBuilder.setConnectTimeout(10000);
        rcBuilder.setSocketTimeout(10000);
        request.setConfig(rcBuilder.build());
		if(param != null)
		{
			//System.out.println(param.getCookieString());
			request.setHeader("Cookie", param.getCookieString());
		}
		HttpResponse response = httpclient.execute(request);
		HttpEntity entity = response.getEntity();
		if(param != null)
		{
			param.setHeaders(response.getHeaders("Set-Cookie"));
		}
		if (entity != null) {
		    InputStream instream = entity.getContent();
		    return getStringFromInputStream(instream);
		}
		return null;
	}
	
	/**
	 * 模拟http get请求
	 * @param host
	 * @param port
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String request(String host, int port, String path, String head, String body, int method, CookieParam param) throws Exception
	{
		URIBuilder rBuilder = new URIBuilder();
		rBuilder.setScheme("http").setHost(host).setPort(port).setPath(path);
		URI uri = rBuilder.build();
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpRequestBase request = null;
		if(method == HttpClientUtil.GET)
		{
			rBuilder.addParameter("head", head);
			rBuilder.addParameter("body", body);
			request = new HttpGet(uri);
		}
		else
		{
			HttpPost post = new HttpPost(uri);
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("head", head));
			formparams.add(new BasicNameValuePair("body", body));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			post.setEntity(entity);
			request = post;
		}
        //添加timeout属性
        RequestConfig.Builder rcBuilder = RequestConfig.copy(RequestConfig.DEFAULT);
        rcBuilder.setConnectTimeout(10000);
        rcBuilder.setSocketTimeout(10000);
        request.setConfig(rcBuilder.build());
		if(param != null)
		{
			//System.out.println(param.getCookieString());
			request.setHeader("Cookie", param.getCookieString());
		}
		HttpResponse response = httpclient.execute(request);
		HttpEntity entity = response.getEntity();
		if(param != null)
		{
			param.setHeaders(response.getHeaders("Set-Cookie"));
		}
		if (entity != null) {
		    InputStream instream = entity.getContent();
		    return getStringFromInputStream(instream);
		}
		return null;
	}
	
	/**
	 * 模拟http get请求
	 * @param url
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String request(String url, String message, int method, CookieParam param) throws Exception
	{
		URIBuilder rBuilder = new URIBuilder(url);
		URI uri = rBuilder.build();
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpRequestBase request = null;
		if(method == HttpClientUtil.GET)
		{
			rBuilder.addParameter("message", message);
			request = new HttpGet(uri);
		}
		else
		{
			HttpPost post = new HttpPost(uri);
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("message", message));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			post.setEntity(entity);
			request = post;
		}
        //添加timeout属性
        RequestConfig.Builder rcBuilder = RequestConfig.copy(RequestConfig.DEFAULT);
        rcBuilder.setConnectTimeout(10000);
        rcBuilder.setSocketTimeout(10000);
        request.setConfig(rcBuilder.build());
		if(param != null)
		{
			request.setHeader("Cookie", param.getCookieString());
		}
		HttpResponse response = httpclient.execute(request);
		HttpEntity entity = response.getEntity();
		if(param != null)
		{
			param.setHeaders(response.getHeaders("Set-Cookie"));
		}
		if (entity != null) {
		    InputStream instream = entity.getContent();
		    return getStringFromInputStream(instream);
		}
		return null;
	}
	
	/**
	 * 
	 * @param instream
	 * @return
	 */
	private static String getStringFromInputStream(InputStream instream) throws Exception
	{
		byte[] dest = new byte[1024];
		int incr = 512;
		int destLen = 0;	//当前数据的长度
		
		byte[] buffer = new byte[256];	//buffer的大小必须小于incr，以保证增长之后有足够的空间
		int size;
		try {
			while((size = instream.read(buffer)) > 0)
	        {
				if(size + destLen > dest.length)
				{
					byte[] newDest = new byte[dest.length + incr];
					System.arraycopy(dest, 0, newDest, 0, destLen);
					dest = newDest;
				}
				System.arraycopy(buffer, 0, dest, destLen, size);
				destLen += size;
	        }
		} finally {
	        instream.close();
	    }
		return new String(dest, 0, destLen, Charset.forName("UTF-8"));
	}
}
