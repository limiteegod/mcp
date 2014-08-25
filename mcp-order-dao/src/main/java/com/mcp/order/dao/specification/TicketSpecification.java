/**
 * 
 */
package com.mcp.order.dao.specification;

import com.mcp.order.model.ts.TTicket;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;


/**
 * @author ming.li
 *
 */
public class TicketSpecification {
	
	/**
	 * 状态是否一致
	 * @param status
	 * @return
	 */
	public static Specification<TTicket> isStatusEqual(final int status)
	{
		return new Specification<TTicket>(){
			@Override
			public Predicate toPredicate(Root<TTicket> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("status"), status);
			}
		};
	}
	
	/**
	 * 订单号是否相等
	 * @param orderId
	 * @return
	 */
	public static Specification<TTicket> isOrderIdEqual(final String orderId)
	{
		return new Specification<TTicket>(){
			@Override
			public Predicate toPredicate(Root<TTicket> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("orderId"), orderId);
			}
		};
	}
	
	/**
	 * 终端机号是否一致
	 * @param terminalId
	 * @return
	 */
	public static Specification<TTicket> isTerminalIdEqual(final String terminalId)
	{
		return new Specification<TTicket>(){
			@Override
			public Predicate toPredicate(Root<TTicket> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("terminalId"), terminalId);
			}
		};
	}
	
	/**
	 * 投注站id是否一致
	 * @param stationId
	 * @return
	 */
	public static Specification<TTicket> isStationIdEqual(final String stationId)
	{
		return new Specification<TTicket>(){
			@Override
			public Predicate toPredicate(Root<TTicket> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("stationId"), stationId);
			}
		};
	}
	
	/**
	 * 投注站id是否一致
	 * @param printerStationId
	 * @return
	 */
	public static Specification<TTicket> isPrinterStationIdEqual(final String printerStationId)
	{
		return new Specification<TTicket>(){
			@Override
			public Predicate toPredicate(Root<TTicket> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("printerStationId"), printerStationId);
			}
		};
	}
	
	/**
	 * 游戏码是否一致
	 * @param gameCode
	 * @return
	 */
	public static Specification<TTicket> isGameCodeEqual(final String gameCode)
	{
		return new Specification<TTicket>(){
			@Override
			public Predicate toPredicate(Root<TTicket> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("gameCode"), gameCode);
			}
		};
	}
	
	/**
	 * 奖期是否一致
	 * @param termCode
	 * @return
	 */
	public static Specification<TTicket> isTermCodeEqual(final String termCode)
	{
		return new Specification<TTicket>(){
			@Override
			public Predicate toPredicate(Root<TTicket> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("termCode"), termCode);
			}
		};
	}
	
	/**
	 * 是否包含对应的期次
	 * @param termCode
	 * @return
	 */
	public static Specification<TTicket> isTermCodeLike(final String termCode)
	{
		return new Specification<TTicket>(){
			@Override
			public Predicate toPredicate(Root<TTicket> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> path = root.get("termCode");
				return cb.like(path, "%" + termCode + "%");
			}
		};
	}
	
	/**
	 * 渠道是否一致
	 * @param channelCode
	 * @return
	 */
	public static Specification<TTicket> isChannelCodeEqual(final String channelCode)
	{
		return new Specification<TTicket>(){
			@Override
			public Predicate toPredicate(Root<TTicket> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("channelCode"), channelCode);
			}
		};
	}
	
	/**
	 * 用户id是否相等
	 * @param customerId
	 * @return
	 */
	public static Specification<TTicket> isCustomerIdEqual(final String customerId)
	{
		return new Specification<TTicket>(){
			@Override
			public Predicate toPredicate(Root<TTicket> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("customerId"), customerId);
			}
		};
	}
	
	/**
	 * 刷票状态是否相等
	 * @param receiptStatus
	 * @return
	 */
	public static Specification<TTicket> isReceiptStatusEqual(final int receiptStatus)
	{
		return new Specification<TTicket>(){
			@Override
			public Predicate toPredicate(Root<TTicket> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("receiptStatus"), receiptStatus);
			}
		};
	}
	
	/**
	 * 刷票状态是否相等
	 * @param receiptStatus
	 * @return
	 */
	public static Specification<TTicket> isReceiptStatusNotEqual(final int receiptStatus)
	{
		return new Specification<TTicket>(){
			@Override
			public Predicate toPredicate(Root<TTicket> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.notEqual(root.get("receiptStatus"), receiptStatus);
			}
		};
	}
	
	/**
	 * 是否是大奖
	 * @param bigBonus
	 * @return
	 */
	public static Specification<TTicket> isBigBonusEqual(final boolean bigBonus)
	{
		return new Specification<TTicket>(){
			@Override
			public Predicate toPredicate(Root<TTicket> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("bigBonus"), bigBonus);
			}
		};
	}
	
	/**
	 * 是否已经打印纸质票
	 * @param paper
	 * @return
	 */
	public static Specification<TTicket> isPaperEqual(final boolean paper)
	{
		return new Specification<TTicket>(){
			@Override
			public Predicate toPredicate(Root<TTicket> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("paper"), paper);
			}
		};
	}
	
	/**
	 *	根据序列号查询彩票
	 *	com.mcp.order.dao.specification.TicketSpecification
	 */	
	  public static Specification<TTicket> isSerialNumberEq(final String serialNumber)
    {
        return new Specification<TTicket>(){
            @Override
            public Predicate toPredicate(Root<TTicket> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("serialNumber"),serialNumber);
            }
        };
    }
		
   	/**
	 *	根据ID查询彩票
	 *	com.mcp.order.dao.specification.TicketSpecification
	 */	
    public static Specification<TTicket> isIdEq(final String id)
    {
        return new Specification<TTicket>(){
            @Override
            public Predicate toPredicate(Root<TTicket> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("id"),id);
            }
        };
    }
		
   	/**
	 *	初始化查询方法
	 *	com.mcp.order.dao.specification.TicketSpecification
	 */		
    public static Specification<TTicket> isIdNotNull()
    {
        return new Specification<TTicket>(){
            @Override
            public Predicate toPredicate(Root<TTicket> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.isNotNull(root.get("id"));
            }
        };
    }
}
