/**
 * 
 */
package com.mcp.jc;

import com.deploy.filter.TestUtil;
import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.print.TestConfig;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.notify.ReqN04Body;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.model.ts.Term;
import com.mcp.order.status.TermState;
import com.mcp.order.util.DateTimeUtil;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ming.li
 *
 */
public class JcTermNotify {
	
	public static void addTerm(String termCode) throws Exception
	{
		ObjectMapper om = new ObjectMapper();
		ReqN04Body body = new ReqN04Body();
		
		Term t = new Term();
		t.setId(CoreUtil.getUUID());
		t.setGameCode("T51");
		t.setCode(termCode);
		t.setOpenTime(new Date());
		t.setEndTime(DateTimeUtil.getDateAfterMilliseconds(36000000));
		t.setCreateTime(new Date());
		t.setStatus(TermState.NOT_ON_SALE.getCode());
		t.setConcedePoints(0);
		t.setMatchTime(new Date());
		t.setWinningNumber("01|1;02|1;03|11;04|2;05|11");
		t.setDetailInfo("中国联赛|0001|中国一队|0002|中国二队");
		List<Term> tList = new ArrayList<Term>();
		tList.add(t);
		
		body.setTerms(tList);
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("N040101"));
		String bodyStr = om.writeValueAsString(body);
		String message = TestUtil.getReqMessage("", "Q0001", bodyStr, "N04");
		System.out.println(message);
		String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, null);
		System.out.println(content);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		addTerm("201312172001");
		addTerm("201312172002");
		addTerm("201312172003");
		addTerm("201312172004");
		addTerm("201312172005");
		addTerm("201312172006");
		addTerm("201312172007");
	}
}
