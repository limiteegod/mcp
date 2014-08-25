/**
 * 
 */
package com.mcp.deploy.database;

import com.mcp.deploy.common.DeployContext;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author ming.li
 *
 */
public class MongoTest {

	private static Logger log = Logger.getLogger(MongoTest.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MongoTemplate mongoTemplate = DeployContext.getInstance().getBean(
				"mongoTemplate", MongoTemplate.class);
		
		/*BasicDBObject dbObject = new BasicDBObject();
		dbObject.append("count", "term_prized_order_T05_2013101009");
		CommandResult cr = mongoTemplate.executeCommand(dbObject);
		log.info(cr.get("n"));*/
		
		//统计总数
		/*String jsonCommand = "{count:'term_prized_order_T05_2013101009'}";
		CommandResult cr = mongoTemplate.executeCommand(jsonCommand);
		log.info(cr.get("n"));*/
		
		/*BasicDBObject dbObject = new BasicDBObject();
		BasicDBObject group = new BasicDBObject("ns", "term_prized_order_T05_2013101009");
		group.append("key", new BasicDBObject("channelCode", 1));
		group.append("cond", new BasicDBObject());
		group.append("$reduce", "function ( curr, result ) {result.total += curr.bonus;result.count++;}");
		group.append("initial", new BasicDBObject("total", 0).append("count", 0));
		dbObject.append("group", group);
		CommandResult cr = mongoTemplate.executeCommand(dbObject);
		
		BasicDBList list = (BasicDBList)cr.get("retval");
		for(int i = 0; i < list.size(); i++)
		{
			BasicDBObject obj = (BasicDBObject)list.get(i);
			log.info(obj.get("channelCode")+ "-------" + obj.get("count") + "-------" + obj.get("total"));
		}
		log.info(cr.get("count"));*/
		
		//统计总的中奖金额
		/*String jsonCommand = "{group:{ns: 'term_prized_order_T05_2013101009', key: { channelCode: 1 }, cond: {}, $reduce: function ( curr, result ) {result.total += curr.bonus;}, initial: { total : 0 }}}";
		CommandResult cr = mongoTemplate.executeCommand(jsonCommand);
		log.info(cr.get("count"));*/
		
		int i = 0;
		DBCollection col = mongoTemplate.getCollection("term_prized_order_T05_2013101009");
		BasicDBObject query = new BasicDBObject();
		query.put("channelCode", "Q0003");
		DBCursor cur = col.find(query);
		while(cur.hasNext())
		{
			i++;
			log.info("index:" + i + ",id:" + cur.next().get("_id"));
		}
	}

}
