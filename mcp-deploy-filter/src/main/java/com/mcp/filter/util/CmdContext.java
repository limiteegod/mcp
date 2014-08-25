/**
 * 
 */
package com.mcp.filter.util;

import com.mcp.core.util.ResourceUtil;
import com.mcp.core.util.tree.Tree;
import com.mcp.core.util.tree.TreeNode;
import com.mcp.order.inter.*;
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
public class CmdContext {
	
	private static Logger log = Logger.getLogger(CmdContext.class);
	
	private static CmdContext context;
	
	private Tree<Object> tree;
	
	private CmdContext()
	{         //TODO 配置文件如何与数据库保持同步？
		URL url = ResourceUtil.getURL("config/cmd.xml");
		log.info("解析系统可用cmd文件：" + url.getPath());
		SAXBuilder builder = new SAXBuilder();
		Document doc;
		try {
			doc = builder.build(url);
		} catch (Exception e) {
			throw new RuntimeException("解析系统可用游戏配置文件：" + url.getPath() + "出现错误！");
		}
		Element root = doc.getRootElement(); //取得根节点
		tree = new Tree<Object>("-1", new Object());
		List<Element> groupList = root.getChildren();
		TreeNode<Object> parent = tree.getRoot();
		for(int i = 0; i < groupList.size(); i++)	//第一层
		{
			Element ele = groupList.get(i);
			String groupCode = ele.getAttributeValue("code");
			String groupPackage = ele.getAttributeValue("package");
			String repPkg = ele.getAttributeValue("rep");
			String reqPkg = ele.getAttributeValue("req");
			CmdGroup group = new CmdGroup();
			group.setCode(groupCode);
			group.setPkg(groupPackage);
			group.setRep(repPkg);
			group.setReq(reqPkg);
			tree.add(parent, groupCode, group);
			
			//cmd命令
			List<Element> cmdList = ele.getChildren();
			for(int j = 0; j < cmdList.size(); j++)
			{
				Element cmdEle = cmdList.get(j);
				String cmdCode = cmdEle.getAttributeValue("code");
				String path = cmdEle.getAttributeValue("path");
				Cmd cmd = new Cmd();
				cmd.setCode(groupCode + cmdCode);
				cmd.setPath(path);
				tree.add(tree.quickAccess(groupCode), cmd.getCode(), cmd);
				
				//reponse
				List<Element> repList = cmdEle.getChildren();
				for(int k = 0; k < repList.size(); k++)
				{
					Element repEle = repList.get(k);
					String repCode = repEle.getAttributeValue("code");
					Response rep = new Response();
					rep.setCode(groupCode + cmdCode + repCode);
					tree.add(tree.quickAccess(groupCode + cmdCode), rep.getCode(), rep);
					
					//filter
					List<Element> filterList = repEle.getChildren();
					for(int l = 0; l < filterList.size(); l++)
					{
						Element filterEle = filterList.get(l);
						String filterCode = filterEle.getAttributeValue("code");
						Filter filter = new Filter();
						filter.setCode(groupCode + cmdCode + repCode + filterCode);
						tree.add(tree.quickAccess(groupCode + cmdCode + repCode), filter.getCode(), filter);
						List<FilterName> filterNameObjectList = new ArrayList<FilterName>();
						
						//filtername
						List<Element> filterNameList = filterEle.getChildren();
						for(int m = 0; m < filterNameList.size(); m++)
						{
							Element filterNameEle = filterNameList.get(m);
							String filterNameValue = filterNameEle.getAttributeValue("value");
							FilterName filterName = new FilterName();
							filterName.setValue(filterNameValue);
							tree.add(tree.quickAccess(groupCode + cmdCode + repCode + filterCode), groupCode + cmdCode + repCode + filterCode + filterNameValue, filterName);
							List<FilterProperty> filterPorpertyObjectList = new ArrayList<FilterProperty>();
							
							//filterporperty
							List<Element> filterPorpertyList = filterNameEle.getChildren();
							for(int n = 0; n < filterPorpertyList.size(); n++)
							{
								Element filterPropertyEle = filterPorpertyList.get(n);
								String filterPropertyValue = filterPropertyEle.getAttributeValue("value");
								FilterProperty fp = new FilterProperty();
								fp.setValue(filterPropertyValue);
								tree.add(tree.quickAccess(groupCode + cmdCode + repCode + filterCode + filterNameValue), groupCode + cmdCode + repCode + filterCode + filterNameValue + filterPropertyValue, fp);
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
		tree.traverse(parent);
	}
	
	/**
	 * 获得唯一的实例
	 * @return
	 */
	public static CmdContext getInstance()
	{
		if(context == null)
		{
			context = new CmdContext();
		}
		return context;
	}
	
	/**
	 * 获得命令分组
	 * @return
	 */
	public CmdGroup getCmdGroupByCode(String code)
	{
		CmdGroup object = (CmdGroup)tree.quickAccessData(code);
		return object;
	}
	
	/**
	 * 获得命令
	 * @return
	 */
	public Cmd getCmdByCode(String code)
	{
		Cmd object = (Cmd)tree.quickAccessData(code);
		return object;
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
	public Filter getFilterByCmdCode(String cmdCode)
	{
		TreeNode<Object> cmdNode = tree.quickAccess(cmdCode);
		List<TreeNode<Object>> repList = cmdNode.getChildren();
		List<TreeNode<Object>> filterList = repList.get(0).getChildren();
		return (Filter)(filterList.get(0).getData());
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
	
	public static void main(String[] args)
	{
		Filter cg = CmdContext.getInstance().getFilterByCode("A010101");
		System.out.println(cg.getCode());
	}
}
