package com.mcp.deploy.cmd.generate;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.cons.OperationType;
import com.mcp.core.util.cons.SystemUserType;

/**
 * Created by limitee on 2014/8/6.
 */
public class OperationGenerate {

    public static void main(String[] args)
    {
        getUserOperation();
    }


    public static void getOperation()
    {
        String sql = "insert into operation(id,name,parentid,type,url) values('%s','%s','%s', %d, '%s');";
        String termDetailInfo = String.format(sql, CoreUtil.getUUID(), "普通查询", "4ef3559a17f248ec971031948953f578", OperationType.DETAIL.ordinal(), "order/normalQuery.htm");
        System.out.println(termDetailInfo);
    }

    public static void getUserOperation()
    {
        String sql = "insert into useroperation(id,operationId,userType) values('%s','%s',%d);";
        String termDetailInfo = String.format(sql, CoreUtil.getUUID(), "f3c93b89d4674557a173b981b62e25e6", SystemUserType.ADMINISTRATOR.ordinal());
        System.out.println(termDetailInfo);
    }
}
