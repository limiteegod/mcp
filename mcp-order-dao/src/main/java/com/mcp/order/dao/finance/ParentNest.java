package com.mcp.order.dao.finance;/*
 * User: yeeson he
 * Date: 13-9-16
 * Time: 下午5:14
 */

import org.jdom2.Element;

public class ParentNest {
    private static String code = "";
    public static String getQuickAccessCode(Element element,String nestCode) {
        if (!element.isRootElement()) {
            element = element.getParentElement();
            code = element.getAttributeValue("type")+nestCode;
            getQuickAccessCode(element,code);
        } else {
            return code;
        }
        return code;
    }
}
