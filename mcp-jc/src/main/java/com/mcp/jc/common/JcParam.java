/**
 * 
 */
package com.mcp.jc.common;

import java.util.List;

/**
 * @author ming.li
 *
 */
public class JcParam {
	
	/**
	 * 所有的票的信息
	 */
	private List<String> ticketList;
	
	/**
	 * 所有票的注数信息
	 */
	private List<Integer> countList;

	public List<String> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<String> ticketList) {
		this.ticketList = ticketList;
	}

	public List<Integer> getCountList() {
		return countList;
	}

	public void setCountList(List<Integer> countList) {
		this.countList = countList;
	}
	
	public int getCount()
	{
		int count = 0;
		for(int i = 0; i < countList.size(); i++)
		{
			count += countList.get(i);
		}
		return count;
	}
}
