package com.mcp.order.dao.admin;

/*
 * User: yeeson he
 * Date: 13-8-20
 * Time: 下午3:14
 */

import com.mcp.order.model.admin.CPlatform;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CPlatformDao extends PagingAndSortingRepository<CPlatform, String> {
	
	@Modifying
    @Query("update CPlatform c set c.formLongValue=c.formLongValue+?1 where c.formKey=?2")
	public int incrLongValueByKey(long money, String formKey);
	
	@Modifying
    @Query("update CPlatform c set c.formLongValue=c.formLongValue-?1 where c.formKey=?2")
	public int decrLongValueByKey(long money, String formKey);
	
	@Modifying
    @Query("update CPlatform c set c.formLongValue=c.formLongValue+?1 where c.id=?2")
	public int incrLongValueById(long money, String id);
	
	@Modifying
    @Query("update CPlatform c set c.formLongValue=c.formLongValue-?1 where c.id=?2")
	public int decrLongValueById(long money, String id);
}
