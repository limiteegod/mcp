/**
 * 
 */
package com.mcp.order.dao;

import com.mcp.order.model.admin.Admini;
import com.mcp.order.model.admin.Area;
import com.mcp.order.model.ts.TOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author ming.li
 *
 */
public interface AdminiDao extends PagingAndSortingRepository<Admini, String>, JpaSpecificationExecutor<Admini> {

    @Query("from Admini t where t.name=?1 and t.type=?2")
    public Admini findOneByNameAndType(String name, int type);

    @Query("from Admini t where t.name=?1 and t.password=?2")
    public Admini findOneByNameAndPassword(String name, String password);

}
