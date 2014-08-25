package com.mcp.jc.common;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.MathUtil;
import com.mcp.core.util.StringUtil;
import com.mcp.order.common.Constants;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeeson on 13-12-17.
 */
public class JcUtils {
	
    
    /**
     * 把订单的号码拆分为彩票，不考虑胆和混投同场次选多个玩法。
     * @param number 订单的号码，场次信息之间以分号分割
     * @param neededBalls 需要的场次数，比如用户玩的是4串N，则需要的场次数为4
     * @return
     */
    public static JcParam splitOrders(String number, int m, int n)
    {
    	JcParam param = new JcParam();
    	List<String> ticketList = new ArrayList<String>();
    	List<Integer> countList = new ArrayList<Integer>();
    	String[] numberArray = number.split(";");
    	
    	int[] danArray = new int[numberArray.length];
    	//List<Integer> danList = new ArrayList<Integer>();
    	
    	int danTuoCount = 0;
    	if(n == 1)
    	{
    		//记录场次的注数
        	for(int i = 0; i < numberArray.length; i++)
        	{
                String matchInfo = numberArray[i];
                if(matchInfo.startsWith("$"))
                {
                	danTuoCount++;
                	danArray[i] = i;
                }
                else
                {
                	danArray[i] = -1;
                }
        	}
    	}
    	int[][] data = MathUtil.getDetailC(numberArray.length, m);
    	for(int i = 0; i < data.length; i++)
    	{
    		int[] set = data[i];
    		if(danTuoCount > 0)	//胆拖玩法
    		{
    			int hitCount = LotteryUtil.getHitCount(danArray, set);
    			if(hitCount == danTuoCount)
    			{
    				JcParam dParam = multiMxn(m, n, set, numberArray);
    	    		ticketList.addAll(dParam.getTicketList());
    	    		countList.addAll(dParam.getCountList());
    			}
    		}
    		else //普通玩法
    		{
    			JcParam dParam = multiMxn(m, n, set, numberArray);
        		ticketList.addAll(dParam.getTicketList());
        		countList.addAll(dParam.getCountList());
    		}
    	}
    	param.setTicketList(ticketList);
    	param.setCountList(countList);
    	return param;
    }
    
    /**
     * 获得订单的注数
     */
    public static int getOrderCount(String number, int m, int n)
    {
    	JcParam param = splitOrders(number, m, n);
        int count = 0;
        List<Integer> countList = param.getCountList();
        for(int c:countList)
        {
        	count += c;
        }
        return count;
    }
    
    /**
     * 标准的多场串1算法。
     * @param m 票的总关数
     * @param n 要通过的关数
     * @param set 票的所有场次在订单所有场次中的序号列表
     * @param termCount 订单所有场次的注数列表
     * @return
     */
    public static int mX1(int m, int n, int[] set, int[] termCount) {
        int[][] data = MathUtil.getDetailC(m, n);
        int count = 0;
        for(int i = 0; i < data.length; i++)
        {
        	int[] record = data[i];
        	int tCount = 1;
        	for(int j = 0; j < record.length; j++)
        	{
        		tCount *= termCount[record[j]];
        	}
        	count += tCount;
        }
        return count;
    }
    
    /**
     * m串n
     * @return
     */
    public static int standardMxn(int m, int n, int[] set, int[] termCount)
    {
    	String mxn = String.valueOf(m) + String.valueOf(n);
    	String str = BunchMap.bunchMap.get(mxn); //mxn为串关描述符号。5串26
        int total = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                int ct = mX1(m, i+1, set, termCount);
                total = total + ct;
            }
        }
        return total;
    }
    
    
    /**
     * 多个m串n，当混投的时候，选择了多个玩法
     * @return
     */
    public static JcParam multiMxn(int m, int n, int[] set, String[] matchArray)
    {
    	JcParam param = new JcParam();
    	List<String> ticketList = new ArrayList<String>();
    	List<Integer> countList = new ArrayList<Integer>();
    	
    	String[] numberArray = new String[set.length];
    	for(int i = 0; i < set.length; i++)
    	{
    		numberArray[i] = matchArray[set[i]];
    	}
    	//System.out.println(Arrays.toString(numberArray));
    	
    	List<String[]> playList = new ArrayList<String[]>();
    	//记录玩法的注数列表
    	int[] countArray = new int[numberArray.length];
    	int playCount = 1;	//记录所有的注数
    	for(int i = 0; i < numberArray.length; i++)
    	{
            String[] playTypeArray = numberArray[i].split("&");
            playList.add(playTypeArray);
            countArray[i] = playTypeArray.length;
            playCount = playCount*playTypeArray.length;
    	}
    	
    	//System.out.println(playCount);
    	int[][] data = MathUtil.getDetailMultiplier(countArray);
    	
    	int[][] termCount = new int[data.length][set.length];
    	List<String> orderList = new ArrayList<String>();
    	for(int i = 0; i < playCount; i++)
    	{
    		String orderString = "";
    		int[] tset = data[i];
    		for(int j = 0; j < tset.length; j++)
    		{
    			if(j > 0)
    			{
    				orderString += ";";
    			}
    			String mString = playList.get(j)[tset[j]];
    			orderString += mString;
    			
    			termCount[i][j] = mString.split(",").length;
    		}
    		orderList.add(orderString);
    	}
    	
    	for(int i = 0; i < orderList.size(); i++)
    	{
    		ticketList.add(orderList.get(i));
    		countList.add(standardMxn(m, n, set, termCount[i]));
    	}
    	param.setTicketList(ticketList);
    	param.setCountList(countList);
    	return param;
    }
    
    /**
     * 获得票的玩法类型，01-胜平负，06-混投
     * @return
     */
    public static String getTicketType(String ticketStr)
    {
    	String playType = null;
    	String[] matchStrArray = ticketStr.split(";");
    	for(int i = 0; i < matchStrArray.length; i++)
    	{
    		String matchStr = matchStrArray[i];
    		String curPlayType = matchStr.split("\\|")[0];
    		if(curPlayType.startsWith("$"))
    		{
    			curPlayType = curPlayType.substring(1);
    		}
    		if(StringUtil.isEmpty(playType))
    		{
    			playType = curPlayType;
    		}
    		else
    		{
    			if(!playType.equals(curPlayType))
    			{
    				return Constants.JC_PLAYTYPE_HUNTOU;
    			}
    		}
    	}
    	return playType;
    }
    
    /**
     * 把一个订单拆分为多个订单，混投和其它玩法分开
     * @param order
     * @return
     */
    public static List<TOrder> splitOrder(TOrder order)
    {
    	List<TOrder> orderList = new ArrayList<TOrder>();
    	List<TTicket> tList = order.getTickets();
    	List<TTicket> htList = new ArrayList<TTicket>();
    	List<TTicket> norList = new ArrayList<TTicket>();
    	long norAmount = 0, htAmount = 0;
    	for(TTicket t:tList)
    	{
    		if(getTicketType(t.getNumbers()).equals(Constants.JC_PLAYTYPE_HUNTOU))
    		{
    			htList.add(t);
    			htAmount += t.getAmount();
    		}
    		else
    		{
    			norList.add(t);
    			norAmount += t.getAmount();
    		}
    	}
    	if(htAmount > 0)
    	{
    		TOrder htOrder = order.clone();
    		htOrder.setId(CoreUtil.getUUID());
    		htOrder.setTicketCount(htList.size());
    		htOrder.setAmount(htAmount);
    		for(TTicket t:htList)
        	{
    			t.setOrderId(htOrder.getId());
        	}
    		orderList.add(htOrder);
    	}
    	if(norAmount > 0)
    	{
    		TOrder norOrder = order.clone();
    		norOrder.setId(CoreUtil.getUUID());
    		norOrder.setTicketCount(norList.size());
    		norOrder.setAmount(norAmount);
    		for(TTicket t:norList)
        	{
    			t.setOrderId(norOrder.getId());
        	}
    		orderList.add(norOrder);
    	}
    	return orderList;
    }
    

    public static void main(String[] args) {
        //String betInfo="$01|201312172001|1@1.0,2&02|201312172001|1@1.0;21|201312172002|1@1.1,2;21|201312172003|1@1.2,3,0&22|201312172003|1@1.2,3;21|201312172005|1@1.3,2,3;21|201312172006|1@1.4,2,3;21|201312172006|1@1.5";
        //System.out.println(getOrderCount(betInfo, 3, 1));
    	String betInfo="$02|201312172001|1@1.0;02|201312172006|1@1.4,2,3;02|201312172006|1@1.5";
    	System.out.println(getTicketType(betInfo));
    }
}
