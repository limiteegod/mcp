/**
 * 系统对资金的可用操作都在这个类中进行注册
 */
package com.mcp.order.dao.finance;

import com.mcp.core.util.ResourceUtil;
import com.mcp.core.util.tree.Tree;
import com.mcp.core.util.tree.TreeNode;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.net.URL;

/**
 * @author ming.li
 */
public class AccountOperator {
    private static Logger log = Logger.getLogger(AccountOperator.class);

    private static AccountOperator context;

    private Tree<AccountOperatorType> tree;

    private AccountOperator() {
        URL url = ResourceUtil.getURL("config/accountOperatorType.xml");
        log.info("解析用户资金相关可用操作配置文件：" + url.getPath());
        SAXBuilder builder = new SAXBuilder();
        Document doc;
        try {
            doc = builder.build(url);
        } catch (Exception e) {
            throw new RuntimeException("解析用户资金相关可用操作配置文件：" + url.getPath() + "出现错误！");
        }

        Element root = doc.getRootElement(); //取得根节点
        AccountOperatorType operator = new AccountOperatorType(root.getAttributeValue("type"), root.getAttributeValue("name"));
        tree = AccountNest.nest(root.getAttributeValue("type"), root, new Tree<AccountOperatorType>(operator.getType(), operator));
    }

    /**
     * 获得唯一的实例
     *
     * @return
     */
    public static AccountOperator getInstance() {
        if (context == null) {
            context = new AccountOperator();
        }
        return context;
    }

    /**
     * 通过id获得AccountOperatorType对象
     *
     * @param id
     * @return
     */
    public AccountOperatorType getAccountOperatorType(String id) {
        return this.tree.quickAccessData(id);
    }

	public Tree<AccountOperatorType> getTree() {
		return tree;
	}

	public void setTree(Tree<AccountOperatorType> tree) {
		this.tree = tree;
	}
    
	/**
	 * 根据id获取账户操作的父级
	 * @param id
	 * @return
	 */
	public AccountOperatorType getParent(String id)
	{
		TreeNode<AccountOperatorType> curNode = this.tree.quickAccess(id);
		if(curNode == null)
		{
			return null;
		}
		TreeNode<AccountOperatorType> parentNode = curNode.getParent();
		if(parentNode == null)
		{
			return null;
		}
		return parentNode.getData();
	}
}
