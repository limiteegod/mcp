/**
 * 
 */
package com.mcp.order.service;

import com.mcp.core.util.StringUtil;
import com.mcp.order.dao.MoneyLogDao;
import com.mcp.order.dao.specification.MoneyLogSpecification;
import com.mcp.order.model.ts.MoneyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ming.li
 *
 */
@Service("moneyLogService")
public class MoneyLogService {
	
	@Autowired
	private MoneyLogDao moneyLogDao;
	
	public MoneyLog save(MoneyLog moneyLog)
	{
		return this.moneyLogDao.save(moneyLog);
	}
	
	/**
	 * 彩民交易流水查询
	 * @param customerId
	 * @param handlerCode
	 * @param startTime
	 * @param endTime
	 * @param startIndex
	 * @param size
	 * @return
	 */
	public Page<MoneyLog> customerQueryRecord(List<String> entityIds, String accountType, Date startTime, Date endTime, int startIndex, int size)
	{
		Sort sort = new Sort(new Order(Direction.DESC, "createTimeStamp"));
		PageRequest pr = new PageRequest(startIndex, size, sort);
		return this.logQuery(entityIds, accountType, startTime, endTime, pr);
	}
	
	/**
	 * 用户定制查询
	 * @param entityId
	 * @param handlerCode
	 * @param startTime
	 * @param endTime
	 * @param p
	 * @return
	 */
	public Page<MoneyLog> logQuery(List<String> entityIds, String accountType, Date startTime, Date endTime, Pageable p)
	{
		Specifications<MoneyLog> specs = null;
		for(int i = 0; i < entityIds.size(); i++)
		{
			String entityId = entityIds.get(i);
			if(specs == null)
			{
				specs = Specifications.where(MoneyLogSpecification.isFromEntityIdEqual(entityId));
			}
			else
			{
				specs = specs.or(MoneyLogSpecification.isFromEntityIdEqual(entityId));
			}
			specs = specs.or(MoneyLogSpecification.isToEntityIdEqual(entityId));
		}
		
		if(!StringUtil.isEmpty(accountType))
		{
			specs = specs.and(MoneyLogSpecification.isOperationCodeLike(accountType));
		}
		if(startTime != null)
		{
			specs = specs.and(MoneyLogSpecification.isCreateTimeStampAfter(startTime.getTime()));
		}
		if(endTime != null)
		{
			specs = specs.and(MoneyLogSpecification.isCreateTimeStampBefore(endTime.getTime()));
		}
		return this.moneyLogDao.findAll(specs, p);
	}

    public long getStationTodayRecharge(String stationId,String handleCode,long time ){
        Object object =this.moneyLogDao.getStationTodayRecharge(stationId,handleCode,time);
        if(object==null){
            return  0;
        }
        return Double.valueOf(object.toString()).longValue();
    }

    public MoneyLog findOneByOperationCodeAndOrderId(String operationCode, String orderId)
    {
        return this.moneyLogDao.findOneByOperationCodeAndOrderId(operationCode, orderId);
    }
}
