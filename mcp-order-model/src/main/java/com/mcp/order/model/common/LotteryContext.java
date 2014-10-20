/**
 * 
 */
package com.mcp.order.model.common;

import com.mcp.core.util.ResourceUtil;
import com.mcp.core.util.tree.Tree;
import com.mcp.core.util.tree.TreeNode;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.model.entity.BetType;
import com.mcp.order.model.entity.PlayType;
import com.mcp.order.model.ts.Game;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ming.li
 *
 */
public class LotteryContext {
	
	private static final String SEP = "*";
	
	private static Logger log = Logger.getLogger(LotteryContext.class);
	
	private static LotteryContext context;
	
	private Tree<Object> tree;
	
	private LotteryContext()
	{         //TODO 配置文件如何与数据库保持同步？
		URL url = ResourceUtil.getURL("config/lotteryInfo.xml");
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
			String fCode = ele.getAttributeValue("code");
			String fName = ele.getAttributeValue("name");
			int fStatus = Integer.parseInt(ele.getAttributeValue("status"));
			int fType = Integer.parseInt(ele.getAttributeValue("type"));
			String fPeriod = ele.getAttributeValue("period");
			String fPublishDesc = ele.getAttributeValue("publishDesc");
			int gameOffset = Integer.parseInt(ele.getAttributeValue("offset"));
			Game game = new Game();
			game.setCode(fCode);
			game.setName(fName);
			game.setPeriod(fPeriod);
			game.setPublishDesc(fPublishDesc);
			game.setStatus(fStatus);
			game.setOffset(gameOffset);
			game.setType(fType);
			tree.add(parent, fCode, game);
//			log.info(game.getCode() + "-" + game.getName());
			List<Element> second = ele.getChildren();
			for(int j = 0; j < second.size(); j++)
			{
				Element secondEle = second.get(j);
				String sCode = fCode + secondEle.getAttributeValue("code");
				String sName = fName + SEP + secondEle.getAttributeValue("name");
				int sStatus = Integer.parseInt(secondEle.getAttributeValue("status"));
				int price = Integer.parseInt(secondEle.getAttributeValue("price"));
				PlayType gpt = new PlayType();
				gpt.setGameCode(fCode);
				gpt.setTypeCode(secondEle.getAttributeValue("code"));
				gpt.setTypeName(sName);
				gpt.setStatus(sStatus);
				gpt.setPrice(price);
				tree.add(tree.quickAccess(fCode), sCode, gpt);
//				log.info(gpt.getGameCode() + "-" + gpt.getTypeCode() + "-" + gpt.getTypeName());
				List<Element> third = secondEle.getChildren();
				for(int k = 0; k < third.size(); k++)
				{
					Element thirdEle = third.get(k);
					String tCode = sCode + thirdEle.getAttributeValue("code");
					String tName = sName + SEP + thirdEle.getAttributeValue("name");
					int tStatus = Integer.parseInt(thirdEle.getAttributeValue("status"));
					String validatorClass = thirdEle.getAttributeValue("validator");
					String check = thirdEle.getAttributeValue("check");
					int gbtOffset = Integer.parseInt(thirdEle.getAttributeValue("offset"));
					//log.info(validatorClass);
					BetType gbt = new BetType();
					gbt.setGameCode(fCode);
					gbt.setStatus(tStatus);
					gbt.setTypeCode(thirdEle.getAttributeValue("code"));
					gbt.setTypeName(tName);
					gbt.setValidatorClass(validatorClass);
					gbt.setCheck(check);
					gbt.setOffset(gbtOffset);
					tree.add(tree.quickAccess(sCode), tCode, gbt);
					//log.info(gbt.getGameCode() + "-" + gbt.getTypeCode() + "-" + gbt.getTypeName() + "-" + validatorClass);
				}
			}
		}
	}
	
	/**
	 * 获得唯一的实例
	 * @return
	 */
	public synchronized static LotteryContext getInstance()
	{
		if(context == null)
		{
			context = new LotteryContext();
		}
		return context;
	}
	
	public Game getGameByCode(String code)
	{
		return (Game)tree.quickAccessData(code);
	}
	
	/**
	 * 通过游戏代码获得所有的玩法类型
	 * @param gameCode
	 * @return
	 */
	public List<PlayType> getAllPlayTypeByGameCode(String gameCode)
	{
		List<PlayType> ptList = new ArrayList<PlayType>();
		TreeNode<Object> gameNode = tree.quickAccess(gameCode);
		List<TreeNode<Object>> children = gameNode.getChildren();
		for(TreeNode<Object> node:children)
		{
			ptList.add((PlayType)node.getData());
		}
		return ptList;
	}
	
	/**
	 * 获得配置了的所有的游戏
	 * @return
	 */
	public List<Game> getAllGames()
	{
		List<Game> gameList = new ArrayList<Game>();
		List<TreeNode<Object>> gameNodeList = tree.getRoot().getChildren();
		for(int i = 0; i < gameNodeList.size(); i++)
		{
			gameList.add((Game)gameNodeList.get(i).getData());
		}
		return gameList;
	}
	
	public PlayType getPlayTypeByCode(String code)
	{
		return (PlayType)tree.quickAccessData(code);
	}
	
	public BetType getBetTypeByCode(String code)
	{
		return (BetType)tree.quickAccessData(code);
	}
	
	/**
	 * 获得投注方式的配置
	 * @param Code
	 * @return
	 */
	public BetType getBetTypeByCode(String gameCode, String playTypeCode, String betTypeCode)
	{
		Game g = getGameByCode(gameCode);
		String code;
		if(g.getType() == ConstantValues.Game_Type_Jingcai.getCode())
		{
			code = gameCode + playTypeCode + "00";
		}
		else
		{
			code = gameCode + playTypeCode + betTypeCode;
		}
		return (BetType)tree.quickAccessData(code);
	}
}
