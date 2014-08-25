package com.mcp.scheduler.issue;

import com.mcp.order.common.Constants;
import com.mcp.order.model.mongo.MgNotify;
import com.mcp.order.model.mongo.MgTermSeal;
import com.mcp.order.mongo.common.MgConstants;
import com.mcp.order.mongo.service.*;
import com.mcp.order.util.DateTimeUtil;
import com.mcp.scheduler.common.SchedulerContext;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日结模型，导出老数据到文件，保持mongodb的效率
 * Created by ming.li on 2014/4/22.
 */
public class ClearModel {

    private static final Logger log = Logger.getLogger(ClearModel.class);

    /**
     * spring的上下文
     */
    private ApplicationContext context;

    private MongoTemplate mongoTemplate;

    private MgTermSealService mgTermSealService;

    public ClearModel()
    {
        this.context = SchedulerContext.getInstance().getSpringContext();
        this.mongoTemplate = this.context.getBean("mongoTemplate", MongoTemplate.class);
        this.mgTermSealService = this.context.getBean("mgTermSealService", MgTermSealService.class);
    }

    /**
     * 获得上一天封存的所有的奖期，
     */
    public void clearOldIssue() throws Exception
    {
        Date todayStart = DateTimeUtil.getToday();
        Date lastDayStart = DateTimeUtil.getDateBeforeMilliseconds(todayStart, 24*60*60*1000L);

        log.info("查询的时间区间,start:" + todayStart + ",end:" + lastDayStart);
        /*//测试时使用，查找当天封存的期次
        Date lastDayStart = DateTimeUtil.getToday();
        Date todayStart = DateTimeUtil.getDateAfterMilliseconds(lastDayStart, 24*60*60*1000);*/
        List<MgTermSeal> sList = this.mgTermSealService.findLastDaySealList(lastDayStart, todayStart);
        for(MgTermSeal mts:sList)
        {
            log.info("日结,清理上一天的期次数据,gameCode:" + mts.getGameCode() + ",termCode:" + mts.getTermCode() + "......");
            this.clearTerm(mts);
        }
    }

    /**
     * 清理一个期次的数据
     * @param mts
     */
    private void clearTerm(MgTermSeal mts) throws Exception
    {
        String gameCode = mts.getGameCode();
        String termCode = mts.getTermCode();
        MgTicketService mgTicketService = this.context.getBean("mgTicketService", MgTicketService.class);
        MgOrderService mgOrderService = this.context.getBean("mgOrderService", MgOrderService.class);
        MgCheckService mgCheckService = this.context.getBean("mgCheckService", MgCheckService.class);
        MgPrizeService mgPrizeService = this.context.getBean("mgPrizeService", MgPrizeService.class);
        mgTicketService.clear(gameCode, termCode);
        mgOrderService.clear(gameCode, termCode);
        mgCheckService.clear(gameCode, termCode);
        mgPrizeService.clear(gameCode, termCode);
    }

    /**
     * 导出ticket的票据信息
     * @param tColNameList
     */
    private void expTicket(List<String> tColNameList, MgTermSeal mts) throws Exception
    {
        for(String colName:tColNameList)
        {
            DBCollection col = this.mongoTemplate.getCollection(colName);
            String fileName = mts.getFilePath() + "/" + colName + ".txt";
            //如果文件存在，则删除原来的文件
            File file = new File(fileName);
            if(file.exists())
            {
                file.delete();
            }
            FileWriter writer = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(writer);
            DBCursor cur = col.find();
            log.info(cur.count());
            while(cur.hasNext())
            {
                DBObject obj = cur.next();
                String line = obj.get("_id") + Constants.SEP_DES + obj.get("orderId") + Constants.SEP_DES + obj.get("customerId") + Constants.SEP_DES + obj.get("stationId") +
                        Constants.SEP_DES + obj.get("playTypeCode") + Constants.SEP_DES + obj.get("betTypeCode") + Constants.SEP_DES + obj.get("numbers") +
                        Constants.SEP_DES + obj.get("price") + Constants.SEP_DES + obj.get("multiple") + Constants.SEP_DES + obj.get("rNumber") +
                        Constants.SEP_DES + obj.get("gameCode") + Constants.SEP_DES + obj.get("status") + Constants.SEP_DES + obj.get("termCode");
                bw.write(line + "\r\n");
            }
            cur.close();
            bw.close();
            writer.close();

            //导出之后，删除collection
            col.drop();
        }
    }

    /**
     * 导出order的信息
     * @param tColNameList
     */
    private void expOrder(List<String> tColNameList, MgTermSeal mts) throws Exception
    {
        for(String colName:tColNameList)
        {
            DBCollection col = this.mongoTemplate.getCollection(colName);
            String fileName = mts.getFilePath() + "/" + colName + ".txt";
            //如果文件存在，则删除原来的文件
            File file = new File(fileName);
            if(file.exists())
            {
                file.delete();
            }
            FileWriter writer = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(writer);
            DBCursor cur = col.find();
            log.info(cur.count());
            while(cur.hasNext())
            {
                DBObject obj = cur.next();
                String line = obj.get("_id") + Constants.SEP_DES + obj.get("schemeId") + Constants.SEP_DES + obj.get("schemeType") + Constants.SEP_DES + obj.get("customerId") +
                        Constants.SEP_DES + obj.get("stationId") + Constants.SEP_DES + obj.get("gameCode") + Constants.SEP_DES + obj.get("termCode") +
                        Constants.SEP_DES + obj.get("bonus") + Constants.SEP_DES + obj.get("status") + Constants.SEP_DES + obj.get("amount") +
                        Constants.SEP_DES + obj.get("ticketCount") + Constants.SEP_DES + obj.get("printCount") + Constants.SEP_DES + obj.get("payType") +
                        Constants.SEP_DES + obj.get("printStationId");
                bw.write(line + "\r\n");
            }
            cur.close();
            bw.close();
            writer.close();

            //导出之后，删除collection
            col.drop();
        }
    }
}
