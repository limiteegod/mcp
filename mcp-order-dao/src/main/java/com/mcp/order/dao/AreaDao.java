/**
 * 
 */
package com.mcp.order.dao;

import com.mcp.order.model.admin.Area;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author ming.li
 *
 */
public interface AreaDao extends PagingAndSortingRepository<Area, String> {
    public List<Area> findAllByALevelAndStatus(int aLevel,int status);
    public List<Area> findAllByParentId(String parentId);
}
