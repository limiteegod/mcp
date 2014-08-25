/**
 * 
 */
package com.mcp.order.gateway.control;

import com.mcp.core.util.CoreUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.JsonBody;
import com.mcp.order.gateway.annotation.JsonHead;
import com.mcp.order.gateway.annotation.McpStation;
import com.mcp.order.gateway.annotation.McpUser;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.cash.*;
import com.mcp.order.inter.util.PageInfoUtil;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.report.Coupon;
import com.mcp.order.model.ts.Customer;
import com.mcp.order.model.ts.MoneyLog;
import com.mcp.order.model.ts.Payment;
import com.mcp.order.model.ts.WithDraw;
import com.mcp.order.service.*;
import com.mcp.order.service.util.UserStationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * @author ming.li
 *
 */
@Controller
@RequestMapping("/cash")
public class CashControl {
	
	@Autowired
	private MoneyService moneyService;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private WithDrawService withDrawService;
	
	@Autowired
	private PaymentService paymentService;

    @Autowired
    private CouponService couponService;
	
	/**
	 * 用户转奖金到投注金账户
	 */
	@RequestMapping(value = "/prizeToRecharge.htm")
	public String prizeToRecharge(@JsonHead("head") Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="C01") ReqC01Body body,
			 ModelMap modelMap) throws Exception {
		RepC01Body repBody = new RepC01Body();
		
		Station station = stationService.findOneByCode(head.getChannelCode());
		String stationId = UserStationUtil.getStationId(station, head.getChannelCode(), body.getStationId());
		
		String orderId = CoreUtil.getUUID();
		List<MoneyLog> logList = moneyService.customerPrizeToRecharge(customer, stationId, customer.getId(), orderId, body.getAmount());
		MoneyLog first = logList.get(0);
		MoneyLog second = logList.get(1);
		repBody.setRechargeBefore(first.getStateBefore());
		repBody.setRechargeAfter(first.getStateAfter());
		
		repBody.setPrizeBefore(second.getStateBefore());
		repBody.setPrizeAfter(second.getStateAfter());
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 用户充值到投注金账户
	 */
	@RequestMapping(value = "/rechargeAtStation.htm")
	public String rechargeAtStation(@JsonHead("head") Head head, @McpStation Station station, @JsonBody(value="body", cmd="C02") ReqC02Body body,
			 ModelMap modelMap) throws Exception {
		RepC02Body repBody = new RepC02Body();
		
		String orderId = body.getOrderId();
		Customer customer = customerService.findOneByNameAndChannelCode(body.getName(), head.getChannelCode());
		MoneyLog moneyLog = moneyService.customerRecharge(station.getId(), customer.getId(), orderId, body.getAmount(), body.getFromType());
		
		repBody.setRechargeBefore(moneyLog.getStateBefore());
		repBody.setRechargeAfter(moneyLog.getStateAfter());
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}
	
	/**
	 * 用户奖金账户提现
	 */
	@RequestMapping(value = "/prizeToOut.htm")
	public String prizeToOut(@JsonHead("head") Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="C03") ReqC03Body body,
			 ModelMap modelMap) throws Exception {
		RepC03Body repBody = new RepC03Body();
		
		Station station = stationService.findOneByCode(head.getChannelCode());
		String orderId = CoreUtil.getUUID();
		
		String stationId = UserStationUtil.getStationId(station, head.getChannelCode(), body.getStationId());
		Payment payment = this.paymentService.findOneByCustomerId(customer.getId());
		if(payment == null)
		{
			throw new CoreException(ErrCode.E1028, ErrCode.codeToMsg(ErrCode.E1028));
		}
		
		List<MoneyLog> logList = moneyService.customerPrizeToOut(customer, stationId, customer.getId(), orderId, body.getAmount());
		MoneyLog first = logList.get(0);
		
		//记录提现请求
		WithDraw withDraw = new WithDraw();
		withDraw.setId(CoreUtil.getUUID());
		withDraw.setAmount(body.getAmount());
		withDraw.setCardNumber(payment.getCardNumber());
		withDraw.setCustomerId(customer.getId());
		withDraw.setDesp("提现");
		withDraw.setMoneyLogId(first.getId());
		withDraw.setStartTime(new Date());
		withDraw.setStatus(ConstantValues.MoneyLog_Status_HANDLING.getCode());
		this.withDrawService.save(withDraw);
		
		repBody.setPrizeBefore(first.getStateBefore());
		repBody.setPrizeAfter(first.getStateAfter());
		
		modelMap.put("response", repBody);
		return "plainJsonView";
	}

    /**
     * 渠道给本渠道的用户发优惠卷
     */
    @RequestMapping(value = "/coupon.htm")
    public String coupon(@JsonHead("head") Head head, @McpStation Station station, @JsonBody(value="body", cmd="C04") ReqC04Body body,
                             ModelMap modelMap) throws Exception {
        RepC04Body repBody = new RepC04Body();

        Customer customer = this.customerService.findOneByNameAndChannelCode(body.getName(), station.getCode());
        if(customer == null)
        {
            throw new CoreException(ErrCode.E1003, ErrCode.codeToMsg(ErrCode.E1003));
        }
        Coupon coupon = new Coupon();
        coupon.setId(CoreUtil.getUUID());
        coupon.setAmount(body.getAmount());
        coupon.setCustomerId(customer.getId());
        Date now = new Date();
        coupon.setCreateTime(now);
        coupon.setActiveTime(now);
        coupon.setExpiredTime(body.getExpiredTime());
        coupon.setType(ConstantValues.COUPON_TYPE_CHANNEL.getCode());
        coupon.setChannelCode(station.getCode());
        coupon.setStatus(ConstantValues.COUPON_STATUS_AVAILABLE.getCode());
        this.couponService.save(coupon);

        repBody.setCoupon(coupon);
        modelMap.put("response", repBody);
        return "plainJsonView";
    }

    /**
     * 渠道给本渠道的用户发优惠卷
     */
    @RequestMapping(value = "/getCoupon.htm")
    public String getCoupon(@JsonHead("head") Head head, @McpUser Customer customer, @JsonBody(value="body", cmd="C05") ReqC05Body body,
                         ModelMap modelMap) throws Exception {
        RepC05Body repBody = new RepC05Body();
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "activeTime"));
        PageRequest page = new PageRequest(body.getPage(), body.getSize(), sort);
        Page<Coupon> pageList = this.couponService.findByCustomerAndStatus(customer, body.getStatus(), page);
        repBody.setRst(pageList.getContent());
        repBody.setPi(PageInfoUtil.getPageInfo(pageList));
        modelMap.put("response", repBody);
        return "plainJsonView";
    }
}
