/**
 * 
 */
package com.mcp.mq.common;

import com.mcp.core.util.ResourceUtil;
import com.mcp.core.util.tree.Tree;
import com.mcp.core.util.tree.TreeNode;
import com.mcp.mq.model.MessageConfig;
import com.mcp.order.inter.Filter;
import com.mcp.order.inter.FilterName;
import com.mcp.order.inter.FilterProperty;
import com.mcp.order.inter.Response;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author ming.li
 *
 */
public class McpMessageContext {
	
	private static Logger log = Logger.getLogger(McpMessageContext.class);
	
	private static McpMessageContext context;
	
	private Tree<Object> tree;
	
	private McpMessageContext()
	{         //TODO 配置文件如何与数据库保持同步？
		URL url = ResourceUtil.getURL("config/mcp_msg.xml");
		log.info("解析系统可用游戏配置文件：" + url.getPath());
		SAXBuilder builder = new SAXBuilder();
		Document doc;
		try {
			doc = builder.build(url);
		} catch (Exception e) {
			throw new RuntimeException("解析系统可用游戏配置文件：" + url.getPath() + "出现错误！");
		}
		Element root = doc.getRootElement(); //取得根节点
		tree = new Tree<Object>("-1", new Object());
		List<Element> first = root.getChildren();
		TreeNode<Object> parent = tree.getRoot();
		for(int i = 0; i < first.size(); i++)	//第一层
		{
			Element ele = first.get(i);
			String code = ele.getAttributeValue("code");
			String name = ele.getAttributeValue("name");
			String queue = ele.getAttributeValue("queue");
			MessageConfig mc = new MessageConfig();
			mc.setCode(Integer.parseInt(code));
			mc.setName(name);
			mc.setQueue(queue);
			
			tree.add(parent, code, mc);
			
			//reponse
			List<Element> repList = ele.getChildren();
			for(int k = 0; k < repList.size(); k++)
			{
				Element repEle = repList.get(k);
				String repCode = repEle.getAttributeValue("code");
				Response rep = new Response();
				rep.setCode(code + repCode);
				tree.add(tree.quickAccess(code), rep.getCode(), rep);
				
				//filter
				List<Element> filterList = repEle.getChildren();
				for(int l = 0; l < filterList.size(); l++)
				{
					Element filterEle = filterList.get(l);
					String filterCode = filterEle.getAttributeValue("code");
					Filter filter = new Filter();
					filter.setCode(code + repCode + filterCode);
					tree.add(tree.quickAccess(code + repCode), filter.getCode(), filter);
					List<FilterName> filterNameObjectList = new ArrayList<FilterName>();
					
					//filtername
					List<Element> filterNameList = filterEle.getChildren();
					for(int m = 0; m < filterNameList.size(); m++)
					{
						Element filterNameEle = filterNameList.get(m);
						String filterNameValue = filterNameEle.getAttributeValue("value");
						FilterName filterName = new FilterName();
						filterName.setValue(filterNameValue);
						tree.add(tree.quickAccess(code + repCode + filterCode), code + repCode + filterCode + filterNameValue, filterName);
						List<FilterProperty> filterPorpertyObjectList = new ArrayList<FilterProperty>();
						
						//filterporperty
						List<Element> filterPorpertyList = filterNameEle.getChildren();
						for(int n = 0; n < filterPorpertyList.size(); n++)
						{
							Element filterPropertyEle = filterPorpertyList.get(n);
							String filterPropertyValue = filterPropertyEle.getAttributeValue("value");
							FilterProperty fp = new FilterProperty();
							fp.setValue(filterPropertyValue);
							tree.add(tree.quickAccess(code + repCode + filterCode + filterNameValue), code + repCode + filterCode + filterNameValue + filterPropertyValue, fp);
							filterPorpertyObjectList.add(fp);
						}
						filterName.setPropertyList(filterPorpertyObjectList);
						filterNameObjectList.add(filterName);
					}
					filter.setNameList(filterNameObjectList);
				}
			}
		}
	}
	
	/**
	 * 获得唯一的实例
	 * @return
	 */
	public static McpMessageContext getInstance()
	{
		if(context == null)
		{
			context = new McpMessageContext();
		}
		return context;
	}
	
	/**
	 * 获得消息配置
	 * @param code
	 * @return
	 */
	public MessageConfig getMcByCode(int code)
	{
		return (MessageConfig)this.tree.quickAccessData(String.valueOf(code));
	}
	
	/**
	 * 获得过滤配置
	 * @param code
	 * @return
	 */
	public Filter getFilterByCode(String code)
	{
		Filter object = (Filter)tree.quickAccessData(code);
		return object;
	}
	
	/**
	 * 获得过滤配置
	 * @param code
	 * @return
	 */
	public String getDefaultFilteCoderByCode(String code)
	{
		return code + "0101";
	}
	
	/**
	 * 通过命令code获取过滤规则，获取的是第一个reponse的，第一个filter所产生的结果
	 * @param cmd
	 * @return
	 */
	public FilterProvider getFilterProviderByCode(String code)
	{
		log.info("filter-code:" + code);
		SimpleFilterProvider provider = new SimpleFilterProvider();
		Filter filter = getFilterByCode(code);
		List<FilterName> nameList = filter.getNameList();
		for(int i = 0; i < nameList.size(); i++)
		{
			FilterName name = nameList.get(i);
			List<FilterProperty> propertyList = name.getPropertyList();
			HashSet<String> set = new HashSet<String>();
			for(int j = 0; j < propertyList.size(); j++)
			{
				set.add(propertyList.get(j).getValue());
			}
			provider.addFilter(name.getValue(), SimpleBeanPropertyFilter.serializeAllExcept(set));
		}
		return provider;
	}
	
	/**
	 * 通过命令code获取过滤规则，获取的是默认的fp
	 * @param cmd
	 * @return
	 */
	public FilterProvider getDefaultFilterProviderByCode(String code)
	{
		return getFilterProviderByCode(getDefaultFilteCoderByCode(code));
	}
}
