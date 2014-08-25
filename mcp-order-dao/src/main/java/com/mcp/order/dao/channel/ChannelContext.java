/**
 * 
 */
package com.mcp.order.dao.channel;

import com.mcp.core.util.ResourceUtil;
import com.mcp.core.util.tree.Tree;
import com.mcp.core.util.tree.TreeNode;
import com.mcp.order.common.Constants;
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
public class ChannelContext {
	
	private static Logger log = Logger.getLogger(ChannelContext.class);
	
	private static ChannelContext context;
	
	private Tree<Object> tree;
	
	private ChannelContext()
	{
		URL url = ResourceUtil.getURL("config/channel.xml");
		log.info("解析系统渠道配置文件：" + url.getPath());
		SAXBuilder builder = new SAXBuilder();
		Document doc;
		try {
			doc = builder.build(url);
		} catch (Exception e) {
			throw new RuntimeException("解析系统可用游戏配置文件：" + url.getPath() + "出现错误！");
		}
		Element root = doc.getRootElement(); //取得根节点
		tree = new Tree<Object>("-1", new Object());
		List<Element> channelList = root.getChildren();
		TreeNode<Object> parent = tree.getRoot();
		for(int i = 0; i < channelList.size(); i++)	//第一层
		{
			SChannel sChannel = new SChannel();
			Element channelEle = channelList.get(i);
			String code = channelEle.getAttribute("code").getValue();
			sChannel.setCode(code);
			
			Element bigBonusEle = channelEle.getChild("bigBonus");
			if(bigBonusEle == null)
			{
				sChannel.setBigBonus(Constants.DEFAULT_BIGBONUS_BORDER);
			} 
			else
			{
				int bigBonus = Integer.parseInt(bigBonusEle.getValue());
				sChannel.setBigBonus(bigBonus);
			}
			
			//是否校验余额
			Element checkBalanceAtBetEle = channelEle.getChild("checkBalanceAtBet");
			if(checkBalanceAtBetEle == null)
			{
				sChannel.setCheckBalanceAtBet(true);
			} 
			else
			{
				boolean checkBalanceAtBet = Boolean.parseBoolean(checkBalanceAtBetEle.getValue());
				sChannel.setCheckBalanceAtBet(checkBalanceAtBet);
			}
			
			//算奖参数类
			Element drawParamEle = channelEle.getChild("draw").getChild("param");
			sChannel.setDrawParam(drawParamEle.getValue());
			
			//算奖任务类
			Element drawJobEle = channelEle.getChild("draw").getChild("job");
			sChannel.setDrawJob(drawJobEle.getValue());
			
			//返奖类
			Element prizeBeanEle = channelEle.getChild("prize").getChild("bean");
			sChannel.setPrizeBean(prizeBeanEle.getValue());
			
			//返奖的任务类名称
			Element prizeJobEle = channelEle.getChild("prize").getChild("job");
			sChannel.setPrizeJob(prizeJobEle.getValue());
			tree.add(parent, code, sChannel);
		}
	}
	
	public static ChannelContext getInstance()
	{
		if(context == null)
		{
			context = new ChannelContext();
		}
		return context;
	}
	
	/**
	 * 根据code获得渠道的配置
	 * @return
	 */
	public SChannel getSChannelByCode(String code)
	{
		return (SChannel)tree.quickAccessData(code);
	}
	
	public static void main(String[] args)
	{
		SChannel channel = ChannelContext.getInstance().getSChannelByCode("Q0001");
		log.info(channel.getBigBonus());
	}
}
