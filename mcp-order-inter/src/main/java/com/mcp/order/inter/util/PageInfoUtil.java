package com.mcp.order.inter.util;

import com.mcp.order.inter.PageInfo;
import org.springframework.data.domain.Page;

/**
 * Created by ming.li on 2014/4/10.
 */
public class PageInfoUtil {

    /**
     * 根据spring data的分页信息，生成展示给接口调用者的分页信息
     * @param page
     * @return
     */
    public static PageInfo getPageInfo(Page<?> page)
    {
        return new PageInfo(page.getNumber(), page.getSize(), page.getTotalPages(),
                page.getNumberOfElements(), page.getTotalElements(), page.hasPreviousPage(), page.isFirstPage(),
                page.hasNextPage(), page.isLastPage());
    }
}
