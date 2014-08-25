/**
 *
 */
package com.mcp.order.service;

import com.mcp.order.dao.BetShopDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ming.li
 */
@Service("betShopService")
public class BetShopService {
	
    @Autowired
    private BetShopDao betShopDao;
    
    
}
