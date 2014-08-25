package com.mcp.order.dao.admin;/*
 * User: yeeson he
 * Date: 13-8-20
 * Time: 下午3:49
 */

import com.mcp.order.model.admin.AreaGames;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AreaGamesDao  extends PagingAndSortingRepository<AreaGames, String> {
}
