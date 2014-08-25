/**
 * 
 */
package com.mcp.order.dao.redis;

import com.mcp.order.common.Constants;
import com.mcp.order.common.RedisKey;
import com.mcp.order.model.message.CustomerMsg;
import com.mcp.order.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ming.li
 *
 */
public class RedisHelp {
	
	//private static Logger log = Logger.getLogger(RedisHelp.class);
	
	@Autowired
	private ShardedJedisPool shardedJedisPool;
	
	public String set(String key, String value)
	{
		ShardedJedis jedis =  shardedJedisPool.getResource();
		String backValue = jedis.set(key, value);
		shardedJedisPool.returnResource(jedis);
		return backValue;
	}
	
	public String get(String key)
	{
		ShardedJedis jedis =  shardedJedisPool.getResource();
		String backValue = jedis.get(key);
		shardedJedisPool.returnResource(jedis);
		return backValue;
	}
	
	public Long lpush(String key, String value)
	{
		ShardedJedis jedis =  shardedJedisPool.getResource();
		Long backValue = jedis.lpush(key, value);
		shardedJedisPool.returnResource(jedis);
		return backValue;
	}
	
	public Long llen(String key)
	{
		ShardedJedis jedis =  shardedJedisPool.getResource();
		Long backValue = jedis.llen(key);
		shardedJedisPool.returnResource(jedis);
		return backValue;
	}
	
	public Long rpush(String key, String value)
	{
		ShardedJedis jedis =  shardedJedisPool.getResource();
		Long backValue = jedis.rpush(key, value);
		shardedJedisPool.returnResource(jedis);
		return backValue;
	}
	
	public Long del(String key)
	{
		ShardedJedis jedis =  shardedJedisPool.getResource();
		Long backValue = jedis.del(key);
		shardedJedisPool.returnResource(jedis);
		return backValue;
	}
	
	public String rpop(String key)
	{
		ShardedJedis jedis =  shardedJedisPool.getResource();
		String backValue = jedis.rpop(key);
		shardedJedisPool.returnResource(jedis);
		return backValue;
	}
	
	public List<String> lrange(String key, long start, long end)
	{
		ShardedJedis jedis =  shardedJedisPool.getResource();
		List<String> backValue = jedis.lrange(key, start, end);
		shardedJedisPool.returnResource(jedis);
		return backValue;
	}
	
	public String ltrim(String key, long start, long end)
	{
		ShardedJedis jedis =  shardedJedisPool.getResource();
		String backValue = jedis.ltrim(key, start, end);
		shardedJedisPool.returnResource(jedis);
		return backValue;
	}
	
	public String lpop(String key)
	{
		ShardedJedis jedis =  shardedJedisPool.getResource();
		String backValue = jedis.lpop(key);
		shardedJedisPool.returnResource(jedis);
		return backValue;
	}
	
	public Long incrBy(String key, long step)
	{
		ShardedJedis jedis =  shardedJedisPool.getResource();
		long backValue = jedis.incrBy(key, step);
		shardedJedisPool.returnResource(jedis);
		return backValue;
	}
	
	public Long decrBy(String key, long step)
	{
		ShardedJedis jedis =  shardedJedisPool.getResource();
		long backValue = jedis.decrBy(key, step);
		shardedJedisPool.returnResource(jedis);
		return backValue;
	}
	
	/**
	 * 写用户购买订单（方案）成功通知，并保持队列的最大长度Constants.CUSTOMER_NOTIFY_MAX_LENGTH
	 * @param customerId
	 * @param targetId
	 * @param amount
	 */
	public void writeOrderNotify(String customerId, String targetId, long amount)
	{
		String timeKey = RedisKey.getNotifyTimeKey(customerId);
		String contentKey = RedisKey.getNotifyContentKey(customerId);
		String value = RedisKey.getCustomerNotifyValue(targetId, amount);
		lpush(timeKey, DateTimeUtil.getCurrentTime());
		lpush(contentKey, value);
		ltrim(timeKey, 0, Constants.CUSTOMER_NOTIFY_MAX_LENGTH);
		ltrim(timeKey, 0, Constants.CUSTOMER_NOTIFY_MAX_LENGTH);
	}
	
	/**
	 * 获取用户的通知，type=0，获取时间>time的记录，type=1，获取时间<time的记录。
	 * 根据时间从大到小排序（DESC）
	 * @param type
	 * @param time
	 * @param size 记录的条数
	 * @return
	 */
	public List<CustomerMsg> getCustomerMsg(String customerId, int type, Date time, int size)
	{
		List<CustomerMsg> msgList = new ArrayList<CustomerMsg>();
		String timeKey = RedisKey.getNotifyTimeKey(customerId);
		List<String> timeList = this.lrange(timeKey, 0, -1);
		if(timeList.size() == 0)
		{
			return msgList;
		}
		int index = -2;
		for(int i = 0; i < timeList.size(); i++)
		{
			String dateString = timeList.get(i);
			Date date = null;
			try {
				date = DateTimeUtil.getDateFromString(dateString);
			} catch (ParseException e) {
			}
			if(date == null)
			{
				break;
			}
			if(type == 0)
			{
				if(date.getTime() <= time.getTime())
				{
					index = i - 1;	//如果第一个记录相等，index=-1
					break;
				}
			}
			else
			{
				if(date.getTime() < time.getTime())
				{
					index = i;
					break;
				}
			}
		}
		long start = 0, end = 0;
		if(type == 0)
		{
			//index==-1，没有记录比传递过来的时间要大
			if(index == -1)
			{
				return msgList;
			}
			//index==-2，所有记录都比传递过来的时间要大
			else if(index == -2)
			{
				start = 0;
				end = timeList.size() - 1;
				if(end > size - 1)
				{
					end = size - 1;
				}
			}
			else if(index >= 0)
			{
				start = 0;
				end = index;
				if(end > size - 1)
				{
					end = size - 1;
				}
			}
		}
		else
		{
			//没有时间比传递过来的时间要小
			if(index == -2)
			{
				return msgList;
			}
			if(index >= 0)
			{
				start = index;
				end = start + size - 1;
				if(end > timeList.size() - 1)
				{
					end = timeList.size() - 1;
				}
			}
		}
		if(start < 0 || end < 0)
		{
			return msgList;
		}
		String contentKey = RedisKey.getNotifyContentKey(customerId);
		List<String> contentList = this.lrange(contentKey, start, end);
		for(int i = 0; i < contentList.size(); i++)
		{ 
			CustomerMsg msg = new CustomerMsg();
			try {
				msg.setTime(DateTimeUtil.getDateFromString(timeList.get((int)start + i)));
			} catch (ParseException e) {
			}
			msg.setContent(contentList.get(i));
			msgList.add(msg);
		}
		return msgList;
	}

	/**
	 * 获得系统账户的余额
	 * 现在有两个账户，现金账户，及业务账户
	 * @param accountId
	 * @return
	 */
	public long getSystemBalance(String accountId)
	{
		String key = RedisKey.getSystemBalanceKey(accountId);
		long balance = 0;
		String balanceStr = get(key);
		if(balanceStr != null)
		{
			balance = Long.parseLong(balanceStr);
		}
		return balance;
	}
}
