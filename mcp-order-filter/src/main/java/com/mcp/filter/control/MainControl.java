/**
 * 
 */
package com.mcp.filter.control;

import com.mcp.filter.annotation.FilterMessage;
import com.mcp.filter.model.FilterMsg;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ming.li
 *
 */
@Controller
@RequestMapping("/main")
public class MainControl {
	
	/*@Autowired
	private TicketService ticketService;
	
	@Autowired
	private CustomerService customerService;*/
	
	private static Logger log = Logger.getLogger(MainControl.class);
	
	/**
	 * 支付宝通知接口
	 * @param notify_data
	 * @param sign
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/alipayNotify.htm")
	public String alipayNotify(@RequestParam("notify_data") String notify_data, @RequestParam("sign") String sign, ModelMap modelMap) throws Exception {
		String verifyData = "notify_data="+notify_data;
        log.info("verifyData : "+verifyData+"\r\n");
        log.info("sign : "+sign+"\r\n");
        boolean verified = false;
        //使用支付宝公钥验签名
        try {
            verified = RSASignature.doCheck(verifyData, sign, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLP0L7qDFHS4XKiYsBnFDXqMM6pd15OvxHj1ICMGRvwO1+/uCAdCMC1ODqIQyAmCKfb4mIc0agfu9BzqJt6eToaZZcdwAqygbieFZNKLjvckMmJjKChDdumYcdFZQbbXc2Ohzf6lf48P0/lbFCaOu4Eh58rU151oxmOBtiu+uQvQIDAQAB");
        	//verified = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("verified : "+verified+"\r\n");
        String rep;
        //验证签名通过
        if (verified) {
        	SAXBuilder builder = new SAXBuilder();
        	InputStream is = new ByteArrayInputStream(notify_data.getBytes());
        	Document doc = builder.build(is);
        	Element root = doc.getRootElement(); //取得根节点
        	String tradeStatus = root.getChild("trade_status").getText();
        	String totalFee = root.getChild("total_fee").getText();
        	String subject = root.getChild("subject").getText();
        	String outTradeNo = root.getChild("out_trade_no").getText();
        	String notifyRegTime = root.getChild("gmt_create").getText();
        	String trade_no = root.getChild("trade_no").getText();
        	
            log.info("trade_Status : " + tradeStatus + "\r\n");
            if(tradeStatus.equals("TRADE_FINISHED"))
            {
            	try {
            		ObjectMapper om = new ObjectMapper();
                    String cmd = "Q08";
                    ServiceType serviceType = AdapterContext.getInstance().getServiceType(cmd);
                    String path = serviceType.getPath();
        			CookieParam param = new CookieParam();
        			Head head = new Head();
        			head.setChannelCode("Q0001");
        			head.setCmd(cmd);
        			head.setId(UUID.randomUUID().toString().replace("-", ""));
        			head.setTimestamp(new Date());
        			head.setVer(Constants.GATEWAY_VERSION);
        			String headStr = om.writeValueAsString(head);
        			
        			ReqQ08Body body = new ReqQ08Body();
        			body.setPayment("支付宝");
        			body.setPaymentType(ConstantValues.Payment_Type_Alipay.getCode());
        			body.setOrderId(outTradeNo);
        			body.setOuterId(trade_no);
        			body.setNotifyTime(new Date());
        			body.setNotifyCreateTime(DateTimeUtil.alipayGetDateFromString(notifyRegTime));
        			body.setNotifyType(subject);
        			body.setAmount((int)(Double.parseDouble(totalFee)*100));
        			body.setDescription("支付宝通知");
        			String bodyStr = om.writeValueAsString(body);
        			String content = HttpClientUtil.request(SystemConfig.GATEWAY_SITE, SystemConfig.GATEWAY_PORT, path, headStr, bodyStr, HttpClientUtil.POST, param);
        			log.info("gateway返回：" + content);
        			
            	} catch(Exception e)
            	{
            		e.printStackTrace();
            		log.info("接收到支付宝通知之后处理过程中出现异常.....");
            	}
            }
            rep = "success";
        } else {
            System.out.println("接收支付宝系统通知验证签名失败，请检查！");
            rep = "fail";
        }
		modelMap.put("response", rep);
		return "plainTextView";
	}*/
	
	
	@RequestMapping(value = "/interface.htm")
	public String inter(@FilterMessage("message") FilterMsg message, ModelMap modelMap) throws Exception {
		modelMap.put("filterMsg", message);
		return "gateWayView";
	}
	
	@RequestMapping(value = "/notify.htm")
	public String notify(@RequestParam("message") String message, ModelMap modelMap) throws Exception {
		log.info("---------------notify test-----------------");
		log.info(message);
		modelMap.put("response", "OK");
		return "plainTextView";
	}
	
	/*@RequestMapping(value = "/pic/{ticketId}")
	public void pic(@PathVariable("ticketId") String ticketId, ModelMap modelMap, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		Object user = null;
		Cookie[] cookies = httpServletRequest.getCookies();
		String userId = getCookieValueByName(cookies, "userId");
		String userMd5 = getCookieValueByName(cookies, "userMd5");
		String typeString = getCookieValueByName(cookies, "userType");
		log.info("userId-" + userId);
		log.info("userType-" + typeString);
		log.info("userMd5-" + userMd5);
		if(userId == null || userMd5 == null || typeString == null)
			return;
		int type = Integer.parseInt(typeString);
		String md5 = null;
		if(type == Constants.USER_TYPE_CUSTOMER)
		{
			Customer cus = customerService.findOne(userId);
			md5 = CookieUtil.getUserMd5(cus.getName(), cus.getPassword(), cus.getLastLoginTime().getTime());
			log.info("md5:" + md5 + "-" + "userMd5:" + userMd5);
			if(md5.equals(userMd5))
			{
				user = cus;
			}
		}
		if(user == null)
			return;
		log.info("ticketId:" + ticketId);
		TTicket ticket = ticketService.findOne(ticketId);
		log.info("ticket:" + ticket);
		if(ticket == null || !ticket.getCustomerId().equals(userId))
		{
			return;
		}
		File file = new File(FileConstants.getTicketFilePath(ticket.getGameCode(), ticket.getTermCode(), ticketId));
		FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[(int)file.length()];
        inputStream.read(data);
        inputStream.close();

        httpServletResponse.setContentType("image/png");

        OutputStream stream = httpServletResponse.getOutputStream();
        stream.write(data);
        stream.flush();
        stream.close();
	}*/
	
	/*private String getCookieValueByName(Cookie[] cookies, String name)
	{
		if(cookies == null)
		{
			return null;
		}
		for(int i = 0; i < cookies.length; i++)
		{
			if(cookies[i].getName().equals(name))
			{
				return cookies[i].getValue();
			}
		}
		return null;
	}*/
}
