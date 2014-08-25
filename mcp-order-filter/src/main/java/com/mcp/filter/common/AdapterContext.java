/**
 * 系统的适配器在这个类中注册
 */
package com.mcp.filter.common;

import com.mcp.core.util.ResourceUtil;
import com.mcp.core.util.tree.Tree;
import com.mcp.core.util.tree.TreeNode;
import com.mcp.filter.model.ServiceType;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.net.URL;
import java.util.List;

/**
 * @author ming.li
 *
 */
public class AdapterContext {
	
	private static Logger log = Logger.getLogger(AdapterContext.class);
	
	private static AdapterContext context;
	
	private Tree<ServiceType> tree;
	
	private AdapterContext()
	{
		URL url = ResourceUtil.getURL("config/api.xml");
		log.info("解析系统可用service配置文件：" + url.getPath());
		SAXBuilder builder = new SAXBuilder();
		Document doc;
		try {
			doc = builder.build(url);
		} catch (Exception e) {
			throw new RuntimeException("解析系统可用service配置文件：" + url.getPath() + "出现错误！");
		}
		Element root = doc.getRootElement(); //取得根节点
		ServiceType service = new ServiceType("-1", null);
		tree = new Tree<ServiceType>(service.getCmd(), service);
		List<Element> first = root.getChildren();
		TreeNode<ServiceType> parent = tree.getRoot();
		for(int i = 0; i < first.size(); i++)	//第一层
		{
			Element ele = first.get(i);
			String cmd = ele.getAttributeValue("cmd");
			String path = ele.getAttributeValue("path");
			ServiceType ser = new ServiceType(cmd, path);
			tree.add(parent, ser.getCmd(), ser);
			log.info(ser.getCmd() + "-" + ser.getPath());
		}
	}
	
	/**
	 * 获得唯一的实例
	 * @return
	 */
	public static AdapterContext getInstance()
	{
		if(context == null)
		{
			context = new AdapterContext();
		}
		return context;
	}
	
	public ServiceType getServiceType(String id)
	{
		return this.tree.quickAccessData(id);
	}
}
