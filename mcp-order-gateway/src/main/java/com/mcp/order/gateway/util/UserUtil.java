package com.mcp.order.gateway.util;

import com.mcp.core.util.cons.SystemUserType;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.service.CustomerService;
import com.mcp.order.service.StationService;
import org.springframework.context.ApplicationContext;

/**
 * Created by limitee on 2014/7/29.
 */
public class UserUtil {

    /**
     * 获得用户对象
     * @param context
     * @param type
     * @param id
     * @return
     */
    public static Object getUser(ApplicationContext context, int type, String id)
    {
        if(type == SystemUserType.CUSTOMER.getCode())
        {
            CustomerService customerService = context.getBean("customerService", CustomerService.class);
            return customerService.findOne(id);
        }
        else if(type == SystemUserType.CHANNEL.getCode())
        {
            StationService stationService = context.getBean("stationService", StationService.class);
            return stationService.findOneByCode(id);
        }
        else
        {
            throw new CoreException(ErrCode.E1003);
        }
    }

}
