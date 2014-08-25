/**
 *
 */
package com.mcp.order.dao;

import com.mcp.order.model.ts.WithDraw;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * @author ming.li
 */
public interface WithDrawDao extends PagingAndSortingRepository<WithDraw, String>, JpaSpecificationExecutor<WithDraw> {

}
