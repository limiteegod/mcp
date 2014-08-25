package com.mcp.order.dao.finance;/*
 * User: yeeson he
 * Date: 13-9-16
 * Time: 下午4:27
 */

import com.mcp.core.util.tree.Tree;
import org.jdom2.Element;

import java.util.Iterator;

public class AccountNest {

    public static Tree<AccountOperatorType> nest(String parentType, Element element, Tree<AccountOperatorType> tree) {
        if (element.getChildren() != null) {
            Iterator<Element> elements = element.getChildren().iterator();
            while (elements.hasNext()) {
                Element ele = elements.next();
                AccountOperatorType op = new AccountOperatorType(parentType + ele.getAttributeValue("type"), ele.getAttributeValue("name"));
                String id = ParentNest.getQuickAccessCode(ele,"")+ele.getAttributeValue("type");
                tree.add(tree.quickAccess(ParentNest.getQuickAccessCode(ele.getParentElement(),ele.getParentElement().getAttributeValue("type"))), id, op);
                nest(parentType + ele.getAttributeValue("type"), ele, tree);
            }
        }
        return tree;
    }
}
